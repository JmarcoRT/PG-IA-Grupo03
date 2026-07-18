import java.util.*;

/**
 * BusquedaRutasPeru.java
 * Clase principal del programa. Responsabilidades:
 *   1. Construir el grafo con las 12 ciudades y 17 conexiones del mapa peruano.
 *   2. Solicitar al usuario la ciudad de origen y la de destino.
 *   3. Ejecutar los cuatro algoritmos (BFS, DFS, Greedy, A*).
 *   4. Mostrar por consola los resultados individuales y la tabla comparativa.
 *
 * Ejecución:
 *   javac *.java
 *   java BusquedaRutasPeru
 */
public class BusquedaRutasPeru {

    public static void main(String[] args) {

        // ──────────────────────────────────────────────
        // 1. CONSTRUCCIÓN DEL GRAFO
        // ──────────────────────────────────────────────
        Grafo mapa = new Grafo();

        // Aristas (bidireccionales) con distancias en km
        mapa.addArista("Lima",      "Trujillo",  557);
        mapa.addArista("Lima",      "Ica",        303);
        mapa.addArista("Lima",      "Huancayo",   298);
        mapa.addArista("Trujillo",  "Chiclayo",   209);
        mapa.addArista("Trujillo",  "Cajamarca",  295);
        mapa.addArista("Chiclayo",  "Piura",      241);
        mapa.addArista("Cajamarca", "Piura",      448);
        mapa.addArista("Cajamarca", "Pucallpa",   940);
        mapa.addArista("Ica",       "Arequipa",   718);
        mapa.addArista("Huancayo",  "Pucallpa",   730);
        mapa.addArista("Huancayo",  "Cusco",      736);
        mapa.addArista("Arequipa",  "Cusco",      521);
        mapa.addArista("Arequipa",  "Tacna",      319);
        mapa.addArista("Arequipa",  "Puno",       297);
        mapa.addArista("Cusco",     "Puno",       388);
        mapa.addArista("Cusco",     "Pucallpa",   833);
        mapa.addArista("Puno",      "Tacna",      377);

        // ──────────────────────────────────────────────
        // 2. ENTRADA DEL USUARIO
        // ──────────────────────────────────────────────
        Scanner sc = new Scanner(System.in);

        System.out.println("============================================");
        System.out.println("    BUSQUEDA DE RUTAS EN EL PERU - IA      ");
        System.out.println("    Grupo 03 | UNMSM-FISI | 2026-I         ");
        System.out.println("============================================");
        System.out.println("\nCiudades disponibles:");
        System.out.println("  " + String.join(", ", mapa.getCiudades()));
        System.out.println();

        String origen  = leerCiudad(sc, mapa, "Ingrese ciudad de origen : ");
        String destino = leerCiudad(sc, mapa, "Ingrese ciudad de destino: ");

        if (origen.equals(destino)) {
            System.out.println("\nOrigen y destino son la misma ciudad. "
                             + "No se requiere busqueda.");
            sc.close();
            return;
        }

        System.out.println("\n--------------------------------------------");
        System.out.println("Buscando ruta de [" + origen + "] a [" + destino + "]");
        System.out.println("--------------------------------------------\n");

        // ──────────────────────────────────────────────
        // 3. EJECUCIÓN DE LOS CUATRO ALGORITMOS
        // ──────────────────────────────────────────────
        Resultado resBFS    = new BFS().buscar(mapa, origen, destino);
        Resultado resDFS    = new DFS().buscar(mapa, origen, destino);
        Resultado resGreedy = new Greedy().buscar(mapa, origen, destino);
        Resultado resAStar  = new AStar().buscar(mapa, origen, destino);

        // ──────────────────────────────────────────────
        // 4. RESULTADOS INDIVIDUALES
        // ──────────────────────────────────────────────
        imprimirResultado("BFS (no informada)",           resBFS);
        imprimirResultado("DFS (no informada)",           resDFS);
        imprimirResultado("Greedy Best-First (informada)", resGreedy);
        imprimirResultado("A* (informada)",               resAStar);

        // ──────────────────────────────────────────────
        // 5. TABLA COMPARATIVA
        // ──────────────────────────────────────────────
        System.out.println("============================================");
        System.out.println("           TABLA COMPARATIVA                ");
        System.out.println("============================================");
        System.out.printf("%-10s  %-7s  %-11s  %-9s  %-10s  %-7s%n",
                          "Algoritmo", "Nodos", "Costo(km)", "Tiempo",
                          "Completo", "Optimo");
        System.out.println("--------------------------------------------");
        imprimirFila("BFS",    resBFS,    "Si", "No");
        imprimirFila("DFS",    resDFS,    "Si", "No");
        imprimirFila("Greedy", resGreedy, "No", "No");
        imprimirFila("A*",     resAStar,  "Si", "Si");
        System.out.println("============================================");
        System.out.println("\n* Completo: garantiza encontrar solucion si existe.");
        System.out.println("* Optimo  : garantiza la solucion de menor costo total.");
        System.out.println("  (BFS es optimo solo en grafos sin pesos; aqui no aplica.)");

        sc.close();
    }

    // ──────────────────────────────────────────────────────────
    // MÉTODOS AUXILIARES
    // ──────────────────────────────────────────────────────────

    /** Lee una ciudad válida desde consola, repitiendo hasta obtener una correcta. */
    private static String leerCiudad(Scanner sc, Grafo mapa, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String ciudad = sc.nextLine().trim();
            if (mapa.getCiudades().contains(ciudad)) {
                return ciudad;
            }
            System.out.println("  [!] Ciudad no encontrada. Verifique mayusculas "
                             + "y tildes. Intente de nuevo.");
        }
    }

    /** Imprime los resultados de un algoritmo en formato legible. */
    private static void imprimirResultado(String nombre, Resultado r) {
        System.out.println("--- " + nombre + " ---");
        if (r == null) {
            System.out.println("  No se encontro ruta.\n");
        } else {
            System.out.println("  Ruta: " + String.join(" -> ", r.ruta));
            System.out.printf("  Costo: %.0f km | Nodos expandidos: %d | Tiempo: %d ms%n%n",
                              r.costo, r.nodosExpandidos, r.tiempoMs);
        }
    }

    /** Imprime una fila de la tabla comparativa con formato alineado. */
    private static void imprimirFila(String nombre, Resultado r,
                                     String completo, String optimo) {
        if (r == null) {
            System.out.printf("%-10s  %-7s  %-11s  %-9s  %-10s  %-7s%n",
                              nombre, "N/A", "N/A", "N/A", completo, optimo);
        } else {
            String tiempoStr = r.tiempoMs + " ms";
            System.out.printf("%-10s  %-7d  %-11.0f  %-9s  %-10s  %-7s%n",
                              nombre, r.nodosExpandidos, r.costo,
                              tiempoStr, completo, optimo);
        }
    }
}
