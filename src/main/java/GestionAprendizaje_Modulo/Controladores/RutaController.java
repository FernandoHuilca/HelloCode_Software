package GestionAprendizaje_Modulo.Controladores;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Conexion.MetodosFrecuentes;
import Conexion.SesionManager;
import GestionAprendizaje_Modulo.Logica.AprendizajeManager;
import GestionAprendizaje_Modulo.Logica.ConfiguracionUsuarioService;
import GestionAprendizaje_Modulo.Logica.NodoRuta;
import GestionAprendizaje_Modulo.Logica.Ruta;
import GestionAprendizaje_Modulo.Modelo.RecursoAprendizaje;
import GestionAprendizaje_Modulo.Repositorio.RecursoRepository;
import Modulo_Usuario.Clases.Usuario;
import Nuevo_Modulo_Leccion.controllers.LeccionUIController;
import Nuevo_Modulo_Leccion.logic.TemaLeccion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * =================================================================================
 * Controlador de la Vista de Ruta - Versión Final y Definitiva
 * Esta versión está preparada para funcionar correctamente una vez que el SesionManager
 * reciba las actualizaciones del estado del usuario desde el módulo de lecciones.
 * =================================================================================
 */
public class RutaController {

    //<editor-fold desc="Variables FXML y de Estado">
    @FXML private VBox contenidoVBox;
    @FXML private Label tituloLenguajeLabel;
    @FXML private AnchorPane rootPane;
    @FXML private Button btnLibrary;
    @FXML private Button btnAdd;

    private Usuario usuarioActual;
    private Ruta rutaActual;
    private int nivelDesbloqueado = 1;
    //</editor-fold>

    @FXML
    private void initialize() {
        System.out.println("[RutaController] Inicializando vista de ruta...");
        AprendizajeManager.getInstancia().construirDatosDePrueba();
        // Asegurar que los recursos estén cargados desde el archivo TXT
        RecursoRepository.getInstancia().cargarRecursosDesdeTXT();
        cargarDatosIniciales();
        // Sincronizar banderas de Leccion con el .txt para el usuario actual antes de dibujar
        try {
            if (usuarioActual != null) {
                AprendizajeManager.getInstancia().sincronizarBanderasParaUsuario(usuarioActual.getUsername());
            }
        } catch (Exception ignored) {}
        construirContenedoresVisuales();
        configurarListenersBotones();
    }

    /**
     * Carga los datos esenciales: el usuario de la sesión y la ruta de aprendizaje activa.
     */
    private void cargarDatosIniciales() {
        this.usuarioActual = SesionManager.getInstancia().getUsuarioAutenticado();
        if (usuarioActual == null) {
            tituloLenguajeLabel.setText("ERROR: No hay usuario en sesión");
            return;
        }

        String lenguaje = DiagnosticoController.lenguajeSeleccionado;
        String nivel = DiagnosticoController.nivelSeleccionado;
        if (lenguaje == null || lenguaje.isEmpty()) {
            ConfiguracionUsuarioService.ConfiguracionUsuario config =
                    ConfiguracionUsuarioService.getInstancia().obtenerConfiguracion(usuarioActual.getUsername());
            if (config != null) {
                lenguaje = config.getLenguaje();
                nivel = config.getNivel();
            }
        }
        this.rutaActual = AprendizajeManager.getInstancia().getRutaPorNombre(lenguaje);

        if (this.rutaActual != null) {
            tituloLenguajeLabel.setText("Ruta de " + rutaActual.getNombre() + " - " + (nivel != null ? nivel : "Básico"));
        } else {
            tituloLenguajeLabel.setText("Contenido no disponible");
        }
    }

