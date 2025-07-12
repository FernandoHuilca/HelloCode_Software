package EjerciciosIteractivos_Modulo;

import EjerciciosIteractivos_Modulo.Contenido.*;
import EjerciciosIteractivos_Modulo.Lecciones.Leccion;

import java.util.ArrayList;
import java.util.Scanner;

//Fernando Huilca y Mateo Quisilema
public class Main {

    private static Scanner inputScanner = new Scanner(System.in); //Scanner para entrada del usuario
    
    public static void main(String[] args) {
        //Variables para el menú principal
        int seleccionUsuario = 1; //Control del bucle del menú principal
        
        //Inicialización del contenido educativo
        Contenido cursoBasico = new Contenido();
        Leccion leccionTiposDatos = new Leccion("Tipos de Datos");
        Leccion leccionSintaxisBasica = new Leccion("Sintaxis Básica");
        cursoBasico.addLeccion(leccionSintaxisBasica);
        cursoBasico.addLeccion(leccionTiposDatos);
        
        //Creación de ejercicios para la lección de tipos de datos
        String preguntaTiposEnteros = "¿Cuál de estos es un tipo de dato entero en Java?";
        ArrayList<String> opcionesTiposEnteros = new ArrayList<>();
        opcionesTiposEnteros.add("String");
        opcionesTiposEnteros.add("boolean");
        opcionesTiposEnteros.add("double");
        opcionesTiposEnteros.add("int");
        opcionesTiposEnteros.add("Integer");
        ArrayList<String> respuestasCorrectasTiposEnteros = new ArrayList<>();
        respuestasCorrectasTiposEnteros.add("int");
        respuestasCorrectasTiposEnteros.add("Integer");

        String preguntaTipoBooleano = "¿Qué tipo se usa para verdadero o falso?";
        ArrayList<String> opcionesTipoBooleano = new ArrayList<>();
        opcionesTipoBooleano.add("String");
        opcionesTipoBooleano.add("boolean");
        opcionesTipoBooleano.add("double");
        opcionesTipoBooleano.add("int");
        ArrayList<String> respuestasCorrectasTipoBooleano = new ArrayList<>();
        respuestasCorrectasTipoBooleano.add("boolean");

        //Ejercicio con múltiples respuestas correctas
        String preguntaTiposPrimitivos = "¿Cuáles de estos son tipos de datos primitivos en Java?";
        ArrayList<String> opcionesTiposPrimitivos = new ArrayList<>();
        opcionesTiposPrimitivos.add("int");
        opcionesTiposPrimitivos.add("String");
        opcionesTiposPrimitivos.add("double");
        opcionesTiposPrimitivos.add("boolean");
        opcionesTiposPrimitivos.add("Integer");
        opcionesTiposPrimitivos.add("char");
        ArrayList<String> respuestasCorrectasTiposPrimitivos = new ArrayList<>();
        respuestasCorrectasTiposPrimitivos.add("int");
        respuestasCorrectasTiposPrimitivos.add("double");
        respuestasCorrectasTiposPrimitivos.add("boolean");
        respuestasCorrectasTiposPrimitivos.add("char");

        //Creación de ejercicios de selección múltiple
        EjercicioBase ejercicioTiposEnteros = new EjercicioSeleccion(preguntaTiposEnteros, opcionesTiposEnteros, respuestasCorrectasTiposEnteros, NivelDificultad.BASICO, Lenguaje.JAVA);
        EjercicioBase ejercicioTipoBooleano = new EjercicioSeleccion(preguntaTipoBooleano, opcionesTipoBooleano, respuestasCorrectasTipoBooleano, NivelDificultad.BASICO, Lenguaje.JAVA);
        EjercicioBase ejercicioTiposPrimitivos = new EjercicioSeleccion(preguntaTiposPrimitivos, opcionesTiposPrimitivos, respuestasCorrectasTiposPrimitivos, NivelDificultad.BASICO, Lenguaje.JAVA);
        
        //Creación de ejercicios de completar código
        String instruccionCompletarVariable = "Declara una variable de tipo entera llamada edad:";
        String codigoIncompletoVariable = "int ____ = 20;";
        ArrayList<String> partesFaltantesVariable = new ArrayList<>();
        partesFaltantesVariable.add("edad");
        ArrayList<String> respuestasEsperadasVariable = new ArrayList<>();
        respuestasEsperadasVariable.add("edad");
        
        String instruccionCompletarMetodo = "Completa el método para sumar dos números:";
        String codigoIncompletoMetodo = "public int sumar(int a, int b) {\n    return ____;\n}";
        ArrayList<String> partesFaltantesMetodo = new ArrayList<>();
        partesFaltantesMetodo.add("a + b");
        ArrayList<String> respuestasEsperadasMetodo = new ArrayList<>();
        respuestasEsperadasMetodo.add("a + b");
        
        EjercicioBase ejercicioCompletarVariable = new EjercicioCompletarCodigo(instruccionCompletarVariable, codigoIncompletoVariable, partesFaltantesVariable, respuestasEsperadasVariable, NivelDificultad.BASICO, Lenguaje.JAVA);
        EjercicioBase ejercicioCompletarMetodo = new EjercicioCompletarCodigo(instruccionCompletarMetodo, codigoIncompletoMetodo, partesFaltantesMetodo, respuestasEsperadasMetodo, NivelDificultad.BASICO, Lenguaje.JAVA);
        
        leccionTiposDatos.addEjercicio(ejercicioTiposEnteros);
        leccionTiposDatos.addEjercicio(ejercicioTipoBooleano);
        leccionTiposDatos.addEjercicio(ejercicioTiposPrimitivos);
        leccionTiposDatos.addEjercicio(ejercicioCompletarVariable);
        leccionTiposDatos.addEjercicio(ejercicioCompletarMetodo);

        //Interfaz de usuario principal
        System.out.println("_______________________ Bienvenido a HelloCode _______________________");
        while (seleccionUsuario != 111) {
            mostrarMenuLecciones(cursoBasico);
            seleccionUsuario = inputScanner.nextInt();
            
            if (seleccionUsuario >= 0 && seleccionUsuario < cursoBasico.getLecciones().size()) {
                ejecutarLeccionSeleccionada(cursoBasico, seleccionUsuario);
            } else if (seleccionUsuario != 111) {
                System.out.println("Opción no válida. Por favor, seleccione una lección válida.");
            }
        }
    }

