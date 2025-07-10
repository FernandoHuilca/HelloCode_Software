package EjerciciosIteractivos_Modulo;

import java.util.ArrayList;

public class EjercicioSeleccion extends Ejercicio {
    private ArrayList<String> opciones;
    private ArrayList<String> respuestas;

    public EjercicioSeleccion(String enunciado, ArrayList<String> opciones, ArrayList<String> respuestas) {
        super(enunciado);

        this.opciones = opciones;
        this.respuestas = respuestas;
    }
    public ArrayList<String> getListOpciones() {
        return opciones;
    }

    public String getOpcion(int numOpcion) {
        return opciones.get(numOpcion);
    }
}
