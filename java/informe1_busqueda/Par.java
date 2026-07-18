/**
 * Par.java
 * Representa un vecino del grafo: ciudad conectada y distancia en km.
 * Usada internamente por Grafo para almacenar las listas de adyacencia.
 */
public class Par {
    public String ciudad;
    public int distancia;

    public Par(String ciudad, int distancia) {
        this.ciudad    = ciudad;
        this.distancia = distancia;
    }
}