    /**
     * Muestra el menú de lecciones disponibles
     */
    private static void mostrarMenuLecciones(Contenido curso) {
        System.out.println("_________________ Contenido del curso _________________");
        System.out.println("Seleccione la lección que desea realizar: ");
        for (int i = 0; i < curso.getLecciones().size(); i++) {
            System.out.println(i + ". Lección: " + curso.getLecciones().get(i).getNombre());
        }
        System.out.println("111. Salir");
    }

    /**
     * Ejecuta la lección seleccionada por el usuario
     */
    private static void ejecutarLeccionSeleccionada(Contenido curso, int indiceLeccion) {
        Leccion leccionActual = curso.getLeccion(indiceLeccion);
        System.out.println("____________________ Lección: " + leccionActual.getNombre() + " ____________________");
        
        for (int i = 0; i < leccionActual.getNumEjercicios(); i++) {
            EjercicioBase ejercicioActual = leccionActual.getEjercicio(i);
            
            //Aplicación del patrón Strategy para diferentes tipos de ejercicios
            if (ejercicioActual instanceof EjercicioSeleccion) {
                ejecutarEjercicioSeleccionMultiple((EjercicioSeleccion) ejercicioActual);
            } else if (ejercicioActual instanceof EjercicioCompletarCodigo) {
                ejecutarEjercicioCompletarCodigo((EjercicioCompletarCodigo) ejercicioActual);
            }
            //Aquí se pueden agregar más tipos de ejercicios en el futuro
        }
    }

