package GestionAprendizaje_Modulo.Logica;

import java.util.ArrayList;
import java.util.List;

import GestionAprendizaje_Modulo.Modelo.RecursoAprendizaje;
import Nuevo_Modulo_Leccion.logic.Leccion;

/**
 * Representa un único paso en una Ruta. Vincula una Leccion
 * con una lista de materiales de apoyo.
 */
public class NodoRuta {

    private final int orden;
    private boolean completado;
    private final String id;
    private final Leccion leccion; // <-- Utiliza la clase Leccion correcta
    private List<RecursoAprendizaje> materialDeApoyo;

    public NodoRuta(int orden, String id, Leccion leccion) {
        this.orden = orden;
        this.id = id; // <-- Se asigna el ID
        this.leccion = leccion;
        this.materialDeApoyo = new ArrayList<>();
    }

    public void agregarMaterialDeApoyo(RecursoAprendizaje recurso) {
        this.materialDeApoyo.add(recurso);
    }

    // --- Getters y otros métodos ---
    public String getId() { return id; } // <-- GETTER AÑADIDO

    public int getOrden() { return orden; }
    public boolean isCompletado() { return completado; }
    public void marcarComoCompletado() { this.completado = true; }
    public Leccion getLeccion() { return leccion; }
    public List<RecursoAprendizaje> getMaterialDeApoyo() { return materialDeApoyo; }
}
