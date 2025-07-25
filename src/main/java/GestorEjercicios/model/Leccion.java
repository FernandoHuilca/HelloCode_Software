package GestorEjercicios.model;

import GestorEjercicios.enums.TipoLeccion;
import GestorEjercicios.strategy.EstrategiaLeccion;
import GestorEjercicios.strategy.EstrategiaLeccionPrueba;
import GestorEjercicios.strategy.FabricaEstrategiasLeccion;
import Modulo_Ejercicios.exercises.EjercicioSeleccion;

import java.util.List;

public class Leccion {
    private int id;
    private String nombre;
    private List<EjercicioSeleccion> ejerciciosSeleccion; // Cambiar de DetalleLeccion a EjercicioSeleccion
    private TipoLeccion tipo;
    private EstrategiaLeccion estrategia;

    public Leccion(int id, String nombre, List<EjercicioSeleccion> ejerciciosSeleccion) {
        this.id = id;
        this.nombre = nombre;
        this.ejerciciosSeleccion = ejerciciosSeleccion;
        this.tipo = TipoLeccion.NORMAL; // Por defecto
        this.estrategia = FabricaEstrategiasLeccion.crearEstrategia(this.tipo);
    }

    public int getId() {
        return id;
    }

    public Leccion(int id, String nombre, List<EjercicioSeleccion> ejerciciosSeleccion, TipoLeccion tipo) {
        this.id = id;
        this.nombre = nombre;
        this.ejerciciosSeleccion = ejerciciosSeleccion;
        this.tipo = tipo;
        this.estrategia = FabricaEstrategiasLeccion.crearEstrategia(this.tipo);
    }

    public List<EjercicioSeleccion> getEjerciciosSeleccion() {
        return ejerciciosSeleccion;
    }

    public String obtenerResumen() {
        return "Lección '" + nombre + "' (" + tipo + ") con " + ejerciciosSeleccion.size() + " ejercicios.";
    }

    public TipoLeccion getTipo() {
        return tipo;
    }

    public EstrategiaLeccion getEstrategia() {
        return estrategia;
    }

    public boolean tieneEjerciciosPendientes() {
        return estrategia.tieneEjerciciosPendientes();
    }

    public void marcarCompletada() {
        if (tipo == TipoLeccion.PRUEBA) {
            ((EstrategiaLeccionPrueba) estrategia).marcarLeccionCompletada();
        }
    }
    /*
    public Leccion crearLeccionRepaso(String nombreRepaso) {
        if (tipo == TipoLeccion.PRUEBA) {
            return ((EstrategiaLeccionPrueba) estrategia).crearLeccionRepaso(nombreRepaso);
        }
        return null;
    }

     */
}
