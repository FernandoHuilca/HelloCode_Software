package GestionAprendizaje_ModuloIsaac.Modelo;

public class Leccion {
    private String id;
    private String titulo;
    private String descripcion;
    private String estado;

    public Leccion(String id, String titulo, String descripcion, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return "Leccion{" +
                "titulo='" + titulo + '\'' +
                '}';
    }
}

