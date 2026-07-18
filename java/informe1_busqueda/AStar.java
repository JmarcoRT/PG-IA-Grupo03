import java.util.*;

/**
 * AStar.java
 * Implementa el algoritmo A* (A estrella).
 *
 * Estrategia: expande el nodo con menor f(n) = g(n) + h(n), donde:
 *   g(n) = costo real acumulado desde el origen hasta n
 *   h(n) = estimación heurística del costo restante desde n hasta la meta
 *          (distancia en línea recta calculada con Haversine)
 *
 * A* garantiza optimalidad si la heurística es ADMISIBLE (h(n) <= h*(n))
 * y eficiencia si además es CONSISTENTE (h(n) <= c(n,n') + h(n')).
 * La distancia en línea recta cumple ambas propiedades.
 *
 * Completitud : Sí (grafo finito, heurística no negativa)
 * Optimalidad : Sí (heurística admisible + consistente)
 * Complejidad : O(b^d) en peor caso, pero típicamente mucho mejor con buena h
 */
public class AStar {

    public Resultado buscar(Grafo grafo, String origen, String destino) {
        long inicio = System.currentTimeMillis();
        int  nodosExpandidos = 0;

        // Cola de prioridad ordenada por f(n) = g(n) + h(n) ascendente
        PriorityQueue<Nodo> frontera = new PriorityQueue<>(
            Comparator.comparingDouble(n -> n.f)
        );

        // Conjunto de nodos ya expandidos (cerrados)
        // Con heurística consistente, el primer camino a cada nodo es el óptimo,
        // por lo que nunca es necesario reabrir un nodo cerrado.
        Set<String> cerrados = new HashSet<>();

        double h0 = Heuristica.distanciaLineaRecta(origen, destino);
        frontera.add(new Nodo(origen, 0.0, h0, new ArrayList<>(List.of(origen))));

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.poll(); // nodo con menor f(n)
            nodosExpandidos++;

            // Verificar meta
            if (actual.ciudad.equals(destino)) {
                long tiempo = System.currentTimeMillis() - inicio;
                return new Resultado(actual.camino, actual.g,
                                     nodosExpandidos, tiempo);
            }

            // Si ya fue expandido con un costo menor, descartar
            if (cerrados.contains(actual.ciudad)) continue;
            cerrados.add(actual.ciudad);

            // Expandir vecinos no cerrados
            for (Par vecino : grafo.getVecinos(actual.ciudad)) {
                if (!cerrados.contains(vecino.ciudad)) {
                    double nuevoG = actual.g + vecino.distancia;
                    double h      = Heuristica.distanciaLineaRecta(vecino.ciudad, destino);
                    List<String> nuevoCamino = new ArrayList<>(actual.camino);
                    nuevoCamino.add(vecino.ciudad);
                    // f = nuevoG + h se calcula en el constructor de Nodo
                    frontera.add(new Nodo(vecino.ciudad, nuevoG, h, nuevoCamino));
                }
            }
        }

        return null; // sin solución
    }
}
