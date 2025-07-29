package Comunidad_Modulo.Controladores_GUI;

import MetodosGlobales.MetodosFrecuentes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EntradaAdminComunidad_Controller {

    @FXML
    private Button buttonModerador;

    @FXML
    private Button buttonSalir;

    @FXML
    private Button buttonVolver;

    @FXML
    private Button btnPerfil2;

    @FXML
    private Button btnRanking;

    @FXML
    private Button btnComunidad;

    @FXML
    private void moderadorComunidad() {
        MetodosFrecuentes.cambiarVentana((Stage) buttonModerador.getScene().getWindow(),
                "/Modulo_Comunidad/Views/Moderador.fxml", "MODERADOR");
    }

    @FXML
    private void volverAHome(){
        MetodosFrecuentes.cambiarVentana((Stage) buttonVolver.getScene().getWindow(),
                "/Modulo_Usuario/views/home.fxml", "Volver a Home - Modulos");
    }

    @FXML
    private void salir() {
        Stage stage = (Stage) buttonSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void irAPerfil() {
        MetodosFrecuentes.cambiarVentana((Stage) btnPerfil2.getScene().getWindow(),
                "/Modulo_Usuario/views/perfil.fxml", "Ir a Perfil de Usuario");
    }

    @FXML
    public void irARanking() {
        MetodosFrecuentes.cambiarVentana((Stage) btnRanking.getScene().getWindow(),
                "/Gamificacion_Modulo/fxml/Ranking.fxml", "Ir a Ranking de Usuario");
    }

    @FXML
    public void irAComunidad() {
        MetodosFrecuentes.cambiarVentana((Stage) btnComunidad.getScene().getWindow(),
                "/Modulo_Comunidad/Views/Comunidad.fxml", "Ir a Menu Comunidad");
    }
}
