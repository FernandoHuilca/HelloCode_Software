package GestionAprendizaje_ModuloIsaac.Ruta;

import GestionAprendizaje_ModuloIsaac.Modelo.Leccion;

public class NodoRuta {
    private int orden;
    private boolean completado;
    private Leccion leccion;

    public NodoRuta(int orden, Leccion leccion) {
        this.orden = orden;
        this.leccion = leccion;
        this.completado = false;
    }

    public void marcarComoCompletado() {
        this.completado = true;
    }

    public Leccion getLeccion() {
        return leccion;
    }

    public boolean estaCompletado() {
        return completado;
    }

    public int getOrden() {
        return orden;
    }

    @Override
    public String toString() {
        return "NodoRuta{" +
                "orden=" + orden +
                ", completado=" + completado +
                ", leccionTest=" + leccion.getTitulo() +
                '}';
    }
}
