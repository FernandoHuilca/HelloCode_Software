package Gamificacion_Modulo;

import java.time.LocalDateTime;
import java.util.Map;

public class Estadistica {
    private Long id;
    private String tipo;
    private Double valor;
    private LocalDateTime fechaGeneracion;
    private Estudiante estudiante;

    public Estadistica(String tipo, Double valor, Estudiante estudiante) {
        // Constructor sin implementación
    }

    public static Estadistica generarEstadistica(String tipo, Double valor, Estudiante estudiante) {
        // Método sin implementación
        return null;
    }

    public void actualizarValor(Double nuevoValor) {
        // Método sin implementación
    }

    public Map<String, Object> getDatos() {
        // Método sin implementación
        return null;
    }
} 