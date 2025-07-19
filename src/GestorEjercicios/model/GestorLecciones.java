package model;

import filtros.FiltroEjercicio;
import java.util.List;
import java.util.stream.Collectors;

public class GestorLecciones {
    public Leccion generarLeccionPersonalizada(String nombre, List<FiltroEjercicio> filtros, List<model.Ejercicio> disponibles) {
        model.GestorEjercicios gestor = new model.GestorEjercicios();
        disponibles.forEach(gestor::agregarEjercicio);
        List<model.Ejercicio> filtrados = gestor.aplicarFiltros(filtros);

        List<model.DetalleLeccion> detalles = filtrados.stream()
                .map(e -> new model.DetalleLeccion(e, new model.ResultadoEvaluacion(true, 100, 10, "Bien hecho", true)))
                .collect(Collectors.toList());

        return new Leccion((int) (Math.random() * 1000), nombre, detalles);
    }
}