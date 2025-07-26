package GestorEjercicios.ejemplo;

import GestorEjercicios.GestorEjerciciosEntry;
import GestorEjercicios.model.Leccion;
import GestorEjercicios.enums.TipoLeccion;
import GestorEjercicios.enums.NivelDificultad;
import GestorEjercicios.enums.LenguajeProgramacion;
import Modulo_Usuario.Clases.UsuarioComunidad;
import Modulo_Ejercicios.exercises.EjercicioSeleccion;
import Modulo_Ejercicios.exercises.EjercicioCompletarCodigo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ejemplo específico que demuestra las nuevas funcionalidades
 * de dificultad y lenguaje de programación en las lecciones
 */
public class EjemploDificultadLenguaje {
    
    public static void main(String[] args) {
        // Inicializar el módulo
        GestorEjerciciosEntry.inicializar();
        
        System.out.println("=== DEMOSTRACIÓN: Dificultad y Lenguaje de Programación ===\n");
        
        // Crear ejercicios de ejemplo con diferentes dificultades y lenguajes
        List<Object> ejerciciosJavaBasico = crearEjerciciosJavaBasico();
        List<Object> ejerciciosPythonIntermedio = crearEjerciciosPythonIntermedio();
        List<Object> ejerciciosJavaScriptAvanzado = crearEjerciciosJavaScriptAvanzado();
        List<Object> ejerciciosMixtos = crearEjerciciosMixtos();
        
        // 1. Crear lecciones con dificultad y lenguaje calculados automáticamente
        System.out.println("1. LECCIONES CON CÁLCULO AUTOMÁTICO:");
        System.out.println("=====================================");
        
        Leccion leccionAutoJava = GestorEjerciciosEntry.crearLeccionNormal(
            "Java Básico - Variables",
            ejerciciosJavaBasico
        );
        
        Leccion leccionAutoPython = GestorEjerciciosEntry.crearLeccionNormal(
            "Python Intermedio - Funciones",
            ejerciciosPythonIntermedio
        );
        
        Leccion leccionAutoJavaScript = GestorEjerciciosEntry.crearLeccionNormal(
            "JavaScript Avanzado - Clases",
            ejerciciosJavaScriptAvanzado
        );
        
        Leccion leccionAutoMixta = GestorEjerciciosEntry.crearLeccionNormal(
            "Lección Mixta - Varios Lenguajes",
            ejerciciosMixtos
        );
        
        // Mostrar información calculada automáticamente
        mostrarInformacionLeccion("Java Básico (Auto)", leccionAutoJava);
        mostrarInformacionLeccion("Python Intermedio (Auto)", leccionAutoPython);
        mostrarInformacionLeccion("JavaScript Avanzado (Auto)", leccionAutoJavaScript);
        mostrarInformacionLeccion("Lección Mixta (Auto)", leccionAutoMixta);
        
        // 2. Crear lecciones con dificultad y lenguaje específicos
        System.out.println("\n2. LECCIONES CON DIFICULTAD Y LENGUAJE ESPECÍFICOS:");
        System.out.println("==================================================");
        
        Leccion leccionJavaAvanzado = GestorEjerciciosEntry.crearLeccionNormal(
            "Java Avanzado - POO",
            ejerciciosJavaBasico, // Usamos ejercicios básicos pero especificamos avanzado
            NivelDificultad.AVANZADO,
            LenguajeProgramacion.JAVA
        );
        
        Leccion leccionPythonBasico = GestorEjerciciosEntry.crearLeccionNormal(
            "Python Básico - Variables",
            ejerciciosPythonIntermedio, // Usamos ejercicios intermedios pero especificamos básico
            NivelDificultad.BASICO,
            LenguajeProgramacion.PYTHON
        );
        
        Leccion leccionJavaScriptIntermedio = GestorEjerciciosEntry.crearLeccionPrueba(
            "JavaScript Intermedio - Funciones",
            ejerciciosJavaScriptAvanzado, // Usamos ejercicios avanzados pero especificamos intermedio
            NivelDificultad.INTERMEDIO,
            LenguajeProgramacion.JAVASCRIPT
        );
        
        // Mostrar información especificada manualmente
        mostrarInformacionLeccion("Java Avanzado (Específico)", leccionJavaAvanzado);
        mostrarInformacionLeccion("Python Básico (Específico)", leccionPythonBasico);
        mostrarInformacionLeccion("JavaScript Intermedio (Específico)", leccionJavaScriptIntermedio);
        
        // 3. Filtrar lecciones por criterios
        System.out.println("\n3. FILTRADO DE LECCIONES POR CRITERIOS:");
        System.out.println("=======================================");
        
        List<Leccion> todasLasLecciones = GestorEjerciciosEntry.obtenerTodasLasLecciones();
        
        // Filtrar por lenguaje
        List<Leccion> leccionesJava = todasLasLecciones.stream()
            .filter(l -> l.getLenguaje() == LenguajeProgramacion.JAVA)
            .collect(Collectors.toList());
        
        List<Leccion> leccionesPython = todasLasLecciones.stream()
            .filter(l -> l.getLenguaje() == LenguajeProgramacion.PYTHON)
            .collect(Collectors.toList());
        
        List<Leccion> leccionesJavaScript = todasLasLecciones.stream()
            .filter(l -> l.getLenguaje() == LenguajeProgramacion.JAVASCRIPT)
            .collect(Collectors.toList());
        
        // Filtrar por dificultad
        List<Leccion> leccionesBasicas = todasLasLecciones.stream()
            .filter(l -> l.getDificultad() == NivelDificultad.BASICO)
            .collect(Collectors.toList());
        
        List<Leccion> leccionesIntermedias = todasLasLecciones.stream()
            .filter(l -> l.getDificultad() == NivelDificultad.INTERMEDIO)
            .collect(Collectors.toList());
        
        List<Leccion> leccionesAvanzadas = todasLasLecciones.stream()
            .filter(l -> l.getDificultad() == NivelDificultad.AVANZADO)
            .collect(Collectors.toList());
        
        // Mostrar resultados del filtrado
        System.out.println("Lecciones por lenguaje:");
        System.out.println("  - Java: " + leccionesJava.size() + " lecciones");
        System.out.println("  - Python: " + leccionesPython.size() + " lecciones");
        System.out.println("  - JavaScript: " + leccionesJavaScript.size() + " lecciones");
        
        System.out.println("\nLecciones por dificultad:");
        System.out.println("  - Básico: " + leccionesBasicas.size() + " lecciones");
        System.out.println("  - Intermedio: " + leccionesIntermedias.size() + " lecciones");
        System.out.println("  - Avanzado: " + leccionesAvanzadas.size() + " lecciones");
        
        // 4. Filtros combinados
        System.out.println("\n4. FILTROS COMBINADOS:");
        System.out.println("======================");
        
        List<Leccion> leccionesJavaAvanzadas = todasLasLecciones.stream()
            .filter(l -> l.getLenguaje() == LenguajeProgramacion.JAVA)
            .filter(l -> l.getDificultad() == NivelDificultad.AVANZADO)
            .collect(Collectors.toList());
        
        List<Leccion> leccionesPythonBasicas = todasLasLecciones.stream()
            .filter(l -> l.getLenguaje() == LenguajeProgramacion.PYTHON)
            .filter(l -> l.getDificultad() == NivelDificultad.BASICO)
            .collect(Collectors.toList());
        
        System.out.println("Lecciones Java Avanzadas:");
        leccionesJavaAvanzadas.forEach(l -> System.out.println("  - " + l.getNombre()));
        
        System.out.println("\nLecciones Python Básicas:");
        leccionesPythonBasicas.forEach(l -> System.out.println("  - " + l.getNombre()));
        
        // 5. Estadísticas generales
        System.out.println("\n5. ESTADÍSTICAS GENERALES:");
        System.out.println("==========================");
        
        System.out.println("Total de lecciones: " + todasLasLecciones.size());
        System.out.println("Lecciones normales: " + todasLasLecciones.stream()
            .filter(l -> l.getTipo() == TipoLeccion.NORMAL).count());
        System.out.println("Lecciones de prueba: " + todasLasLecciones.stream()
            .filter(l -> l.getTipo() == TipoLeccion.PRUEBA).count());
        
        System.out.println("\nDistribución por lenguaje:");
        System.out.println("  - Java: " + todasLasLecciones.stream()
            .filter(l -> l.getLenguaje() == LenguajeProgramacion.JAVA).count());
        System.out.println("  - Python: " + todasLasLecciones.stream()
            .filter(l -> l.getLenguaje() == LenguajeProgramacion.PYTHON).count());
        System.out.println("  - JavaScript: " + todasLasLecciones.stream()
            .filter(l -> l.getLenguaje() == LenguajeProgramacion.JAVASCRIPT).count());
        
        System.out.println("\nDistribución por dificultad:");
        System.out.println("  - Básico: " + todasLasLecciones.stream()
            .filter(l -> l.getDificultad() == NivelDificultad.BASICO).count());
        System.out.println("  - Intermedio: " + todasLasLecciones.stream()
            .filter(l -> l.getDificultad() == NivelDificultad.INTERMEDIO).count());
        System.out.println("  - Avanzado: " + todasLasLecciones.stream()
            .filter(l -> l.getDificultad() == NivelDificultad.AVANZADO).count());
    }
    
