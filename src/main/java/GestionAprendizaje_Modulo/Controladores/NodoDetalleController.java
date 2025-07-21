package GestionAprendizaje_Modulo.Controladores;

import GestionAprendizaje_Modulo.Modelo.RecursoAprendizaje;
import GestionAprendizaje_Modulo.Ruta.NodoRuta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NodoDetalleController {

    @FXML private Label labelTituloLeccion;
    @FXML private Text textDescripcionLeccion;
    @FXML private VBox vboxRecursos;

    /**
     * Método público para "inyectar" el nodo cuyos detalles se mostrarán.
     * @param nodo El objeto NodoRuta que el usuario seleccionó.
     */
    public void setNodo(NodoRuta nodo) {
        if (nodo == null) return;

        // 1. Poblar la información de la Lección
        labelTituloLeccion.setText(nodo.getLeccion().getTitulo());
        textDescripcionLeccion.setText(nodo.getLeccion().getDescripcion());

        // 2. Poblar dinámicamente el material de apoyo
        vboxRecursos.getChildren().clear();
        if (nodo.getMaterialDeApoyo().isEmpty()) {
            vboxRecursos.getChildren().add(new Label("No hay material de apoyo para esta lección."));
        } else {
            for (RecursoAprendizaje recurso : nodo.getMaterialDeApoyo()) {
                VBox cardRecurso = new VBox(3);
                Label tituloRecurso = new Label(recurso.getTitulo());
                tituloRecurso.setStyle("-fx-font-weight: bold;");

                Text detalleRecurso = new Text(recurso.obtenerDetalle());
                detalleRecurso.setWrappingWidth(340);

                cardRecurso.getChildren().addAll(tituloRecurso, detalleRecurso);
                vboxRecursos.getChildren().add(cardRecurso);
            }
        }
    }
}