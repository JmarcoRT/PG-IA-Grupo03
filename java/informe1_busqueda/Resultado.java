import java.util.List;

/**
 * Resultado.java
 * Encapsula la salida de un algoritmo de búsqueda:
 *   - ruta          : secuencia de ciudades desde origen hasta destino
 *   - costo         : distancia total en km
 *   - nodosExpandidos: cuántos nodos se sacaron de la frontera
 *   - tiempoMs      : tiempo de ejecución en milisegundos
 */
public class Resultado {
    public List<String> ruta;
    public double       costo;
    public int          nodosExpandidos;
    public long         tiempoMs;

    public Resultado(List<String> ruta, double costo,
                     int nodosExpandidos, long tiempoMs) {
        this.ruta            = ruta;
        this.costo           = costo;
        this.nodosExpandidos = nodosExpandidos;
        this.tiempoMs        = tiempoMs;
    }
}