    /**
     * Orquesta la construcción de toda la interfaz visual de la ruta.
     * Este método se llama al inicio y cada vez que se necesita refrescar la vista.
     */
    private void construirContenedoresVisuales() {
        // --- PUNTO CLAVE DE SINCRONIZACIÓN ---
        // Se obtiene la versión más reciente del usuario desde la sesión.
        // Si el módulo de lección actualizó el SesionManager, aquí se reflejará.
        this.usuarioActual = SesionManager.getInstancia().getUsuarioAutenticado();
        if (this.usuarioActual == null || this.rutaActual == null) return;

        System.out.println("[RutaController] Redibujando. Vidas actuales del usuario: " + this.usuarioActual.getVidas());

        contenidoVBox.getChildren().clear();
        contenidoVBox.setSpacing(20);

        nivelDesbloqueado = calcularNivelDesbloqueado();
        int maxTemasVisibles = determinarTemasVisibles();
        Map<TemaLeccion, List<NodoRuta>> nodosPorTema = agruparNodosPorTema();

        int temaIndex = 0;
        for (Map.Entry<TemaLeccion, List<NodoRuta>> grupo : nodosPorTema.entrySet()) {
            boolean temaDesbloqueado = temaIndex < maxTemasVisibles;
            VBox contenedorUI = crearContenedorUI(grupo.getKey(), grupo.getValue(), temaDesbloqueado);
            contenidoVBox.getChildren().add(contenedorUI);
            temaIndex++;
        }
    }

    /**
     * Crea el contenedor de UI para un solo tema, aplicando la lógica de estado a cada nodo.
     * Aquí reside la lógica principal para estilizar y deshabilitar los botones de lección.
     */
    private VBox crearContenedorUI(TemaLeccion temaEnum, List<NodoRuta> nodos, boolean temaDesbloqueado) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 15; -fx-background-color: #FFFFFF1A; -fx-background-radius: 10;");

        Label tituloTema = new Label(capitalizar(temaEnum.name()));
        tituloTema.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        FlowPane nodosPane = new FlowPane(15, 15);
        nodos.sort(Comparator.comparingInt(NodoRuta::getOrden));

        for (NodoRuta nodo : nodos) {
            Button nodoBoton = new Button("L" + nodo.getOrden());
            nodoBoton.getStyleClass().add("nodo-boton");
            nodoBoton.setUserData(nodo);

            boolean estaCompletado = AprendizajeManager.getInstancia().isNodoCompletadoParaUsuario(usuarioActual, nodo);
            boolean tieneVidas = usuarioActual.getVidas() > 0;

            // Variable que define si el botón DEBERÍA ser clickeable.
            boolean puedeInteractuar = estaCompletado || (temaDesbloqueado && tieneVidas);

            // 1. Aplica estilo y deshabilita basado en las condiciones.
            if (estaCompletado) {
                nodoBoton.setStyle("-fx-background-color: #3498DB;"); // Azul
                nodoBoton.setTooltip(new Tooltip("Lección COMPLETADA. Puedes repasarla."));
            } else if (!temaDesbloqueado) {
                nodoBoton.setStyle("-fx-background-color: #cccccc; -fx-text-fill: #888;"); // Gris
                nodoBoton.setTooltip(new Tooltip("Desbloquea este tema avanzando de nivel."));
            } else if (!tieneVidas) {
                nodoBoton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white;"); // Rojo
                nodoBoton.setTooltip(new Tooltip("¡Te quedaste sin vidas! No puedes empezar lecciones nuevas."));
            } else {
                nodoBoton.setStyle("-fx-background-color: #50C878;"); // Verde
                nodoBoton.setTooltip(new Tooltip("Lección con " + nodo.getLeccion().getNumEjercicios() + " ejercicios."));
            }
            // ¡La barrera principal! El botón se deshabilita si no se puede interactuar.
            nodoBoton.setDisable(!puedeInteractuar);

            // 2. Lógica de clic segura.
            // Si el botón está deshabilitado, este código nunca se ejecuta.
            nodoBoton.setOnAction(e -> {
                NodoRuta nodoClicado = (NodoRuta) ((Button) e.getSource()).getUserData();
                Stage stage = (Stage) contenidoVBox.getScene().getWindow();
                LeccionUIController.mostrarUnaLeccion(nodoClicado.getLeccion(), stage, "/GestionAprendizaje_Modulo/Vistas/Ruta.fxml");
                construirContenedoresVisuales();
            });

            nodosPane.getChildren().add(nodoBoton);
        }

