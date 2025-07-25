package GestorEjercicios.model;

import Modulo_Ejercicios.exercises.EjercicioSeleccion;

public class DetalleLeccion {
    private EjercicioSeleccion ejercicio;
    private ResultadoEvaluacion resultado;

    public DetalleLeccion(EjercicioSeleccion ejercicio, ResultadoEvaluacion resultado) {
        this.ejercicio = ejercicio;
        this.resultado = resultado;
    }

    public EjercicioSeleccion getEjercicio() { return ejercicio; }
    public ResultadoEvaluacion getResultado() { return resultado; }


}