import java.util.*;

/**
 * DFS.java
 * Implementa la Búsqueda en Profundidad (Depth-First Search).
 *
 * Estrategia: explora una rama completa antes de retroceder, usando una pila LIFO.
 * No usa heurística. Puede encontrar soluciones muy costosas porque no considera
 * los pesos de las aristas al decidir qué camino explorar.
 *
 * Completitud : Sí (grafo finito con detección de ciclos por camino)
 * Optimalidad : No
 * Complejidad : O(b·m) espacio (m = profundidad máxima), O(b^m) tiempo en peor caso
 */
public class DFS {

    public Resultado buscar(Grafo grafo, String origen, String destino) {
        long inicio = System.currentTimeMillis();
        int  nodosExpandidos = 0;

        // Pila LIFO: produce la exploración en profundidad
        Deque<Nodo> pila = new ArrayDeque<>();

        List<String> caminoInicial    = new ArrayList<>(List.of(origen));
        Set<String>  visitadosInicial = new HashSet<>(Set.of(origen));
        pila.push(new Nodo(origen, 0.0, caminoInicial, visitadosInicial));

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop(); // extrae el nodo más reciente (LIFO)
            nodosExpandidos++;

            // Verificar si llegamos a la meta
            if (actual.ciudad.equals(destino)) {
                long tiempo = System.currentTimeMillis() - inicio;
                return new Resultado(actual.camino, actual.g,
                                     nodosExpandidos, tiempo);
            }

            // Expandir vecinos en orden inverso para mantener el orden
            // de exploración consistente con el orden de inserción en el grafo
            List<Par> vecinos = grafo.getVecinos(actual.ciudad);
            for (int i = vecinos.size() - 1; i >= 0; i--) {
                Par vecino = vecinos.get(i);
                if (!actual.visitados.contains(vecino.ciudad)) {
                    List<String> nuevoCamino = new ArrayList<>(actual.camino);
                    nuevoCamino.add(vecino.ciudad);

                    Set<String> nuevosVisitados = new HashSet<>(actual.visitados);
                    nuevosVisitados.add(vecino.ciudad);

                    pila.push(new Nodo(vecino.ciudad,
                                       actual.g + vecino.distancia,
                                       nuevoCamino, nuevosVisitados));
                }
            }
        }

        return null; // sin solución
    }
}
