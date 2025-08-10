package GestionAprendizaje_Modulo.Controladores;

import java.io.IOException;

import Conexion.SesionManager;
import Modulo_Usuario.Clases.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CursosController {

    @FXML
    private Label tituloLenguajeLabel;

    @FXML
    private Button btnJava;
    @FXML
    private Button btnPython;
    @FXML
    private Button btnPhp;
    @FXML
    private Button btnCplusplus;

    @FXML
    private Button btnVolver;

    @FXML
    private void initialize() {
        // Acción para el botón de "Volver"
        btnVolver.setOnAction(event -> {
            try {
                // Cargar el archivo FXML de la vista HomeUsuario (siempre volver al home)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modulo_Usuario/views/homeUsuario.fxml"));
                AnchorPane homePane = loader.load(); // Cargar la vista de home

                // Obtener el stage actual y cambiar la escena
                Stage stage = (Stage) btnVolver.getScene().getWindow();
                stage.setScene(new Scene(homePane)); // Cambiar la escena
                stage.show(); // Mostrar la nueva escena
            } catch (IOException e) {
                e.printStackTrace(); // Mostrar el error si algo falla
            }
        });

        // Lógica para el botón Java
        btnJava.setOnAction(event -> {
            abrirDiagnostico("Java");
        });

        // Lógica para el botón Python
        btnPython.setOnAction(event -> {
            abrirDiagnostico("Python");
        });

        // Lógica para el botón PHP
        btnPhp.setOnAction(event -> {
            abrirDiagnostico("PHP");
        });

        // Lógica para el botón C++
        btnCplusplus.setOnAction(event -> {
            abrirDiagnostico("C");
        });
    }

    private void abrirDiagnostico(String lenguaje) {
        try {
            // Verificar si el usuario ya tiene este lenguaje
            Usuario usuarioActual = SesionManager.getInstancia().getUsuarioAutenticado();
            if (usuarioActual != null) {
                ConfiguracionUsuarioService service = ConfiguracionUsuarioService.getInstancia();

                if (service.usuarioTieneLenguaje(usuarioActual.getUsername(), lenguaje)) {
                    // El usuario ya tiene este lenguaje, ir directamente a la ruta
                    DiagnosticoController.lenguajeSeleccionado = lenguaje;

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/Ruta.fxml"));
                    AnchorPane rutaPane = loader.load();
                    Stage stage = (Stage) btnJava.getScene().getWindow();
                    stage.setScene(new Scene(rutaPane));
                    stage.show();
                    return;
                }
            }

            // El usuario no tiene este lenguaje, ir al diagnóstico
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/Diagnostico.fxml"));
            AnchorPane diagnosticoPane = loader.load();

            DiagnosticoController diagnosticoController = loader.getController();
            diagnosticoController.setDiagnosticoText(lenguaje);

            Stage stage = (Stage) btnJava.getScene().getWindow();
            stage.setScene(new Scene(diagnosticoPane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
