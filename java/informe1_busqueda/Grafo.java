import java.util.*;

/**
 * Grafo.java
 * Representa el mapa de ciudades como un grafo no dirigido y ponderado.
 * Internamente usa un Map<String, List<Par>> (lista de adyacencia).
 * Cada Par almacena la ciudad vecina y la distancia en km de la arista.
 */
public class Grafo {

    // Mapa de adyacencia: ciudad -> lista de (vecino, distancia)
    private final Map<String, List<Par>> adyacencias;

    public Grafo() {
        // LinkedHashMap para preservar el orden de inserción al listar ciudades
        adyacencias = new LinkedHashMap<>();
    }

    /**
     * Agrega una arista bidireccional entre c1 y c2 con el costo indicado.
     * Si alguna de las ciudades no existe aún, la crea automáticamente.
     */
    public void addArista(String c1, String c2, int distancia) {
        adyacencias.computeIfAbsent(c1, k -> new ArrayList<>()).add(new Par(c2, distancia));
        adyacencias.computeIfAbsent(c2, k -> new ArrayList<>()).add(new Par(c1, distancia));
    }

    /**
     * Devuelve la lista de vecinos (ciudad + distancia) de una ciudad dada.
     * Retorna lista vacía si la ciudad no existe en el grafo.
     */
    public List<Par> getVecinos(String ciudad) {
        return adyacencias.getOrDefault(ciudad, Collections.emptyList());
    }

    /** Devuelve el conjunto de ciudades registradas en el grafo. */
    public Set<String> getCiudades() {
        return adyacencias.keySet();
    }
}
