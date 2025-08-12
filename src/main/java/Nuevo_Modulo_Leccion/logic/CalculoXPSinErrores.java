package Nuevo_Modulo_Leccion.logic;

public class CalculoXPSinErrores implements CalculoXP {
    private long tiempoDeLeccion; // en milisegundos
    private final int XP_MINIMA = 400;
    private final int XP_MAXIMA = 800;
    private final long TIEMPO_OPTIMO = 90_000;   // 1 min 30 seg en ms
    private final long TIEMPO_MAXIMO = 300_000;  // 5 min en ms

    public CalculoXPSinErrores(long tiempoTranscurridoLeccion) {
        this.tiempoDeLeccion = tiempoTranscurridoLeccion;
    }

    @Override
    public int calcularXP() {
        if (tiempoDeLeccion <= 0) {
            return XP_MINIMA;
        }

        if (tiempoDeLeccion <= TIEMPO_OPTIMO) {
            // Rápido → más XP
            double factor = 1 - ((double) tiempoDeLeccion / TIEMPO_OPTIMO);
            return (int) (XP_MINIMA + (XP_MAXIMA - XP_MINIMA) * factor);
        } else if (tiempoDeLeccion <= TIEMPO_MAXIMO) {
            // Tiempo aceptable → decae poco a poco
            double factor = 1 - ((double) (tiempoDeLeccion - TIEMPO_OPTIMO) / (TIEMPO_MAXIMO - TIEMPO_OPTIMO));
            return (int) (XP_MINIMA + (XP_MAXIMA - XP_MINIMA) * Math.max(factor, 0.2));
        } else {
            // Muy lento → mínima XP
            return XP_MINIMA;
        }
    }
}
