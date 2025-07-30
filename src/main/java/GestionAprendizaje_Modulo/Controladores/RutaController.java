package GestionAprendizaje_Modulo.Controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GestionAprendizaje_Modulo.Logica.AprendizajeManager;
import GestionAprendizaje_Modulo.Logica.Curso;
import GestionAprendizaje_Modulo.Logica.NodoRuta;
import GestionAprendizaje_Modulo.Logica.ProgresoLecciones;
import GestionAprendizaje_Modulo.Logica.Ruta;
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

    /**
     * El método initialize se ejecuta automáticamente después de que el FXML ha sido cargado.
     * Es el punto de partida para construir la vista dinámica.
     */
    @FXML
    private void initialize() {
        System.out.println("[RutaController] Inicializando. Obteniendo datos pre-construidos del Manager...");

        String lenguajeSeleccionado = DashboardEstudianteController.getLenguajeSeleccionado();
        tituloLenguajeLabel.setText(lenguajeSeleccionado); // Actualizar el título con el lenguaje seleccionado

        // Obtener la lista de lecciones desde el repositorio
        List<Leccion> listLecciones = LeccionRepository.getLecciones();
        List<Leccion> leccionesFiltradas = filtrarLeccionesPorLenguaje(listLecciones, lenguajeSeleccionado);

        if (leccionesFiltradas.isEmpty()) {
            System.err.println("[RutaController] No se encontraron lecciones para el lenguaje seleccionado: " + lenguajeSeleccionado);
            return;
        }

        // 1. "JALAR" LA ESTRUCTURA DE DATOS YA PROCESADA
        // Le pedimos al manager la lista de cursos que él ya construyó al inicio de la app.
        List<Curso> cursos = AprendizajeManager.getInstancia().getCursos();

        // Comprobación de seguridad: ¿Se construyó algún curso con alguna ruta?
        if (cursos.isEmpty() || cursos.get(0).getRutas().isEmpty()) {
            System.err.println("[RutaController] No se encontraron rutas construidas en el Manager. La vista estará vacía.");
            return; // No hay nada que dibujar.
        }

        // Para esta demostración, tomamos la primera ruta del primer curso.
        // En una aplicación real, esta información (qué curso y qué ruta mostrar)
        // vendría de la pantalla anterior.
        Ruta rutaParaMostrar = cursos.get(0).getRutas().get(0);

        // 2. CONSTRUIR LA VISTA (LOS NODITOS) BASADO EN LOS DATOS OBTENIDOS
        construirNodosVisuales(rutaParaMostrar.getNodos(), leccionesFiltradas);

        // Mostrar el progreso de lecciones completadas
        System.out.println("Lecciones completadas: " + ProgresoLecciones.getLeccionesCompletadas());
    }

    /**
     * Dibuja los "noditos" en la pantalla a partir de la lista de NodoRuta.
     * @param nodosDeLaRuta La lista de objetos NodoRuta que ya tienen su lección asignada.
     */
    private void construirNodosVisuales(List<NodoRuta> nodosDeLaRuta, List<Leccion> listLecciones) {
        // Posiciones predefinidas (X, Y) para cada nodito en la pantalla.
        // Puedes añadir o quitar coordenadas para cambiar el diseño visual de la ruta.
        double[][] positions = {
                {50, 50}, {150, 120}, {80, 200}, {200, 280}, {120, 360}
        };

        System.out.println("[RutaController] Dibujando " + nodosDeLaRuta.size() + " nodos en la pantalla.");

        // Creamos un "nodito" visual por cada objeto NodoRuta que exista en el modelo.
        for (int i = 0; i < nodosDeLaRuta.size(); i++) {
            // Asegurarnos de no intentar dibujar más nodos que posiciones tengamos definidas.
            if (i >= positions.length) {
                System.err.println("[RutaController] Advertencia: Hay más nodos en el modelo que posiciones definidas. El nodo " + (i + 1) + " no se dibujará.");
                break;
            }

            NodoRuta nodo = nodosDeLaRuta.get(i);
            // Crear el botón que representará el nodo.
            Button nodoButton = new Button("L" + (i + 1));
            // (Puedes cambiar el color si el nodo está completado: nodo.isCompletado())
            nodoButton.setStyle("-fx-background-color: #50C878; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 50%; -fx-min-width: 80px; -fx-min-height: 80px; -fx-max-width: 80px; -fx-max-height: 80px; -fx-cursor: hand;");

            // Posicionar el botón en la pantalla usando su orden como índice.
            nodoButton.setLayoutX(positions[i][0]);
            nodoButton.setLayoutY(positions[i][1]);

            // Guardamos el objeto Leccion del nodo dentro del botón. Esto es crucial.
            Leccion leccionDelNodo = listLecciones.get(i % listLecciones.size());
            nodoButton.setTooltip(new Tooltip("Lección con " + leccionDelNodo.getNumEjercicios() + " ejercicios"));

            // --- CONFIGURACIÓN DEL CLIC PARA ABRIR LA LECCIÓN ---
            nodoButton.setOnAction(event -> abrirLeccion(leccionDelNodo));

            // Añadir el botón, ya configurado, al panel visual.
            nodoContainer.getChildren().add(nodoButton);
        }
    }

    /**
     * Maneja la acción del botón "Atrás", cargando la vista anterior.
     */
    @FXML
    private void manejarAtras() {
        try {
            // Cambia esta ruta a la vista a la que quieras regresar.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/DashboardEstudiante.fxml"));
            AnchorPane dashboardPane = loader.load();
            rootPane.getChildren().setAll(dashboardPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private List<Leccion> filtrarLeccionesPorLenguaje(List<Leccion> lecciones, String lenguaje) {
        List<Leccion> leccionesFiltradas = new ArrayList<>();
        for (Leccion leccion : lecciones) {
            if (leccion.getListEjercicios().stream().anyMatch(ejercicio -> 
                ejercicio.getLenguaje().name().equalsIgnoreCase(lenguaje))) {
                leccionesFiltradas.add(leccion);
            }
        }
        return leccionesFiltradas;
    }

    private void abrirLeccion(Leccion leccion) {
        try {
            // Incrementar el progreso de lecciones completadas
            ProgresoLecciones.incrementarLeccionesCompletadas();

            // Mostrar la lección utilizando el método del controlador de lecciones
            LeccionUIController.mostrarUnaLeccion(
                leccion,
                (Stage) rootPane.getScene().getWindow(),
                "/GestionAprendizaje_Modulo/Vistas/Ruta.fxml" // Ruta para regresar a la pantalla de ruta
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al abrir la lección: " + e.getMessage());
        }
    }
}