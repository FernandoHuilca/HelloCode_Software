package GestionAprendizaje_Modulo.Controladores;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DiagnosticoController {

    @FXML private Label tituloLabel;
    @FXML private RadioButton rbNivelBasico;
    @FXML private RadioButton rbNivelIntermedio;
    @FXML private RadioButton rbNivelAvanzado;
    @FXML private Button btnContinuar;

    private ToggleGroup nivelGroup;

    // Variable estática para guardar el nivel seleccionado globalmente
    public static String nivelSeleccionado = null;
    // Variable estática para guardar el lenguaje seleccionado globalmente
    public static String lenguajeSeleccionado = null;

    @FXML
    private void initialize() {
        // Crear y asociar ToggleGroup para nivel
        nivelGroup = new ToggleGroup();
        rbNivelBasico.setToggleGroup(nivelGroup);
        rbNivelIntermedio.setToggleGroup(nivelGroup);
        rbNivelAvanzado.setToggleGroup(nivelGroup);

        // Acción del botón Continuar
        btnContinuar.setOnAction(event -> {
            // Validar selección
            RadioButton seleccionado = (RadioButton) nivelGroup.getSelectedToggle();
            if (seleccionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Nivel no seleccionado");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, selecciona tu nivel de conocimiento antes de continuar.");
                alerta.showAndWait();
                return;
            }

            // Obtener nivel
            String nivel = seleccionado.getText();
            nivelSeleccionado = nivel; // Guardar el nivel globalmente
            System.out.println("Nivel seleccionado: " + nivel);

            // Navegar a la vista de Ruta
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/GestionAprendizaje_Modulo/Vistas/Ruta.fxml")
                );
                AnchorPane rutaPane = loader.load();
                Stage stage = (Stage) btnContinuar.getScene().getWindow();
                stage.setScene(new Scene(rutaPane));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Permite cambiar el texto del título desde otro controlador.
     */
    public void setDiagnosticoText(String texto) {
        tituloLabel.setText(texto);
        lenguajeSeleccionado = texto; // Guardar el lenguaje seleccionado
    }
}
