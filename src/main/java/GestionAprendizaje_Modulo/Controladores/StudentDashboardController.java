package GestionAprendizaje_Modulo.Controladores;

import GestionAprendizaje_Modulo.Modelo.Curso;
import GestionAprendizaje_Modulo.Modelo.RecursoAprendizaje;
import GestionAprendizaje_Modulo.Repositorio.RepositorioEnMemoria;
import GestionAprendizaje_Modulo.Ruta.NodoRuta;
import GestionAprendizaje_Modulo.Ruta.Ruta;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class StudentDashboardController {

    // Misma fuente de datos que el administrador
    private final RepositorioEnMemoria repositorio = RepositorioEnMemoria.getInstancia();

    // Controles FXML del panel izquierdo (selección)
    @FXML private ComboBox<Curso> comboCursos;
    @FXML private ComboBox<Ruta> comboRutas;
    @FXML private ListView<NodoRuta> listNodos;

    // Controles FXML del panel derecho (visualización)
    @FXML private Label labelNodoSeleccionado;
    @FXML private Text textDescripcionLeccion;
    @FXML private VBox vboxMaterialApoyo;

    @FXML
    public void initialize() {
        configurarControles();
        cargarCursos();

        // Listener para la selección de nodos
        listNodos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarDetallesDelNodo(newSelection);
            } else {
                limpiarPanelDetalles();
            }
        });
    }

    private void configurarControles() {
        // --- Configuración de ComboBoxes ---
        comboCursos.setConverter(new StringConverter<>() {
            @Override public String toString(Curso curso) { return curso == null ? "" : curso.getNombre(); }
            @Override public Curso fromString(String string) { return null; }
        });

        comboRutas.setConverter(new StringConverter<>() {
            @Override public String toString(Ruta ruta) { return ruta == null ? "" : ruta.getNombre(); }
            @Override public Ruta fromString(String string) { return null; }
        });

        // --- Configuración de ListView de Nodos ---
        listNodos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(NodoRuta item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Muestra el progreso de forma visual
                    String prefix = item.estaCompletado() ? "[✔] " : "[ ] ";
                    setText(prefix + "Lección " + item.getOrden() + ": " + item.getLeccion().getTitulo());
                }
            }
        });
    }

    private void cargarCursos() {
        comboCursos.setItems(FXCollections.observableArrayList(repositorio.getCursos().values()));
    }

    @FXML
    void onCursoSeleccionado() {
        Curso curso = comboCursos.getSelectionModel().getSelectedItem();
        limpiarSeleccionRutaYNodo();
        if (curso != null) {
            comboRutas.setItems(FXCollections.observableArrayList(curso.getRutas()));
        }
    }

    @FXML
    void onRutaSeleccionada() {
        Ruta ruta = comboRutas.getSelectionModel().getSelectedItem();
        listNodos.getItems().clear();
        limpiarPanelDetalles();
        if (ruta != null) {
            listNodos.setItems(FXCollections.observableArrayList(ruta.getNodos()));
        }
    }

    private void mostrarDetallesDelNodo(NodoRuta nodo) {
        // Actualiza el panel derecho con la información del nodo seleccionado
        labelNodoSeleccionado.setText("Lección: " + nodo.getLeccion().getTitulo());
        textDescripcionLeccion.setText(nodo.getLeccion().getDescripcion());

        // Limpia el material de apoyo anterior y crea el nuevo
        vboxMaterialApoyo.getChildren().clear();

        if (nodo.getMaterialDeApoyo().isEmpty()) {
            vboxMaterialApoyo.getChildren().add(new Text("(No hay material de apoyo para esta lección)"));
        } else {
            for (RecursoAprendizaje recurso : nodo.getMaterialDeApoyo()) {
                // Para cada recurso, crea una mini-tarjeta con su información
                VBox cardRecurso = new VBox();
                Label tituloRecurso = new Label(recurso.getTitulo());
                tituloRecurso.setStyle("-fx-font-weight: bold;");

                Text detalleRecurso = new Text(recurso.obtenerDetalle());
                detalleRecurso.setWrappingWidth(300); // Para que el texto se ajuste

                cardRecurso.getChildren().addAll(tituloRecurso, detalleRecurso);
                vboxMaterialApoyo.getChildren().add(cardRecurso);
            }
        }

        // Simulamos que al ver el nodo, se marca como completado
        nodo.marcarComoCompletado();
        listNodos.refresh(); // Refresca la lista para mostrar el check [✔]
    }

    private void limpiarPanelDetalles() {
        labelNodoSeleccionado.setText("Contenido de la Lección");
        textDescripcionLeccion.setText("(Seleccione un nodo para ver la descripción)");
        vboxMaterialApoyo.getChildren().clear();
    }

    private void limpiarSeleccionRutaYNodo() {
        comboRutas.getItems().clear();
        listNodos.getItems().clear();
        limpiarPanelDetalles();
    }
}