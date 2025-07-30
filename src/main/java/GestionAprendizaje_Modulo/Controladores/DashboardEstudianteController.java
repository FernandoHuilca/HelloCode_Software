package GestionAprendizaje_Modulo.Controladores;

import GestionAprendizaje_Modulo.Logica.AprendizajeManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class DashboardEstudianteController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ComboBox<String> lenguajeComboBox;

    @FXML
    private ComboBox<String> nivelComboBox;

    @FXML
    private Button continuarButton;

    private static String lenguajeSeleccionado;

    public static String getLenguajeSeleccionado() {
        return lenguajeSeleccionado;
    }

    @FXML
    private void initialize() {
        lenguajeComboBox.getItems().addAll("Java", "Python", "C");
        nivelComboBox.getItems().addAll("Principiante", "Intermedio", "Avanzado");
    }

    @FXML
    private void manejarContinuar() {
        try {
            lenguajeSeleccionado = lenguajeComboBox.getValue();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/Ruta.fxml"));
            AnchorPane listaNodosPane = loader.load();
            rootPane.getChildren().setAll(listaNodosPane);
        } catch (Exception e) {
            System.err.println("Error al cargar la vista Ruta.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
