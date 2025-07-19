package model;

import java.util.List;

public class Leccion {
    private int id;
    private String nombre;
    private List<model.DetalleLeccion> ejerciciosResueltos;

    public Leccion(int id, String nombre, List<model.DetalleLeccion> ejerciciosResueltos) {
        this.id = id;
        this.nombre = nombre;
        this.ejerciciosResueltos = ejerciciosResueltos;
    }

    public List<model.DetalleLeccion> getEjerciciosResueltos() {
        return ejerciciosResueltos;
    }

    public String obtenerResumen() {
        return "Lección '" + nombre + "' con " + ejerciciosResueltos.size() + " ejercicios.";
    }
}