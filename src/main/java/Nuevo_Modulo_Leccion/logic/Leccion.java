package Nuevo_Modulo_Leccion.logic;

import Modulo_Ejercicios.exercises.EjercicioBase;

import java.util.ArrayList;
import java.util.List;

public class Leccion {
    //Una lección tiene o está compuesta por varios ejercicios:
    List<EjercicioBase> listEjercicios;

    public Leccion() {
        listEjercicios = new ArrayList<EjercicioBase>();
    }



    public void agregarEjercicio(EjercicioBase nuevoEjercicio) {
        listEjercicios.add(nuevoEjercicio);
    }

    public int getNumEjercicios() {
        return listEjercicios.size();
    }
}