        Button verRecursosBtn = new Button("💡 Ver Recursos de " + capitalizar(temaEnum.name()));
        verRecursosBtn.setStyle("-fx-background-color: #A6B1E1; -fx-text-fill: #424874; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;");
        verRecursosBtn.setDisable(!temaDesbloqueado);
        verRecursosBtn.setOnAction(e -> abrirVentanaDeRecursos(capitalizar(temaEnum.name()), temaEnum));
        vbox.getChildren().addAll(tituloTema, nodosPane, verRecursosBtn);
        return vbox;
    }

    //<editor-fold desc="Métodos Auxiliares y de Navegación">
    private int determinarTemasVisibles() {
        String nivelDiagnostico = DiagnosticoController.nivelSeleccionado;
        int maxTemas = 1;
        if ("Intermedio".equalsIgnoreCase(nivelDiagnostico)) {
            maxTemas = 2;
        } else if ("Avanzado".equalsIgnoreCase(nivelDiagnostico)) {
            maxTemas = Integer.MAX_VALUE;
        }
        if (nivelDesbloqueado == 2 && maxTemas < 2) {
            maxTemas = 2;
        } else if (nivelDesbloqueado == 3) {
            maxTemas = Integer.MAX_VALUE;
        }
        return maxTemas;
    }

    private Map<TemaLeccion, List<NodoRuta>> agruparNodosPorTema() {
        return rutaActual.getNodos().stream()
                .filter(nodo -> nodo.getLeccion() != null && !nodo.getLeccion().getListEjercicios().isEmpty())
                .collect(Collectors.groupingBy(
                        nodo -> nodo.getLeccion().getListEjercicios().get(0).getTema(),
                        LinkedHashMap::new, Collectors.toList()
                ));
    }

    private int calcularNivelDesbloqueado() {
        int nivel = 1;
        Map<TemaLeccion, List<NodoRuta>> nodosPorTema = agruparNodosPorTema();
        if (estanTodosCompletos(nodosPorTema, 0, 1)) {
            nivel = 2;
            if (estanTodosCompletos(nodosPorTema, 0, 2)) {
                nivel = 3;
            }
        }
        return nivel;
    }

    private boolean estanTodosCompletos(Map<TemaLeccion, List<NodoRuta>> nodosPorTema, int start, int end) {
        int idx = 0;
        for (List<NodoRuta> nodos : nodosPorTema.values()) {
            if (idx >= start && idx < end) {
                for (NodoRuta nodo : nodos) {
                    if (!AprendizajeManager.getInstancia().isNodoCompletadoParaUsuario(usuarioActual, nodo)) {
                        return false;
                    }
                }
            }
            idx++;
        }
        return true;
    }

    private void configurarListenersBotones() {
        btnLibrary.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/MisLenguajes.fxml"));
                AnchorPane misLenguajesPane = loader.load();
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(new Scene(misLenguajesPane));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnAdd.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/SeleccionMultipleLenguajes.fxml"));
                AnchorPane seleccionPane = loader.load();
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(new Scene(seleccionPane));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void abrirVentanaDeRecursos(String temaNombre, TemaLeccion temaEnum) {
        try {
            // Corregido: usar el LENGUAJE (nombre de la ruta) y no el NIVEL para buscar recursos
            List<RecursoAprendizaje> recursos = RecursoRepository.getInstancia().buscarRecursosPorLenguajeYTema(
                    rutaActual.getNombre(), temaEnum.name());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionAprendizaje_Modulo/Vistas/RecursosTema.fxml"));
            Parent root = loader.load();
            RecursosTemaController controller = loader.getController();
            controller.setRecursos(temaNombre, recursos);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle("Recursos para " + temaNombre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        String conEspacios = texto.replace('_', ' ');
        return conEspacios.substring(0, 1).toUpperCase() + conEspacios.substring(1).toLowerCase();
    }

    @FXML
    private void manejarAtras() {
        try {
            MetodosFrecuentes.cambiarVentana((Stage) rootPane.getScene().getWindow(), "/Modulo_Usuario/views/homeUsuario.fxml", "Perfil de Usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>
}