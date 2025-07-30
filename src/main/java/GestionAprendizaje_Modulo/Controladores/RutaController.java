package GestionAprendizaje_Modulo.Controladores;

import java.io.IOException;
import java.util.List;

import GestionAprendizaje_Modulo.Logica.AprendizajeManager;
import Conexion.MetodosFrecuentes;
import Nuevo_Modulo_Leccion.controllers.LeccionUIController;
import Nuevo_Modulo_Leccion.dataBase.LeccionRepository;
import Nuevo_Modulo_Leccion.logic.Leccion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * =================================================================================
 * Controlador de la Vista de Ruta - Versión Final
 * =================================================================================
 * Este controlador es la capa de VISTA (presentación) de la ruta de aprendizaje.
 * Su lógica es la siguiente:
 * 1. Al inicializarse, le pide al `AprendizajeManager` los datos ya construidos
 *    y listos para usar (la estructura de Cursos y Rutas).
 * 2. Utiliza estos datos para dibujar dinámicamente los "noditos" (botones) en
 *    la pantalla en posiciones predefinidas.
 * 3. Configura cada nodito para que, al ser presionado, abra la interfaz de la
 *    lección que tiene asignada, llamando al controlador del otro módulo.
 */
public class RutaController {

    @FXML private Pane nodoContainer;    // El panel del FXML donde se dibujarán los nodos.
    @FXML private AnchorPane rootPane;   // El panel raíz de la ventana, para obtener el Stage.
    @FXML private Label tituloLenguajeLabel; // Agregar un label para el título del lenguaje
    @FXML private Button btnBack;

    /**
     * El método initialize se ejecuta automáticamente después de que el FXML ha sido cargado.
     * Es el punto de partida para construir la vista dinámica.
     */
    @FXML
    private void initialize() {
        AprendizajeManager.getInstancia().construirDatosDePrueba();
        System.out.println("[RutaController] Inicializando. Obteniendo datos pre-construidos del Manager...");

        String lenguajeSeleccionado = DashboardEstudianteController.getLenguajeSeleccionado();
        tituloLenguajeLabel.setText(lenguajeSeleccionado); // Actualizar el título con el lenguaje seleccionado

        // Obtener la lista de lecciones desde el repositorio
        List<Leccion> listLecciones = LeccionRepository.getLecciones();

        if (listLecciones.isEmpty()) {
            System.err.println("[RutaController] No se encontraron lecciones disponibles.");
            return;
        }

        // Dibujar los nodos visuales con las lecciones disponibles
        construirNodosVisuales(listLecciones);
    }

    /**
     * Dibuja los "noditos" en la pantalla a partir de la lista de NodoRuta.
     * @param //nodosDeLaRuta La lista de objetos NodoRuta que ya tienen su lección asignada.
     */
    private void construirNodosVisuales(List<Leccion> listLecciones) {
        // Posiciones predefinidas (X, Y) para cada nodito en la pantalla.
        // Puedes añadir o quitar coordenadas para cambiar el diseño visual de la ruta.
        double[][] positions = {
                {50, 50}, {150, 120}, {80, 200}, {200, 280}, {120, 360}
        };

        System.out.println("[RutaController] Dibujando " + listLecciones.size() + " nodos en la pantalla.");

        for (int i = 0; i < listLecciones.size(); i++) {
            if (i >= positions.length) {
                System.err.println("[RutaController] Advertencia: Hay más lecciones que posiciones definidas. El nodo " + (i + 1) + " no se dibujará.");
                break;
            }

            Leccion leccion = listLecciones.get(i);
            Button nodoButton = new Button("L" + (i + 1));
            nodoButton.setStyle("-fx-background-color: #50C878; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 50%; -fx-min-width: 80px; -fx-min-height: 80px; -fx-max-width: 80px; -fx-max-height: 80px; -fx-cursor: hand;");
            nodoButton.setLayoutX(positions[i][0]);
            nodoButton.setLayoutY(positions[i][1]);
            nodoButton.setTooltip(new Tooltip("Lección con " + leccion.getNumEjercicios() + " ejercicios"));

            nodoButton.setOnAction(event -> {
                try {
                    // Pasar la ruta de la pantalla de ruta al método mostrarUnaLeccion
                    LeccionUIController.mostrarUnaLeccion(leccion, (Stage) rootPane.getScene().getWindow(), "/GestionAprendizaje_Modulo/Vistas/Ruta.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error al abrir la lección: " + e.getMessage());
                }
            });

            nodoContainer.getChildren().add(nodoButton);
        }
    }

    /**
     * Maneja la acción del botón "Atrás", cargando la vista anterior.
     */
    @FXML
    private void manejarAtras() {
         MetodosFrecuentes.cambiarVentana((Stage) rootPane.getScene().getWindow(), "/Modulo_Usuario/views/homeUsuario.fxml", "Perfil de Usuario");
    }

    /**
     * Maneja la acción del botón "Recursos", cargando la vista de recursos.
     */
    @FXML
    private void abrirRecursos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/Recursos.fxml"));
            AnchorPane recursosPane = loader.load();
            rootPane.getChildren().setAll(recursosPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
