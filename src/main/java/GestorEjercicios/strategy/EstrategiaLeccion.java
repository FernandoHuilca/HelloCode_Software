package GestorEjercicios.strategy;

import GestorEjercicios.model.DetalleLeccion;
import GestorEjercicios.model.Leccion;
import Modulo_Ejercicios.exercises.EjercicioSeleccion;  // Importar EjercicioSeleccion

import java.util.List;

public interface EstrategiaLeccion {
    Leccion crearLeccion(String nombre, List<EjercicioSeleccion> ejercicios); // Cambiar a List<EjercicioSeleccion>
    //void procesarRespuesta(DetalleLeccion detalle, String respuestaUsuario);
    List<EjercicioSeleccion> obtenerEjerciciosPendientes();  // Cambiar a List<EjercicioSeleccion>
    boolean tieneEjerciciosPendientes();
}
