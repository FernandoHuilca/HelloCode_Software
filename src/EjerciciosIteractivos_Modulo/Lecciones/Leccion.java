package EjerciciosIteractivos_Modulo.Lecciones;

import EjerciciosIteractivos_Modulo.Ejercicio;

import java.util.ArrayList;

public class Leccion {
    ArrayList<Ejercicio> listaEjercicios;

    //Atributos que sugerimos tenga una lección
    private String nombre; //De que va la lección en general?


    public Leccion(String nombre) {
        this.nombre = nombre;
        this.listaEjercicios = new ArrayList<>();
    }



    public String getNombre() {
        return nombre;
    }

    public int getNumEjercicios() {
        return listaEjercicios.size();
    }

    public Ejercicio getEjercicio(int numEjercicio) {
        return listaEjercicios.get(numEjercicio);
    }

    public boolean addEjercicio(Ejercicio nuevoEjercicio) {
        return listaEjercicios.add(nuevoEjercicio);
    }
}
