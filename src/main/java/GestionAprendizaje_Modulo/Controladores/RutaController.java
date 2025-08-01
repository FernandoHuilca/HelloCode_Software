package GestionAprendizaje_Modulo.Controladores;

import java.io.IOException;
import java.util.List;

import GestionAprendizaje_Modulo.Logica.AprendizajeManager;
import GestionAprendizaje_Modulo.Logica.Curso;
import GestionAprendizaje_Modulo.Logica.NodoRuta;
import GestionAprendizaje_Modulo.Logica.Ruta;
import MetodosGlobales.MetodosFrecuentes;
import MetodosGlobales.SesionManager;
import Modulo_Usuario.Clases.Usuario;
import Nuevo_Modulo_Leccion.controllers.LeccionUIController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RutaController {

    @FXML private Pane nodoContainer;    // El panel donde se dibujarán los nodos.
    @FXML private AnchorPane rootPane;   // El panel raíz de la ventana, para obtener el Stage.
    @FXML private Label tituloLenguajeLabel; // El label para mostrar el título de la ruta.

    // Botones de la parte superior
    @FXML private Button btnRecursos;

    // Botones de la barra inferior
    @FXML private Button btnHome;
    @FXML private Button btnAdd;
    @FXML private Button btnLibrary;

    private Usuario usuarioActual;  // Usuario que está usando la aplicación

    @FXML
    private void initialize() {
        System.out.println("[RutaController] Inicializando...");

        // Configuración de la acción del botón 'Recursos'
        btnRecursos.setOnAction(event -> {
            System.out.println("Botón de Recursos presionado");
            abrirRecursos();  // Llamar al método para manejar la acción
        });

        // Cargar datos de prueba
        try {
            AprendizajeManager.getInstancia().construirDatosDePrueba();
        } catch (Exception e) {
            System.err.println("Error al cargar datos en RutaController");
            e.printStackTrace();
            tituloLenguajeLabel.setText("Error al cargar datos");
            return;
        }

        // Obtener el usuario actual
        this.usuarioActual = SesionManager.getInstancia().getUsuarioAutenticado();
        if (usuarioActual == null) {
            System.err.println("No hay usuario en la sesión.");
            tituloLenguajeLabel.setText("Usuario Desconocido");
            return;
        }

        System.out.println("[RutaController] Usuario: " + usuarioActual.getUsername());

        // Obtener la estructura de la ruta
        List<Curso> cursos = AprendizajeManager.getInstancia().getCursos();
        if (cursos.isEmpty() || cursos.get(0).getRutas().isEmpty()) {
            System.err.println("[RutaController] No hay cursos o rutas disponibles.");
            return;
        }

        // Cargar la ruta
        Ruta ruta = cursos.get(0).getRutas().get(0);
        tituloLenguajeLabel.setText(ruta.getNombre()); // Mostrar nombre de la ruta

        // Dibujar los nodos con el progreso del usuario
        construirNodosVisuales(ruta.getNodos());

        // Vincular los botones con sus respectivas acciones
        configurarBotones();
    }

    private void abrirRecursos() {
        try {
            // Cargar el archivo FXML de la vista de recursos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/Recursos.fxml"));
            AnchorPane recursosPane = loader.load();  // Cargar el contenido de la vista de recursos

            // Cambiar la escena en el Stage actual
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.getScene().setRoot(recursosPane); // Cambiar la escena al nuevo panel
        } catch (IOException e) {
            e.printStackTrace();  // Mostrar error si algo falla al cargar el FXML
        }
    }

    // Función para configurar los botones de la barra inferior
    private void configurarBotones() {
        // Acción para el botón Home
        btnHome.setOnAction(event -> {
            try {
                // Cargar el archivo FXML de la vista HomeUsuario
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modulo_Usuario/views/homeUsuario.fxml"));
                AnchorPane homePane = loader.load(); // Cargar el contenido del archivo FXML

                // Obtener el stage actual y cambiar la escena
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(new javafx.scene.Scene(homePane)); // Cambiar la escena por la nueva vista
                stage.show(); // Mostrar la nueva escena
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Acción para el botón Add
        btnAdd.setOnAction(event -> {
            try {
                // Cargar el archivo FXML de la vista HomeUsuario
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/Cursos.fxml"));
                AnchorPane cursos = loader.load(); // Cargar el contenido del archivo FXML

                // Obtener el stage actual y cambiar la escena
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(new javafx.scene.Scene(cursos)); // Cambiar la escena por la nueva vista
                stage.show(); // Mostrar la nueva escena
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Acción para el botón Library
        btnLibrary.setOnAction(event -> {
            System.out.println("Library button clicked!");
            // Aquí puedes agregar la acción para el botón de "📚".
            // Ejemplo: Navegar a una vista de biblioteca o recursos
        });
    }

    private void construirNodosVisuales(List<NodoRuta> nodosDeLaRuta) {
        // Limpiamos el contenedor antes de (re)dibujar para evitar duplicados al actualizar.
        nodoContainer.getChildren().clear();

        // Coordenadas (X, Y) para cada nodo.
        double[][] posiciones = {
                {50, 50}, {150, 120}, {80, 200}, {200, 280}, {120, 360}
        };

        System.out.println("[RutaController] Dibujando " + nodosDeLaRuta.size() + " nodos para el usuario " + usuarioActual.getUsername());

        for (NodoRuta nodo : nodosDeLaRuta) {
            if (nodo.getOrden() > posiciones.length) break;

            Button boton = new Button("L" + nodo.getOrden());

            // --- LÓGICA DE VISUALIZACIÓN DE PROGRESO ---
            // Le preguntamos al Manager si el USUARIO ACTUAL ha completado ESTE nodo.
            boolean estaCompletado = AprendizajeManager.getInstancia().isNodoCompletadoParaUsuario(usuarioActual, nodo);

            // Aplicamos un estilo diferente si el nodo está completado.
            if (estaCompletado) {
                // Estilo para nodo completado (ej. azul)
                boton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 50%; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px; -fx-cursor: hand;");
                boton.setTooltip(new Tooltip("Lección COMPLETADA"));
            } else {
                // Estilo para nodo pendiente (ej. verde)
                boton.setStyle("-fx-background-color: #50C878; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 50%; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px; -fx-cursor: hand;");
                boton.setTooltip(new Tooltip("Lección con " + nodo.getLeccion().getNumEjercicios() + " ejercicios"));
            }

            boton.setLayoutX(posiciones[nodo.getOrden() - 1][0]);
            boton.setLayoutY(posiciones[nodo.getOrden() - 1][1]);

            // Guardamos el objeto NODO completo en el botón para tener acceso a su ID y Leccion.
            boton.setUserData(nodo);

            // --- LÓGICA DE CLIC CON GUARDADO DE PROGRESO ---
            boton.setOnAction(event -> {
                NodoRuta nodoClicado = (NodoRuta) ((Button) event.getSource()).getUserData();

                // Abrimos la ventana de la lección del otro módulo.
                Stage stage = (Stage) rootPane.getScene().getWindow();
                LeccionUIController.mostrarUnaLeccion(nodoClicado.getLeccion(), stage, "/GestionAprendizaje_Modulo/Vistas/Ruta.fxml");

                // ¡ACCIÓN CLAVE! Marcamos el nodo como completado PARA EL USUARIO ACTUAL.
                // Esto guardará el progreso en la memoria del AprendizajeManager.
                AprendizajeManager.getInstancia().marcarNodoComoCompletado(usuarioActual, nodoClicado);

                // Volvemos a dibujar toda la ruta para que el cambio de color sea instantáneo.
                construirNodosVisuales(nodosDeLaRuta);
            });

            nodoContainer.getChildren().add(boton);
        }
    }



}
