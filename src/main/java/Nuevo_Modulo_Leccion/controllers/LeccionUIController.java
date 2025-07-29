package Nuevo_Modulo_Leccion.controllers;

import MetodosGlobales.MetodosFrecuentes;
import Nuevo_Modulo_Leccion.logic.Leccion;
import javafx.stage.Stage;

import javax.swing.*;

public class LeccionUIController {
    /*
    public static void mostrarUnaLeccion(Leccion leccionAMostrar, Stage ventanaAnterior) {
        // Mostrar mensaje emergente
        JOptionPane.showMessageDialog(null, "Próximamente esta lógica de mostrar una lección");

        // Volver a mostrar la ventana anterior, por si estaba oculta
        ventanaAnterior.show();
    }
    public static void mostrarUnaLeccion(Leccion leccionAMostrar) {
        JOptionPane.showMessageDialog(null, "Proximamente esta logica de mostar una leccion");
        //MetodosFrecuentes.mostrarVentana("/Modulo_Ejercicios/views/SeleccionMultiple-view.fxml", "Titulo xd");
    } */

    public static void mostrarUnaLeccion(Leccion leccionAMostrar, Stage ventanaActual, String rutaFXML) {
        // Cerrar  la ventana actual
        ventanaActual.close(); // se cierra para poder cargarla nuevamente con los datos actualizados

        // Mostrar mensaje emergente
        JOptionPane.showMessageDialog(null, "Próximamente esta lógica de mostrar una lección");

        // Abrir la nueva ventana
        MetodosFrecuentes.mostrarVentana(rutaFXML, "Ventana Nueva");
    }
}
