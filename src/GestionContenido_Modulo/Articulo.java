package GestionContenido_Modulo;

public class Articulo extends RecursoAprendizaje {
    private final String contenido;
    private final String autor;

    public Articulo(String titulo, String contenido, String autor) {
        super(titulo);
        this.contenido = contenido;
        this.autor = autor;
    }

    @Override
    public String obtenerDetalle() {
        return String.format("Autor: %s, Contenido: %s", autor, contenido);
    }

    public String getContenido() {
        return contenido;
    }

    public String getAutor() {
        return autor;
    }
}