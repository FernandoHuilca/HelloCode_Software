package GestionContenido_Modulo;

public class Video extends RecursoAprendizaje {
    private final String url;
    private final int duracion; // en segundos

    public Video(String titulo, String url, int duracion) {
        super(titulo);
        this.url = url;
        this.duracion = duracion;
    }

    public Video(String titulo, String url) {
        this(titulo, url, 0); // constructor con duración opcional
    }

    @Override
    public String obtenerDetalle() {
        return String.format("URL: %s (%ds)", url, duracion);
    }

    public String getUrl() {
        return url;
    }

    public int getDuracion() {
        return duracion;
    }
}
