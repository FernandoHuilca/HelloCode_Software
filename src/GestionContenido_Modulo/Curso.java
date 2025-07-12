package GestionContenido_Modulo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Curso {
    private final String id;
    private String nombre;
    private String descripcion;
    private final Date fechaCreacion;
    private final List<ModuloEducativo> modulos;

    public Curso(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = new Date();
        this.modulos = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Date getFechaCreacion() { return new Date(fechaCreacion.getTime()); }
    public List<ModuloEducativo> getModulosEducativos() { return new ArrayList<>(modulos); }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // Operaciones
    public void agregarModuloEducativo(ModuloEducativo modulo) {
        modulos.add(modulo);
    }

    public List<ModuloEducativo> obtenerModulosEducativos() {
        return new ArrayList<>(modulos);
    }
}