    /**
     * Muestra la información completa de una lección
     */
    private static void mostrarInformacionLeccion(String titulo, Leccion leccion) {
        System.out.println(titulo + ":");
        System.out.println("  - Nombre: " + leccion.getNombre());
        System.out.println("  - Tipo: " + leccion.getTipo());
        System.out.println("  - Dificultad: " + leccion.getDificultad());
        System.out.println("  - Lenguaje: " + leccion.getLenguaje());
        System.out.println("  - Ejercicios: " + leccion.getNumeroEjercicios());
        System.out.println("  - Resumen: " + leccion.obtenerResumen());
        System.out.println();
    }
    
    /**
     * Crea ejercicios de Java básico
     */
    private static List<Object> crearEjerciciosJavaBasico() {
        List<Object> ejercicios = new ArrayList<>();
        
        // Ejercicio 1: Variables en Java
        EjercicioSeleccion ej1 = new EjercicioSeleccion();
        ej1.setId("java_basico_1");
        ej1.setInstruccion("¿Cuál es la forma correcta de declarar una variable en Java?");
        ej1.setNivelDificultad("BASICO");
        ej1.setLenguaje("JAVA");
        ej1.setOpciones(List.of("var x = 5;", "int x = 5;", "variable x = 5;", "x = 5;"));
        ej1.setRespuestaCorrecta("int x = 5;");
        ejercicios.add(ej1);
        
        // Ejercicio 2: Tipos de datos
        EjercicioSeleccion ej2 = new EjercicioSeleccion();
        ej2.setId("java_basico_2");
        ej2.setInstruccion("¿Qué tipo de dato se usa para números enteros en Java?");
        ej2.setNivelDificultad("BASICO");
        ej2.setLenguaje("JAVA");
        ej2.setOpciones(List.of("float", "int", "String", "boolean"));
        ej2.setRespuestaCorrecta("int");
        ejercicios.add(ej2);
        
        return ejercicios;
    }
    
