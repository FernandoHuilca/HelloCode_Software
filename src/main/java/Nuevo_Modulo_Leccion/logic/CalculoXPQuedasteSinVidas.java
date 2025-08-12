package Nuevo_Modulo_Leccion.logic;

public class CalculoXPQuedasteSinVidas implements CalculoXP {
    private long tiempoDeLeccion; // en ms
    private final int XP_MINIMA = 50;   // algo simbólico
    private final int XP_MAXIMA = 75;  // si fue muy rápido
    private final long TIEMPO_OPTIMO = 60_000;   // 1 minuto
    private final long TIEMPO_MAXIMO = 300_000;  // 5 minutos

    public CalculoXPQuedasteSinVidas(long tiempoTranscurridoLeccion) {
        this.tiempoDeLeccion = tiempoTranscurridoLeccion;
    }

    @Override
    public int calcularXP() {
        if (tiempoDeLeccion <= 0) {
            return XP_MINIMA;
        }

        if (tiempoDeLeccion <= TIEMPO_OPTIMO) {
            // Fue rápido pero perdió vidas
            double factor = 1 - ((double) tiempoDeLeccion / TIEMPO_OPTIMO);
            return (int) (XP_MINIMA + (XP_MAXIMA - XP_MINIMA) * factor);
        } else if (tiempoDeLeccion <= TIEMPO_MAXIMO) {
            // Tardó más, pero igual recibe algo
            double factor = 1 - ((double) (tiempoDeLeccion - TIEMPO_OPTIMO) / (TIEMPO_MAXIMO - TIEMPO_OPTIMO));
            return (int) (XP_MINIMA + (XP_MAXIMA - XP_MINIMA) * Math.max(factor, 0.1));
        } else {
            // Muy lento
            return XP_MINIMA;
        }
    }
}
