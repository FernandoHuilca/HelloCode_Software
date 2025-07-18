package Comunidad_Modulo.core;

public class Comunidad {
    private String nombre;
    private String descripcion;
    private int numeroMiembros;

    public Comunidad(String nombre, String descripcion, int numeroMiembros) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.numeroMiembros = numeroMiembros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroMiembros() {
        return numeroMiembros;
    }

    public void setNumeroMiembros(int numeroMiembros) {
        this.numeroMiembros = numeroMiembros;
    }
}
