import java.util.*;

/**
 * Nodo.java
 * Representa un estado dentro del árbol de búsqueda.
 *
 * Campos:
 *   ciudad    - ciudad actual del agente
 *   g         - costo acumulado desde el origen hasta esta ciudad
 *   h         - estimación heurística del costo restante hasta la meta
 *   f         - función de evaluación: f = g + h
 *               (para BFS/DFS h=0, por lo que f = g)
 *   camino    - lista de ciudades recorridas desde el origen hasta aquí
 *   visitados - conjunto de ciudades ya en el camino (para detección de ciclos
 *               en BFS y DFS; en Greedy y A* se usa un conjunto "cerrados" externo)
 *
 * Implementa Comparable<Nodo> para ser usado en PriorityQueue (Greedy y A*).
 */
public class Nodo implements Comparable<Nodo> {

    public String       ciudad;
    public double       g;
    public double       h;
    public double       f;
    public List<String> camino;
    public Set<String>  visitados;

    /**
     * Constructor para BFS y DFS.
     * No se emplea heurística: h = 0, f = g.
     */
    public Nodo(String ciudad, double g,
                List<String> camino, Set<String> visitados) {
        this.ciudad    = ciudad;
        this.g         = g;
        this.h         = 0;
        this.f         = g;
        this.camino    = camino;
        this.visitados = visitados;
    }

    /**
     * Constructor para Greedy Best-First y A*.
     * f = g + h; visitados se deduce del propio camino.
     */
    public Nodo(String ciudad, double g, double h, List<String> camino) {
        this.ciudad    = ciudad;
        this.g         = g;
        this.h         = h;
        this.f         = g + h;
        this.camino    = camino;
        this.visitados = new HashSet<>(camino);
    }

    /** Comparación por f(n) para la cola de prioridad. */
    @Override
    public int compareTo(Nodo otro) {
        return Double.compare(this.f, otro.f);
    }
}