    /**
     * Crea ejercicios de Python intermedio
     */
    private static List<Object> crearEjerciciosPythonIntermedio() {
        List<Object> ejercicios = new ArrayList<>();
        
        // Ejercicio 1: Funciones en Python
        EjercicioSeleccion ej1 = new EjercicioSeleccion();
        ej1.setId("python_intermedio_1");
        ej1.setInstruccion("¿Cuál es la sintaxis correcta para definir una función en Python?");
        ej1.setNivelDificultad("INTERMEDIO");
        ej1.setLenguaje("PYTHON");
        ej1.setOpciones(List.of("function nombre():", "def nombre():", "func nombre():", "define nombre():"));
        ej1.setRespuestaCorrecta("def nombre():");
        ejercicios.add(ej1);
        
        // Ejercicio 2: List comprehensions
        EjercicioSeleccion ej2 = new EjercicioSeleccion();
        ej2.setId("python_intermedio_2");
        ej2.setInstruccion("¿Cuál es la forma correcta de crear una lista de números pares del 0 al 10?");
        ej2.setNivelDificultad("INTERMEDIO");
        ej2.setLenguaje("PYTHON");
        ej2.setOpciones(List.of("[x for x in range(11) if x % 2 == 0]", "[x if x % 2 == 0 for x in range(11)]", 
                                "for x in range(11): if x % 2 == 0", "list(range(0, 11, 2))"));
        ej2.setRespuestaCorrecta("[x for x in range(11) if x % 2 == 0]");
        ejercicios.add(ej2);
        
        return ejercicios;
    }
    
    /**
     * Crea ejercicios de JavaScript avanzado
     */
    private static List<Object> crearEjerciciosJavaScriptAvanzado() {
        List<Object> ejercicios = new ArrayList<>();
        
        // Ejercicio 1: Clases en JavaScript
        EjercicioSeleccion ej1 = new EjercicioSeleccion();
        ej1.setId("javascript_avanzado_1");
        ej1.setInstruccion("¿Cuál es la sintaxis correcta para definir una clase en JavaScript ES6?");
        ej1.setNivelDificultad("AVANZADO");
        ej1.setLenguaje("JAVASCRIPT");
        ej1.setOpciones(List.of("class MiClase {}", "function MiClase() {}", "var MiClase = class {}", "object MiClase {}"));
        ej1.setRespuestaCorrecta("class MiClase {}");
        ejercicios.add(ej1);
        
        // Ejercicio 2: Promesas
        EjercicioSeleccion ej2 = new EjercicioSeleccion();
        ej2.setId("javascript_avanzado_2");
        ej2.setInstruccion("¿Qué método se usa para manejar múltiples promesas en JavaScript?");
        ej2.setNivelDificultad("AVANZADO");
        ej2.setLenguaje("JAVASCRIPT");
        ej2.setOpciones(List.of("Promise.all()", "Promise.race()", "Promise.resolve()", "Promise.reject()"));
        ej2.setRespuestaCorrecta("Promise.all()");
        ejercicios.add(ej2);
        
        return ejercicios;
    }
    
    /**
     * Crea ejercicios mixtos con diferentes lenguajes y dificultades
     */
    private static List<Object> crearEjerciciosMixtos() {
        List<Object> ejercicios = new ArrayList<>();
        
        // Agregar ejercicios de diferentes tipos
        ejercicios.addAll(crearEjerciciosJavaBasico());
        ejercicios.addAll(crearEjerciciosPythonIntermedio());
        ejercicios.addAll(crearEjerciciosJavaScriptAvanzado());
        
        return ejercicios;
    }
} 