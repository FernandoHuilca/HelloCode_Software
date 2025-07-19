package model;

import filtros.FiltroEjercicio;
import enums.TipoLeccion;
import enums.NivelDificultad;
import enums.LenguajeProgramacion;

import java.util.List;
import java.util.stream.Collectors;

public class GestorLecciones {

    public Leccion generarLeccionPersonalizada(String nombre, List<FiltroEjercicio> filtros, List<Ejercicio> disponibles) {
        GestorEjercicios gestor = new GestorEjercicios();
        disponibles.forEach(gestor::agregarEjercicio);
        List<Ejercicio> filtrados = gestor.aplicarFiltros(filtros);

        List<DetalleLeccion> detalles = filtrados.stream()
                .map(e -> new DetalleLeccion(e, new ResultadoEvaluacion(true, 100, 10, "Bien hecho", true)))
                .collect(Collectors.toList());

        Leccion leccion = new Leccion((int) (Math.random() * 1000), nombre, detalles);
        leccion.setTipo(TipoLeccion.NORMAL);
        leccion.setConocimiento(5);
        return leccion;
    }

    public Leccion generarLeccionDiagnostico(LenguajeProgramacion lenguaje, List<Ejercicio> disponibles) {
        List<Ejercicio> seleccionados = disponibles.stream()
                .filter(e -> e.getLenguaje() == lenguaje)
                .distinct()
                .limit(10)
                .collect(Collectors.toList());

        Leccion leccion = new Leccion("Diagnóstico " + lenguaje, TipoLeccion.DIAGNOSTICO);
        leccion.setDetalles(seleccionados.stream()
                .map(DetalleLeccion::new)
                .collect(Collectors.toList()));
        leccion.setConocimiento(5);
        return leccion;
    }

    public Leccion generarLeccionNormal(LenguajeProgramacion lenguaje, NivelDificultad dificultad, List<Ejercicio> disponibles) {
        List<Ejercicio> seleccionados = disponibles.stream()
                .filter(e -> e.getLenguaje() == lenguaje && e.getDificultad() == dificultad)
                .limit(5)
                .collect(Collectors.toList());

        Leccion leccion = new Leccion("Lección " + lenguaje + " " + dificultad, TipoLeccion.NORMAL);
        leccion.setDetalles(seleccionados.stream()
                .map(DetalleLeccion::new)
                .collect(Collectors.toList()));
        leccion.setConocimiento(5);
        return leccion;
    }

    public Leccion generarLeccionPrueba(List<Ejercicio> fallados) {
        List<Ejercicio> seleccionados = fallados.stream()
                .distinct()
                .limit(10)
                .collect(Collectors.toList());

        Leccion leccion = new Leccion("Prueba ejercicios fallados", TipoLeccion.PRUEBA);
        leccion.setDetalles(seleccionados.stream()
                .map(DetalleLeccion::new)
                .collect(Collectors.toList()));
        leccion.setConocimiento(5);
        return leccion;
    }
}
