package GestionContenido_Modulo;

import java.util.UUID;

public abstract class RecursoAprendizaje {
    private final String id;
    private final String titulo;

    public RecursoAprendizaje(String titulo) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getId() {
        return id;
    }

    // Método obligatorio para todas las subclases
    public abstract String obtenerDetalle();

    @Override
    public String toString() {
        return String.format("%s: %s", this.getClass().getSimpleName(), titulo);
    }
}
