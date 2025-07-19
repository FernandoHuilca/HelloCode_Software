package model;

import java.util.List;
import enums.TipoLeccion;

public class Leccion {
    private int id;
    private String nombre;
    private TipoLeccion tipo;
    private int conocimiento = 0;
    private List<DetalleLeccion> ejerciciosResueltos;

    public Leccion(int id, String nombre, List<DetalleLeccion> ejerciciosResueltos) {
        this.id = id;
        this.nombre = nombre;
        this.ejerciciosResueltos = ejerciciosResueltos;
    }

    public List<DetalleLeccion> getEjerciciosResueltos() {
        return ejerciciosResueltos;
    }

    public String obtenerResumen() {
        return "Lección '" + nombre + "' con " + ejerciciosResueltos.size() + " ejercicios.";
    }

}