package Nuevo_Modulo_Leccion.controllers;

import GestionAprendizaje_Modulo.Logica.AprendizajeManager;
import GestionAprendizaje_Modulo.Logica.Ruta;
import MetodosGlobales.LeccionesCompletadas;
import MetodosGlobales.MetodosFrecuentes;
import MetodosGlobales.SesionManager;
import Modulo_Ejercicios.Controladores.EjercicioCompletarController;
import Modulo_Ejercicios.Controladores.EjercicioSeleccionController;
import Modulo_Ejercicios.exercises.EjercicioBase;
import Modulo_Ejercicios.exercises.EjercicioCompletarCodigo;
import Modulo_Ejercicios.exercises.EjercicioSeleccion;
import Modulo_Usuario.Clases.Usuario;
import Nuevo_Modulo_Leccion.logic.Leccion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LeccionUIController {
    
    // Variables estáticas para manejar la secuencia de ejercicios
    private static Leccion leccionActual;
    private static int indiceEjercicioActual = 0;
    private static String rutaFXMLVentanaFinal; // nueva variable

    /**
     * Método principal para mostrar una lección con ejercicios mixtos
     * Detecta automáticamente los tipos de ejercicios y carga la vista apropiada
     */
    public static void mostrarUnaLeccion(Leccion leccionAMostrar, Stage ventanaActual, String rutaFXML) {

        try {
            // Cerrar la ventana actual
            if (ventanaActual != null) {
                ventanaActual.close();
            }

            // Inicializar la secuencia de ejercicios
            leccionActual = leccionAMostrar;
            indiceEjercicioActual = 0;
            rutaFXMLVentanaFinal = rutaFXML;
            
            // Mostrar el primer ejercicio
            mostrarSiguienteEjercicio();

        } catch (Exception e) {
            e.printStackTrace();
            MetodosFrecuentes.mostrarAlerta("Error", "Error al cargar la lección: " + e.getMessage());
        }
    }

    /**
     * Muestra el siguiente ejercicio en la secuencia
     */
    public static void mostrarSiguienteEjercicio() {
        try {
            // Verificar si hay más ejercicios
            if (leccionActual == null || indiceEjercicioActual >= leccionActual.getListEjercicios().size()) {
                mostrarLeccionCompletada();
                return;
            }
            
            // Obtener el ejercicio actual
            EjercicioBase ejercicioActual = leccionActual.getListEjercicios().get(indiceEjercicioActual);
            
            // Mostrar el ejercicio según su tipo
            if (ejercicioActual instanceof EjercicioSeleccion) {
                mostrarEjercicioSeleccion((EjercicioSeleccion) ejercicioActual);
            } else if (ejercicioActual instanceof EjercicioCompletarCodigo) {
                mostrarEjercicioCompletar((EjercicioCompletarCodigo) ejercicioActual);
            } else {
                // Ejercicio no reconocido, saltar al siguiente
                indiceEjercicioActual++;
                mostrarSiguienteEjercicio();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            MetodosFrecuentes.mostrarAlerta("Error", "Error al cargar el ejercicio: " + e.getMessage());
        }
    }

    public static void avanzarAlSiguienteEjercicio() {
        indiceEjercicioActual++;
        mostrarSiguienteEjercicio();
        System.out.println(indiceEjercicioActual);
    }


    //Obtiene el índice del ejercicio actual para mostrar progreso

    public static int getIndiceEjercicioActual() {
        return indiceEjercicioActual;
    }

    // Obtiene el total de ejercicios en la lección actual

    public static int getTotalEjercicios() {
        return leccionActual != null ? leccionActual.getListEjercicios().size() : 0;
    }
    

    private static void mostrarEjercicioSeleccion(EjercicioSeleccion ejercicio) {
        try {
            FXMLLoader loader = new FXMLLoader(LeccionUIController.class.getResource("/Modulo_Ejercicios/views/SeleccionMultiple-view.fxml"));
            Parent root = loader.load();
            
            // Obtener el controlador y configurar el ejercicio individual
            EjercicioSeleccionController controller = loader.getController();
            controller.setEjercicio(ejercicio);
            
            // Mostrar la ventana
            Stage stage = new Stage();
            stage.setTitle("Ejercicio de Selección Múltiple " + (indiceEjercicioActual + 1));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            MetodosFrecuentes.mostrarAlerta("Error", "Error al cargar ejercicio de selección: " + e.getMessage());
        }
    }

    /**
     * Muestra un ejercicio de completar código individual
     */
    private static void mostrarEjercicioCompletar(EjercicioCompletarCodigo ejercicio) {
        try {
            FXMLLoader loader = new FXMLLoader(LeccionUIController.class.getResource("/Modulo_Ejercicios/views/CompletarCodigo.fxml"));
            Parent root = loader.load();
            
            // Obtener el controlador y configurar el ejercicio individual
            EjercicioCompletarController controller = loader.getController();
            controller.setEjercicio(ejercicio);
            
            // Mostrar la ventana
            Stage stage = new Stage();
            stage.setTitle("Ejercicio de Completar Código " + (indiceEjercicioActual + 1));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
            MetodosFrecuentes.mostrarAlerta("Error", "Error al cargar ejercicio de completar código: " + e.getMessage());
        }
    }

    /**
     * Muestra la pantalla de lección completada
     */
    /*private static void mostrarLeccionCompletada() {
        try {
            MetodosFrecuentes.mostrarVentana("/Nuevo_Modulo_Leccion/views/ResumenLeccionCompletada.fxml", "Resumen");
            //MetodosFrecuentes.mostrarAlerta("¡Felicidades!", "Has completado todos los ejercicios de la lección.");
            // Abrir la ventana final que fue pasada por parámetro
            if (rutaFXMLVentanaFinal != null && !rutaFXMLVentanaFinal.isEmpty()) {
                MetodosFrecuentes.mostrarVentana(rutaFXMLVentanaFinal, "Menú de Lecciones");
            }
            // Aquí puedes agregar lógica adicional para mostrar estadísticas, XP ganado, etc.

        } catch (Exception e) {
            e.printStackTrace();
            MetodosFrecuentes.mostrarAlerta("Error", "Error al mostrar lección completada: " + e.getMessage());
        }
    }*/
    private static void mostrarLeccionCompletada() {
        try {
            FXMLLoader loader = new FXMLLoader(LeccionUIController.class.getResource("/Nuevo_Modulo_Leccion/views/ResumenLeccionCompletada.fxml"));
            Parent root = loader.load();

            Stage resumenStage = new Stage();
            resumenStage.setTitle("Resumen de la Lección");
            resumenStage.setScene(new Scene(root));
            resumenStage.setResizable(false);

            resumenStage.showAndWait();

            if (rutaFXMLVentanaFinal != null && !rutaFXMLVentanaFinal.isEmpty()) {
                MetodosFrecuentes.mostrarVentana(rutaFXMLVentanaFinal, "Menú de Lecciones");
            }
            SesionManager.getInstancia().setCurso(AprendizajeManager.getInstancia().getCursos());
        } catch (Exception e) {
            e.printStackTrace();
            MetodosFrecuentes.mostrarAlerta("Error", "Error al mostrar lección completada: " + e.getMessage());
        }
    }



}
