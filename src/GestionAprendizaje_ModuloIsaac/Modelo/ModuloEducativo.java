package GestionAprendizaje_Modulo.Modelo;

import java.util.UUID;

public class ModuloEducativo {
    private String id;
    private String titulo;
    private String version;
    private String estado;

    public ModuloEducativo(String titulo, String version, String estado) {
        this.id = UUID.randomUUID().toString(); // Genera id único automáticamente
        this.titulo = titulo;
        this.version = version;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getVersion() {
        return version;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "ModuloEducativo{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", version='" + version + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}