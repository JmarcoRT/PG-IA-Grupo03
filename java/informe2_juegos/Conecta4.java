import java.util.Scanner;

/**
 * Conecta 4 - Humano vs Maquina
 * Algoritmo: Minimax con poda Alfa-Beta, profundidad limite = 6
 * Heuristica: centrado de columna + evaluacion de ventanas de 4 + bloqueo de
 * amenazas Grupo 03 - IA Seccion 21
 */

public class Conecta4 {

    static final int FILAS = 6;
    static final int COLUMNAS = 7;
    static final char VACIO = '.';
    static final char HUMANO = 'X';
    static final char MAQUINA = 'O';
    static final int PROFUNDIDAD_LIMITE = 6;

    // Contadores globales de nodos evaluados (se reinician en cada jugada de la
    // maquina)
    static long nodosConPoda = 0;
    static long nodosSinPoda = 0;

    char[][] tablero;

    public Conecta4() {
        tablero = new char[FILAS][COLUMNAS];
        for (char[] fila : tablero)
            java.util.Arrays.fill(fila, VACIO);
    }

    // ---------- Utilidades de tablero ----------

    void imprimirTablero() {
        System.out.println();
        for (int f = 0; f < FILAS; f++) {
            System.out.print("| ");
            for (int c = 0; c < COLUMNAS; c++) {
                System.out.print(tablero[f][c] + " | ");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int c = 0; c < COLUMNAS; c++)
            System.out.print((c + 1) + "   ");
        System.out.println("\n");
    }

    boolean columnaValida(char[][] t, int col) {
        return col >= 0 && col < COLUMNAS && t[0][col] == VACIO;
    }

    // Deja caer una ficha en la columna; retorna la fila donde cayo, o -1 si la
    // columna esta llena
    int soltarFicha(char[][] t, int col, char ficha) {
        for (int f = FILAS - 1; f >= 0; f--) {
            if (t[f][col] == VACIO) {
                t[f][col] = ficha;
                return f;
            }
        }
        return -1;
    }

    // Deshace la ultima ficha soltada en una columna, dejando la casilla vacia
    // otra vez (usado por backtracking en Minimax, en lugar de copiar el tablero)
    void quitarFicha(char[][] t, int col, int fila) {
        t[fila][col] = VACIO;
    }

    boolean tableroLleno(char[][] t) {
        for (int c = 0; c < COLUMNAS; c++)
            if (t[0][c] == VACIO)
                return false;
        return true;
    }

    // Verifica si "ficha" tiene 4 en linea en algun lugar del tablero
    boolean hayGanador(char[][] t, char ficha) {
        // Horizontal
        for (int f = 0; f < FILAS; f++)
            for (int c = 0; c <= COLUMNAS - 4; c++)
                if (t[f][c] == ficha && t[f][c + 1] == ficha && t[f][c + 2] == ficha && t[f][c + 3] == ficha)
                    return true;
        // Vertical
        for (int f = 0; f <= FILAS - 4; f++)
            for (int c = 0; c < COLUMNAS; c++)
                if (t[f][c] == ficha && t[f + 1][c] == ficha && t[f + 2][c] == ficha && t[f + 3][c] == ficha)
                    return true;
        // Diagonal descendente (\)
        for (int f = 0; f <= FILAS - 4; f++)
            for (int c = 0; c <= COLUMNAS - 4; c++)
                if (t[f][c] == ficha && t[f + 1][c + 1] == ficha && t[f + 2][c + 2] == ficha
                        && t[f + 3][c + 3] == ficha)
                    return true;
        // Diagonal ascendente (/)
        for (int f = 3; f < FILAS; f++)
            for (int c = 0; c <= COLUMNAS - 4; c++)
                if (t[f][c] == ficha && t[f - 1][c + 1] == ficha && t[f - 2][c + 2] == ficha
                        && t[f - 3][c + 3] == ficha)
                    return true;
        return false;
    }

    // ---------- Funcion de evaluacion heuristica ----------

    // Evalua una ventana de 4 celdas para el jugador "ficha"
    int evaluarVentana(char[] ventana, char ficha) {
        char rival = (ficha == MAQUINA) ? HUMANO : MAQUINA;
        int puntaje = 0;
        int contFicha = 0, contRival = 0, contVacio = 0;
        for (char v : ventana) {
            if (v == ficha)
                contFicha++;
            else if (v == rival)
                contRival++;
            else
                contVacio++;
        }

        if (contFicha == 4)
            puntaje += 100000;
        else if (contFicha == 3 && contVacio == 1)
            puntaje += 100;
        else if (contFicha == 2 && contVacio == 2)
            puntaje += 10;

        // Bloqueo de amenazas: penaliza fuerte si el rival tiene 3 en linea con espacio
        // libre
        if (contRival == 3 && contVacio == 1)
            puntaje -= 120;
        else if (contRival == 2 && contVacio == 2)
            puntaje -= 8;

        return puntaje;
    }

    int evaluarTablero(char[][] t, char ficha) {
        int puntaje = 0;

        // Preferencia por el centro: las fichas centrales participan en mas lineas
        // posibles
        int colCentro = COLUMNAS / 2;
        int contCentro = 0;
        for (int f = 0; f < FILAS; f++)
            if (t[f][colCentro] == ficha)
                contCentro++;
        puntaje += contCentro * 6;

        // Ventanas horizontales
        for (int f = 0; f < FILAS; f++)
            for (int c = 0; c <= COLUMNAS - 4; c++) {
                char[] ventana = { t[f][c], t[f][c + 1], t[f][c + 2], t[f][c + 3] };
                puntaje += evaluarVentana(ventana, ficha);
            }
        // Ventanas verticales
        for (int c = 0; c < COLUMNAS; c++)
            for (int f = 0; f <= FILAS - 4; f++) {
                char[] ventana = { t[f][c], t[f + 1][c], t[f + 2][c], t[f + 3][c] };
                puntaje += evaluarVentana(ventana, ficha);
            }
        // Diagonales descendentes (\)
        for (int f = 0; f <= FILAS - 4; f++)
            for (int c = 0; c <= COLUMNAS - 4; c++) {
                char[] ventana = { t[f][c], t[f + 1][c + 1], t[f + 2][c + 2], t[f + 3][c + 3] };
                puntaje += evaluarVentana(ventana, ficha);
            }
        // Diagonales ascendentes (/)
        for (int f = 3; f < FILAS; f++)
            for (int c = 0; c <= COLUMNAS - 4; c++) {
                char[] ventana = { t[f][c], t[f - 1][c + 1], t[f - 2][c + 2], t[f - 3][c + 3] };
                puntaje += evaluarVentana(ventana, ficha);
            }

        return puntaje;
    }

    // ---------- Minimax con poda Alfa-Beta ----------

    int minimaxAlfaBeta(char[][] t, int profundidad, int alfa, int beta, boolean esMaximizador) {
        nodosConPoda++;

        boolean ganaMaquina = hayGanador(t, MAQUINA);
        boolean ganaHumano = hayGanador(t, HUMANO);
        boolean lleno = tableroLleno(t);

        if (ganaMaquina)
            return 10_000_000 - (PROFUNDIDAD_LIMITE - profundidad);
        if (ganaHumano)
            return -10_000_000 + (PROFUNDIDAD_LIMITE - profundidad);
        if (lleno)
            return 0;
        if (profundidad == 0)
            return evaluarTablero(t, MAQUINA);

        if (esMaximizador) {
            int mejor = Integer.MIN_VALUE;
            for (int c = 0; c < COLUMNAS; c++) {
                if (!columnaValida(t, c))
                    continue;
                int fila = soltarFicha(t, c, MAQUINA);
                int valor = minimaxAlfaBeta(t, profundidad - 1, alfa, beta, false);
                quitarFicha(t, c, fila); // backtracking: se deshace la jugada
                mejor = Math.max(mejor, valor);
                alfa = Math.max(alfa, valor);
                if (alfa >= beta)
                    break; // poda beta
            }
            return mejor;
        } else {
            int mejor = Integer.MAX_VALUE;
            for (int c = 0; c < COLUMNAS; c++) {
                if (!columnaValida(t, c))
                    continue;
                int fila = soltarFicha(t, c, HUMANO);
                int valor = minimaxAlfaBeta(t, profundidad - 1, alfa, beta, true);
                quitarFicha(t, c, fila); // backtracking: se deshace la jugada
                mejor = Math.min(mejor, valor);
                beta = Math.min(beta, valor);
                if (alfa >= beta)
                    break; // poda alfa
            }
            return mejor;
        }
    }

    // Minimax puro, SIN poda (solo para comparar el numero de nodos evaluados y
    // confirmar empiricamente que la decision final coincide con la versioncon poda
    // Alfa-Beta)

    int minimaxSinPoda(char[][] t, int profundidad, boolean esMaximizador) {
        nodosSinPoda++;

        boolean ganaMaquina = hayGanador(t, MAQUINA);
        boolean ganaHumano = hayGanador(t, HUMANO);
        boolean lleno = tableroLleno(t);

        if (ganaMaquina)
            return 10_000_000 - (PROFUNDIDAD_LIMITE - profundidad);
        if (ganaHumano)
            return -10_000_000 + (PROFUNDIDAD_LIMITE - profundidad);
        if (lleno)
            return 0;
        if (profundidad == 0)
            return evaluarTablero(t, MAQUINA);

        if (esMaximizador) {
            int mejor = Integer.MIN_VALUE;
            for (int c = 0; c < COLUMNAS; c++) {
                if (!columnaValida(t, c))
                    continue;
                int fila = soltarFicha(t, c, MAQUINA);
                int valor = minimaxSinPoda(t, profundidad - 1, false);
                quitarFicha(t, c, fila); // backtracking: se deshace la jugada
                mejor = Math.max(mejor, valor);
            }
            return mejor;
        } else {
            int mejor = Integer.MAX_VALUE;
            for (int c = 0; c < COLUMNAS; c++) {
                if (!columnaValida(t, c))
                    continue;
                int fila = soltarFicha(t, c, HUMANO);
                int valor = minimaxSinPoda(t, profundidad - 1, true);
                quitarFicha(t, c, fila); // backtracking: se deshace la jugada
                mejor = Math.min(mejor, valor);
            }
            return mejor;
        }
    }

    // Elige la mejor columna para la maquina y reporta nodos evaluados con y sin
    // poda
    // Ademas, calcula por separado la columna que elegiria Minimax puro (sin poda)
    // y verifica empiricamente si coincide con la elegida por la version con poda
    // Alfa-Beta.

    int elegirJugadaMaquina() {
        nodosConPoda = 0;
        nodosSinPoda = 0;

        // --- Pasada CON poda: decide la jugada real de la maquina ---
        int mejorColumna = -1;
        int mejorValor = Integer.MIN_VALUE;
        int alfa = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;

        for (int c = 0; c < COLUMNAS; c++) {
            if (!columnaValida(tablero, c))
                continue;
            int fila = soltarFicha(tablero, c, MAQUINA);
            int valor = minimaxAlfaBeta(tablero, PROFUNDIDAD_LIMITE - 1, alfa, beta, false);
            quitarFicha(tablero, c, fila); // backtracking: se deshace la jugada
            if (valor > mejorValor) {
                mejorValor = valor;
                mejorColumna = c;
            }
            alfa = Math.max(alfa, valor);
        }

        // --- Pasada SIN poda: solo con fines comparativos (conteo de nodos y
        // verificacion empirica de que la jugada elegida es la misma) ---
        int mejorColumnaSinPoda = -1;
        int mejorValorSinPoda = Integer.MIN_VALUE;

        for (int c = 0; c < COLUMNAS; c++) {
            if (!columnaValida(tablero, c))
                continue;
            int fila = soltarFicha(tablero, c, MAQUINA);
            int valor = minimaxSinPoda(tablero, PROFUNDIDAD_LIMITE - 1, false);
            quitarFicha(tablero, c, fila); // backtracking: se deshace la jugada
            if (valor > mejorValorSinPoda) {
                mejorValorSinPoda = valor;
                mejorColumnaSinPoda = c;
            }
        }

        // Verificacion empirica: ambas pasadas deben producir la misma jugada, ya que
        // exploran el mismo arbol logico de decision y solo difieren en que ramas se
        // descartan por poda.
        if (mejorColumna == mejorColumnaSinPoda) {
            System.out.println("Verificacion: coincide la jugada CON poda (columna " + (mejorColumna + 1)
                    + ") y SIN poda (columna " + (mejorColumnaSinPoda + 1) + ").");
        } else {
            System.out.println("ADVERTENCIA: la jugada CON poda (columna " + (mejorColumna + 1)
                    + ") difiere de la jugada SIN poda (columna " + (mejorColumnaSinPoda + 1)
                    + "). Revisar implementacion de poda Alfa-Beta.");
        }

        return mejorColumna;
    }

    // ---------- Bucle principal del juego ----------

    void jugar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== CONECTA 4 - Humano (X) vs Maquina (O) ===");
        System.out.println("Profundidad de busqueda de la maquina: " + PROFUNDIDAD_LIMITE);
        imprimirTablero();

        boolean turnoHumano = true;

        while (true) {
            if (turnoHumano) {
                int col;
                while (true) {
                    System.out.print("Elige columna (1-7): ");
                    col = sc.nextInt() - 1;
                    if (columnaValida(tablero, col))
                        break;
                    System.out.println("Columna invalida o llena, intenta de nuevo.");
                }
                soltarFicha(tablero, col, HUMANO);
            } else {
                long inicio = System.currentTimeMillis();
                int col = elegirJugadaMaquina();
                long fin = System.currentTimeMillis();
                soltarFicha(tablero, col, MAQUINA);
                System.out.println("La maquina juega en la columna " + (col + 1));
                System.out.println("Nodos evaluados CON poda Alfa-Beta: " + nodosConPoda);
                System.out.println("Nodos evaluados SIN poda (Minimax puro): " + nodosSinPoda);
                System.out.println("Tiempo de calculo: " + (fin - inicio) + " ms");
            }

            imprimirTablero();

            if (hayGanador(tablero, HUMANO)) {
                System.out.println("¡Gana el HUMANO!");
                break;
            }
            if (hayGanador(tablero, MAQUINA)) {
                System.out.println("¡Gana la MAQUINA!");
                break;
            }
            if (tableroLleno(tablero)) {
                System.out.println("Empate. El tablero se lleno.");
                break;
            }

            turnoHumano = !turnoHumano;
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Conecta4().jugar();
    }
}