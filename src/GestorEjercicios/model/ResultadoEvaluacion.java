package model;

public class ResultadoEvaluacion {
    private boolean esCorrecto;
    private int puntuacionObtenida;
    private long tiempoEmpleado;
    private String obtenerMensaje;
    private boolean hayEjercicioSiguiente;

    public ResultadoEvaluacion(boolean esCorrecto, int puntuacionObtenida, long tiempoEmpleado, String obtenerMensaje, boolean hayEjercicioSiguiente) {
        this.esCorrecto = esCorrecto;
        this.puntuacionObtenida = puntuacionObtenida;
        this.tiempoEmpleado = tiempoEmpleado;
        this.obtenerMensaje = obtenerMensaje;
        this.hayEjercicioSiguiente = hayEjercicioSiguiente;
    }

    public boolean isCorrecto() { return esCorrecto; }
    public int getPuntuacionObtenida() { return puntuacionObtenida; }
    public String getObtenerMensaje() { return obtenerMensaje; }

    public String getTiempoFormateado() {
        long segundos = tiempoEmpleado / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%02d min %02d seg", minutos, segundos);
    }
    public static boolean evaluarLeccion(int totalEjercicios, int ejerciciosCorrectos) {
        double porcentaje = (ejerciciosCorrectos * 100.0) / totalEjercicios;
        return porcentaje >= 60.0;
    }
}