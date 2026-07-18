import java.util.*;

/**
 * Greedy.java
 * Implementa la Búsqueda Greedy Best-First (Primero el Mejor Voraz).
 *
 * Estrategia: expande siempre el nodo con menor h(n), donde h(n) es la
 * distancia en línea recta desde la ciudad actual hasta el destino (Haversine).
 * Ignora el costo acumulado g(n): solo le importa "qué tan cerca parece estar".
 *
 * Completitud : No garantizada (puede ciclar en grafos con ciclos sin detección)
 *               Con conjunto "cerrados" se vuelve completo en grafos finitos.
 * Optimalidad : No (puede elegir caminos localmente atractivos pero globalmente costosos)
 * Complejidad : O(b^m) tiempo y espacio en peor caso
 */
public class Greedy {

    public Resultado buscar(Grafo grafo, String origen, String destino) {
        long inicio = System.currentTimeMillis();
        int  nodosExpandidos = 0;

        // Cola de prioridad ordenada por h(n) ascendente
        PriorityQueue<Nodo> frontera = new PriorityQueue<>(
            Comparator.comparingDouble(n -> n.h)
        );

        // Conjunto de nodos ya expandidos (evita reexpansión)
        Set<String> cerrados = new HashSet<>();

        double h0 = Heuristica.distanciaLineaRecta(origen, destino);
        frontera.add(new Nodo(origen, 0.0, h0, new ArrayList<>(List.of(origen))));

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.poll(); // nodo con menor h(n)
            nodosExpandidos++;

            // Verificar meta antes de marcar como cerrado
            if (actual.ciudad.equals(destino)) {
                long tiempo = System.currentTimeMillis() - inicio;
                return new Resultado(actual.camino, actual.g,
                                     nodosExpandidos, tiempo);
            }

            // Si ya fue expandido, saltar
            if (cerrados.contains(actual.ciudad)) continue;
            cerrados.add(actual.ciudad);

            // Expandir vecinos no cerrados
            for (Par vecino : grafo.getVecinos(actual.ciudad)) {
                if (!cerrados.contains(vecino.ciudad)) {
                    double h = Heuristica.distanciaLineaRecta(vecino.ciudad, destino);
                    List<String> nuevoCamino = new ArrayList<>(actual.camino);
                    nuevoCamino.add(vecino.ciudad);
                    // En Greedy: f = h (ignoramos g para la prioridad)
                    frontera.add(new Nodo(vecino.ciudad,
                                          actual.g + vecino.distancia,
                                          h, nuevoCamino));
                }
            }
        }

        return null; // sin solución
    }
}
