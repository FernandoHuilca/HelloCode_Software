package Gamificacion_Modulo.clases;

import java.util.ArrayList;
import java.util.List;

public class Logro {
    private final String nombre;
    private final String descripcion;
    // Lista estática de logros disponibles
    private static final List<Logro> logrosDisponibles = new ArrayList<>();
    private static boolean inicializado = false;

    public Logro(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Método estático para obtener logros disponibles
    public static List<Logro> getLogrosDisponibles() {
        if (!inicializado) {
            inicializarLogros();
        }
        return new ArrayList<>(logrosDisponibles);
    }

    // Método para inicializar logros predeterminados
    private static void inicializarLogros() {
        if (!inicializado) {
            logrosDisponibles.clear();
            logrosDisponibles.add(new Logro("Principiante", "Completar tu primer desafío"));
            logrosDisponibles.add(new Logro("Dedicado", "Por realizar 3 lecciones"));
            logrosDisponibles.add(new Logro("Acumulador", "Obtener muchos puntos de desafíos"));
            logrosDisponibles.add(new Logro("Coleccionista", "Por completar una ruta"));
            inicializado = true;
            System.out.println(">>> Logros predeterminados cargados: " + logrosDisponibles.size());
        }
    }

    // Método para agregar un nuevo logro
    public static void agregarLogro(Logro logro) {
        if (!inicializado) {
            inicializarLogros();
        }
        logrosDisponibles.add(logro);
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}