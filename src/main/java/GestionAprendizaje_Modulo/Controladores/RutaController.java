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

/**
 * =================================================================================
 * Controlador de la Vi	sta de Ruta - Versión Final con Progreso de Usuario
 * =================================================================================
 * Este controlador es la capa de VISTA (presentación) de la ruta de aprendizaje.
 * Es consciente del usuario logueado y muestra su progreso individual,
 * permitiéndole interactuar con las lecciones y guardando su avance.
 */
public class RutaController {

    @FXML private Pane nodoContainer;    // El panel del FXML donde se dibujarán los nodos.
    @FXML private AnchorPane rootPane;   // El panel raíz de la ventana, para obtener el Stage.
    @FXML private Label tituloLenguajeLabel; // El label para mostrar el título de la ruta.

    // Botones de la parte superior
    @FXML private Button btnRecursos;

    // Botones de la barra inferior
    @FXML private Button btnHome;
    @FXML private Button btnAdd;
    @FXML private Button btnLibrary;

    /**
     * Atributo para mantener una referencia al usuario que está usando la pantalla.
     * Se obtiene del SesionManager al iniciar.
     */
    private Usuario usuarioActual;

    /**
     * El método initialize se ejecuta automáticamente después de que el FXML ha sido cargado.
     * Es el punto de partida para construir la vista dinámica.
     */
    @FXML
    private void initialize() {
        System.out.println("[RutaController] Inicializando...");
        System.out.println("[RutaController] Inicializando...");

        // Configuración de la acción del botón 'Recursos'
        btnRecursos.setOnAction(event -> {
            System.out.println("Botón de Recursos presionado");
            abrirRecursos();  // Llamar al método para manejar la acción
        });

        // --- ¡ACCIÓN CLAVE! LA CARGA DE DATOS SE INICIA AQUÍ ---
        try {
            // 1. Llamamos al manager para que construya toda la estructura de datos.
            AprendizajeManager.getInstancia().construirDatosDePrueba();
        } catch (Exception e) {
            System.err.println("!!! ERROR FATAL DURANTE LA CONSTRUCCIÓN DE DATOS en RutaController !!!");
            e.printStackTrace();
            // Mostrar un mensaje de error en la UI
            tituloLenguajeLabel.setText("Error al cargar datos");
            return; // Detener si la construcción falla.
        }
        // 1. OBTENER EL USUARIO ACTUAL DE LA SESIÓN
        this.usuarioActual = SesionManager.getInstancia().getUsuarioAutenticado();

        // Comprobación de seguridad: si no hay un usuario logueado, la funcionalidad
        // de progreso no funcionará. Es crucial manejar este caso.
        if (usuarioActual == null) {
            System.err.println("[RutaController] ¡ERROR CRÍTICO! No hay ningún usuario en la sesión. No se puede mostrar ni guardar el progreso.");
            tituloLenguajeLabel.setText("Usuario Desconocido");
            // En una aplicación real, aquí podrías redirigir a la pantalla de login.
            return;
        }
        System.out.println("[RutaController] Vista cargada para el usuario: " + usuarioActual.getUsername());

        // 2. OBTENER LA ESTRUCTURA DE LA RUTA (la "plantilla")
        // Se asume que AprendizajeManager ya fue inicializado al arrancar la app.
        List<Curso> cursos = AprendizajeManager.getInstancia().getCursos();

        if (cursos.isEmpty() || cursos.get(0).getRutas().isEmpty()) {
            System.err.println("[RutaController] No hay cursos o rutas disponibles en el Manager.");
            return;
        }

        // Para esta demo, mostramos la primera ruta del primer curso.
        Ruta ruta = cursos.get(0).getRutas().get(0);
        tituloLenguajeLabel.setText(ruta.getNombre()); // Actualizar el título de la vista

        // 3. DIBUJAR LA VISTA, AHORA CONSCIENTE DEL PROGRESO DEL USUARIO
        construirNodosVisuales(ruta.getNodos());

        // Vincular los botones con sus respectivas acciones
        configurarBotones();
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

    /**
     * Dibuja los "noditos" en la pantalla, comprobando el progreso del usuario para cada uno.
     * @param nodosDeLaRuta La lista de objetos NodoRuta que componen la ruta "plantilla".
     */
    private void construirNodosVisuales(List<NodoRuta> nodosDeLaRuta) {
        // Limpiamos el contenedor antes de (re)dibujar para evitar duplicados al actualizar.
        nodoContainer.getChildren().clear();

        // Coordenadas (X, Y) para cada nodo.
        double[][] posiciones = {
                {50, 50}, {150, 120}, {80, 200}, {200, 280}, {120, 360},
                {300, 100}, {400, 180}, {500, 260}, {350, 330}, {600, 400}  // Agregamos más posiciones
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
                boton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 50%; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px; -fx-cursor: hand;");
                boton.setTooltip(new Tooltip("Lección COMPLETADA"));
            } else {
                // Estilo para nodo pendiente (ej. verde)
                boton.setStyle("-fx-background-color: #50C878; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 50%; -fx-min-width: 40px; -fx-min-height: 40px; -fx-max-width: 40px; -fx-max-height: 40px; -fx-cursor: hand;");
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

    @FXML
    private void manejarAtras() {
        MetodosFrecuentes.cambiarVentana((Stage) rootPane.getScene().getWindow(), "/Modulo_Usuario/views/homeUsuario.fxml", "Perfil de Usuario");

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
}