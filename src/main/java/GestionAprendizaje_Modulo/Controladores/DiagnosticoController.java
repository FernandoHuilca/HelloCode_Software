package GestionAprendizaje_Modulo.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DiagnosticoController {

    @FXML
    private Label tituloLabel;
    @FXML
    private Button btnFinalizar;
    @FXML
    private void initialize() {
        btnFinalizar.setOnAction(event -> {
            try {
                // Cargar el archivo FXML de la vista anterior
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/Ruta.fxml"));
                AnchorPane rutaPane = loader.load(); // Cargar la vista anterior

                // Obtener el stage actual y cambiar la escena
                Stage stage = (Stage) btnFinalizar.getScene().getWindow();
                stage.setScene(new Scene(rutaPane)); // Cambiar la escena
                stage.show(); // Mostrar la nueva escena
            } catch (IOException e) {
                e.printStackTrace(); // Mostrar el error si algo falla
            }
        });
    }

    // Método para recibir el nombre del lenguaje y actualizar el título
    public void setDiagnostico(String lenguaje) {
        // Cambiar el título con el nombre del lenguaje
        tituloLabel.setText("Diagnóstico " + lenguaje);
    }
}
