import java.util.*;

/**
 * Heuristica.java
 * Calcula la distancia en línea recta entre dos ciudades usando la
 * fórmula de Haversine sobre las coordenadas geográficas reales
 * (latitud y longitud en grados decimales).
 *
 * La distancia en línea recta es siempre <= distancia real por carretera,
 * por lo que la heurística es ADMISIBLE y CONSISTENTE, lo que garantiza
 * la optimalidad de A*.
 */
public class Heuristica {

    private static final double RADIO_TIERRA_KM = 6371.0;

    // Coordenadas geográficas de cada ciudad: {latitud, longitud}
    private static final Map<String, double[]> COORDENADAS = new HashMap<>();

    static {
        COORDENADAS.put("Lima",      new double[]{-12.0464, -77.0428});
        COORDENADAS.put("Trujillo",  new double[]{ -8.1120, -79.0288});
        COORDENADAS.put("Arequipa",  new double[]{-16.4090, -71.5375});
        COORDENADAS.put("Cusco",     new double[]{-13.5320, -71.9675});
        COORDENADAS.put("Piura",     new double[]{ -5.1945, -80.6328});
        COORDENADAS.put("Tacna",     new double[]{-18.0060, -70.2481});
        COORDENADAS.put("Ica",       new double[]{-14.0678, -75.7286});
        COORDENADAS.put("Puno",      new double[]{-15.8402, -70.0219});
        COORDENADAS.put("Chiclayo",  new double[]{ -6.7714, -79.8409});
        COORDENADAS.put("Huancayo",  new double[]{-12.0653, -75.2049});
        COORDENADAS.put("Cajamarca", new double[]{ -7.1620, -78.5127});
        COORDENADAS.put("Pucallpa",  new double[]{ -8.3791, -74.5539});
    }

    /**
     * Devuelve la distancia en km entre dos ciudades usando Haversine.
     * Retorna 0 si alguna ciudad no está registrada.
     *
     * Fórmula:
     *   a = sin²(Δlat/2) + cos(lat1)·cos(lat2)·sin²(Δlon/2)
     *   d = 2 · R · arcsin(√a)
     */
    public static double distanciaLineaRecta(String ciudad1, String ciudad2) {
        double[] c1 = COORDENADAS.get(ciudad1);
        double[] c2 = COORDENADAS.get(ciudad2);
        if (c1 == null || c2 == null) return 0.0;

        double lat1 = Math.toRadians(c1[0]);
        double lon1 = Math.toRadians(c1[1]);
        double lat2 = Math.toRadians(c2[0]);
        double lon2 = Math.toRadians(c2[1]);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2)
                 + Math.cos(lat1) * Math.cos(lat2)
                   * Math.pow(Math.sin(dLon / 2), 2);

        return 2 * RADIO_TIERRA_KM * Math.asin(Math.sqrt(a));
    }
}
