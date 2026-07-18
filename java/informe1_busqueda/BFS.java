import java.util.*;

/**
 * BFS.java
 * Implementa la Búsqueda en Anchura (Breadth-First Search).
 *
 * Estrategia: explora el grafo nivel por nivel usando una cola FIFO.
 * No usa heurística. Garantiza encontrar el camino con MENOS ARISTAS,
 * pero NO el de menor costo en grafos ponderados.
 *
 * Completitud : Sí (grafo finito con detección de ciclos)
 * Optimalidad : No (en grafos ponderados)
 * Complejidad : O(b^d) tiempo y espacio, b=factor de ramificación, d=profundidad
 */
public class BFS {

    public Resultado buscar(Grafo grafo, String origen, String destino) {
        long inicio = System.currentTimeMillis();
        int  nodosExpandidos = 0;

        // Cola FIFO: cada elemento es un Nodo con su camino y visitados propios
        Queue<Nodo> frontera = new LinkedList<>();

        List<String> caminoInicial    = new ArrayList<>(List.of(origen));
        Set<String>  visitadosInicial = new HashSet<>(Set.of(origen));
        frontera.add(new Nodo(origen, 0.0, caminoInicial, visitadosInicial));

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.poll(); // extrae el nodo más antiguo (FIFO)
            nodosExpandidos++;

            // Verificar si llegamos a la meta
            if (actual.ciudad.equals(destino)) {
                long tiempo = System.currentTimeMillis() - inicio;
                return new Resultado(actual.camino, actual.g,
                                     nodosExpandidos, tiempo);
            }

            // Expandir vecinos no visitados en este camino
            for (Par vecino : grafo.getVecinos(actual.ciudad)) {
                if (!actual.visitados.contains(vecino.ciudad)) {
                    List<String> nuevoCamino = new ArrayList<>(actual.camino);
                    nuevoCamino.add(vecino.ciudad);

                    Set<String> nuevosVisitados = new HashSet<>(actual.visitados);
                    nuevosVisitados.add(vecino.ciudad);

                    frontera.add(new Nodo(vecino.ciudad,
                                         actual.g + vecino.distancia,
                                         nuevoCamino, nuevosVisitados));
                }
            }
        }

        return null; // sin solución
    }
}
