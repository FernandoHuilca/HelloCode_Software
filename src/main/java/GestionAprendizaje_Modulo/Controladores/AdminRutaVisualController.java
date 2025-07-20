package GestionAprendizaje_Modulo.Controladores;

import GestionAprendizaje_Modulo.Ruta.NodoRuta;
import GestionAprendizaje_Modulo.Ruta.Ruta;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminRutaVisualController {

    @FXML private Label labelNombreRuta;
    @FXML private VBox pathContainer;
    @FXML private Button buttonAgregar;

    private Ruta rutaActual;

    // Este método es llamado desde el controlador anterior para pasarle la ruta a editar.
    public void setRuta(Ruta ruta) {
        this.rutaActual = ruta;
        labelNombreRuta.setText("Editando Ruta: " + ruta.getNombre());
        renderizarRuta();
    }

    private void renderizarRuta() {
        pathContainer.getChildren().clear(); // Limpiamos la vista para redibujar

        // Dibujamos cada nodo existente
        for (NodoRuta nodo : rutaActual.getNodos()) {
            pathContainer.getChildren().add(crearComponenteNodo(nodo));
        }

        // Añadimos el botón para agregar un nuevo nodo
        Button buttonAgregar = new Button("+");
        buttonAgregar.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-background-radius: 50;");
        buttonAgregar.setPrefSize(50, 50);
        buttonAgregar.setOnAction(event -> añadirNodos());
        pathContainer.getChildren().add(buttonAgregar);
    }

    private HBox crearComponenteNodo(NodoRuta nodo) {
        HBox nodoBox = new HBox(10);
        nodoBox.setAlignment(Pos.CENTER_LEFT);

        Circle circulo = new Circle(20, Color.DODGERBLUE);
        Label etiquetaOrden = new Label(String.valueOf(nodo.getOrden()));
        etiquetaOrden.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        // StackPane para poner el número dentro del círculo (opcional, pero se ve bien)
        javafx.scene.layout.StackPane stack = new javafx.scene.layout.StackPane(circulo, etiquetaOrden);

        Label etiquetaLeccion = new Label(nodo.getLeccion().getTitulo());
        etiquetaLeccion.setFont(new javafx.scene.text.Font(16));

        // Podrías añadir un botón de "Editar" aquí si quisieras

        nodoBox.getChildren().addAll(stack, etiquetaLeccion);
        return nodoBox;
    }

    @FXML
    private void añadirNodos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/DialogoCrearNodo.fxml"));
            Parent root = loader.load();

            DialogoCrearNodoController controller = loader.getController();
            controller.setRuta(rutaActual); // Pasamos la ruta al controlador del diálogo

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Nuevo Nodo");
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal
            dialogStage.setScene(new Scene(root));

            // Espera a que el diálogo se cierre
            dialogStage.showAndWait();

            // Después de que se cierra el diálogo, volvemos a renderizar la ruta para mostrar los cambios
            renderizarRuta();

        } catch (IOException e) {
            e.printStackTrace();
            // Mostrar una alerta de error
        }
    }
}