    /**
     * Ejecuta un ejercicio de selección múltiple
     * Esta lógica podría ser parte de una interfaz gráfica en el futuro
     */
    private static void ejecutarEjercicioSeleccionMultiple(EjercicioSeleccion ejercicio) {
        System.out.println("\n" + ejercicio.getInstruccion());
        System.out.println("____ Opciones ____");
        
        for (int i = 0; i < ejercicio.getListOpciones().size(); i++) {
            System.out.println(i + ". " + ejercicio.getOpcion(i));
        }
        
        System.out.println("\nEste ejercicio tiene " + ejercicio.obtenerRespuestasCorrectas().size() + " respuesta(s) correcta(s).");
        
        
        ArrayList<Respuesta> respuestasUsuario = new ArrayList<>();
        
        for (int i = 0; i < ejercicio.obtenerRespuestasCorrectas().size(); i++) {
            System.out.print("Seleccione la respuesta " + (i + 1) + " (número de opción): ");
            int seleccionRespuesta = inputScanner.nextInt();
            
            //Validación de entrada
            if (seleccionRespuesta >= 0 && seleccionRespuesta < ejercicio.getListOpciones().size()) {
                //Verificar que no se haya seleccionado la misma opción antes
                    respuestasUsuario.add(new RespuestaString(ejercicio.getOpcion(seleccionRespuesta)));
            } else {
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                i--; // Repetir esta iteración
            }
        }
        
        System.out.println("\nSus respuestas seleccionadas:");
        for (int i = 0; i < respuestasUsuario.size(); i++) {
            System.out.println((i + 1) + ". " + respuestasUsuario.get(i).getRespuesta());
        }
        
        boolean respuestaCorrecta = ejercicio.evaluarRespuestas(respuestasUsuario);
        
        if (respuestaCorrecta) {
            System.out.println("\n¡Correcto! ¡Felicidades! Todas sus respuestas son correctas.");
        } else {
            System.out.println("\nIncorrecto. ¡Sigue practicando!");
            System.out.println("Respuestas correctas: " + ejercicio.obtenerRespuestasCorrectas());
        }
    }

    /**
     * Ejecuta un ejercicio de completar código
     * Esta lógica podría ser parte de una interfaz gráfica en el futuro
     */
    private static void ejecutarEjercicioCompletarCodigo(EjercicioCompletarCodigo ejercicio) {
        System.out.println("\n" + ejercicio.getInstruccion());
        System.out.println("____ Código a completar ____");
        System.out.println(ejercicio.obtenerCodigoIncompleto());
        
        System.out.println("\nEste ejercicio tiene " + ejercicio.obtenerNumeroPartesFaltantes() + " parte(s) por completar.");
        
        ArrayList<Respuesta> respuestasUsuario = new ArrayList<>();
        
        for (int i = 0; i < ejercicio.obtenerNumeroPartesFaltantes(); i++) {
            System.out.print("Complete la parte " + (i + 1) + ": ");
            String respuestaUsuario = inputScanner.next();
            respuestasUsuario.add(new RespuestaString(respuestaUsuario));
        }
        
        System.out.println("\nSus respuestas:");
        for (int i = 0; i < respuestasUsuario.size(); i++) {
            System.out.println((i + 1) + ". " + respuestasUsuario.get(i).getRespuesta());
        }
        
        boolean respuestaCorrecta = ejercicio.evaluarRespuestas(respuestasUsuario);
        
        if (respuestaCorrecta) {
            System.out.println("\n¡Correcto! ¡Felicidades! El código está completo.");
        } else {
            System.out.println("\nIncorrecto. ¡Sigue practicando!");
            System.out.println("Respuestas correctas: " + ejercicio.obtenerRespuestasEsperadas());
        }
    }
}