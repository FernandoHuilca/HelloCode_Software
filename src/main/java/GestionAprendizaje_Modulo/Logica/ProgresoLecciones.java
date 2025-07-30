package GestionAprendizaje_Modulo.Logica;

public class ProgresoLecciones {

    private static int leccionesCompletadas = 0;

    public static void incrementarLeccionesCompletadas() {
        leccionesCompletadas++;
    }

    public static int getLeccionesCompletadas() {
        return leccionesCompletadas;
    }
}