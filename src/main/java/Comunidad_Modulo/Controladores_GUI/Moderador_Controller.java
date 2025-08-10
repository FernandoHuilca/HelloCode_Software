package Comunidad_Modulo.Controladores_GUI;

import Comunidad_Modulo.controladores.ContextoSistema;
import Comunidad_Modulo.modelo.Comunidad;
import Conexion.MetodosFrecuentes;
import Modulo_Usuario.Clases.UsuarioComunidad;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import Comunidad_Modulo.modelo.ForoGeneral;
import Comunidad_Modulo.modelo.GrupoDiscusion;
import Comunidad_Modulo.modelo.GrupoCompartir;
import Comunidad_Modulo.modelo.Solucion;

import java.util.List;
import java.util.stream.Collectors;

public class Moderador_Controller implements Initializable {

    @FXML private Button btnEliminarUsuario;
    @FXML private Button btnListarUsuarios;
    @FXML private TextField txtNombreUsuarioEliminar;
    @FXML private TextField txtNombreComunidad;
    @FXML private TextArea txtAreaInformacion;
    @FXML private Button btnEliminarComunidad;
    @FXML private TextField txtComunidadEliminar;
    @FXML private Button btnVolver;
    @FXML private ComboBox<String> comboComunidades;
    @FXML private ComboBox<String> comboTipoGrupo;
    @FXML private ComboBox<String> comboGrupos;
    @FXML private Button btnVerHistorial;
    @FXML private TextArea txtAreaHistorial;

    private ContextoSistema contexto;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("🔄 Inicializando controlador de Moderador...");
        inicializarSistema();
        configuracionInterfazModerador();
        actualizarInformacion();
        configurarControlesHistorial();
    }

    private void inicializarSistema() {
        this.contexto = ContextoSistema.getInstance();
    }

    private void configuracionInterfazModerador() {
        txtAreaInformacion.setEditable(false);
        txtAreaInformacion.setWrapText(true);
        txtNombreUsuarioEliminar.setPromptText("Username");
        txtNombreComunidad.setPromptText("Nombre Comunidad");
        txtComunidadEliminar.setPromptText("Nombre de la Comunidad a Eliminar");
    }

    /* Eliminación de un Usuario de una Comunidad */
    @FXML
    public void eliminarUsuario() {
        String nombreUsuario = txtNombreUsuarioEliminar.getText().trim();
        String nombreComunidad = txtNombreComunidad.getText().trim();

        if (nombreUsuario.isEmpty()) {
            mostrarMensajeError("Por favor, ingrese el nombre del usuario que desea eliminar.");
            return;
        }

        if (nombreComunidad.isEmpty()) {
            mostrarMensajeError("Por favor, ingrese el nombre de la comunidad.");
            return;
        }

        // Buscar la comunidad específica
        Optional<Comunidad> comunidadOpt = contexto.getComunidades().stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombreComunidad))
                .findFirst();

        if (!comunidadOpt.isPresent()) {
            mostrarMensajeError("No se encontró la comunidad '" + nombreComunidad + "'.");
            return;
        }

        Comunidad comunidad = comunidadOpt.get();
        
        // Buscar el usuario en la comunidad específica
        Optional<UsuarioComunidad> usuarioOpt = comunidad.getUsuariosMiembros().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(nombreUsuario))
                .findFirst();

        if (!usuarioOpt.isPresent()) {
            mostrarMensajeError("El usuario '" + nombreUsuario + "' no se encuentra en la comunidad '" +
                        nombreComunidad + "'.");
            return;
        }

        UsuarioComunidad usuario = usuarioOpt.get();

        // Eliminar el usuario de la comunidad específica
        comunidad.getUsuariosConectados().removeIf(u -> 
            u.getUsername().equalsIgnoreCase(nombreUsuario));
        comunidad.getUsuariosMiembros().remove(usuario);
        
        // Actualizar la comunidad en el contexto
        contexto.actualizarComunidad(comunidad);
        
        // Desconectar al usuario del sistema
        contexto.desconectarUsuarioDeComunidad(usuario, comunidad);
        
        mostrarMensajeExito("Usuario '" + nombreUsuario + "' eliminado exitosamente de la comunidad '" + 
                           nombreComunidad + "'");

        // Limpiar los campos
        txtNombreUsuarioEliminar.clear();
        txtNombreComunidad.clear();
        actualizarInformacion();
    }

    /**
     * Eliminacion de una Comunidad
     * */

    @FXML
    public void eliminarComunidad() {
        String nombreComunidad = txtComunidadEliminar.getText().trim();

        if (nombreComunidad.isEmpty()) {
            mostrarMensajeError("Por favor, ingrese el nombre de la comunidad que desea eliminar.");
            return;
        }

        // Buscar la comunidad
        Optional<Comunidad> comunidadOpt = contexto.getComunidades().stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombreComunidad))
                .findFirst();

        if (!comunidadOpt.isPresent()) {
            mostrarMensajeError("No se encontró la comunidad '" + nombreComunidad + "'.");
            return;
        }

        // Mostrar diálogo de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro que desea eliminar la comunidad '" + nombreComunidad + "'?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Comunidad comunidad = comunidadOpt.get();

            // Desconectar a todos los usuarios de la comunidad
            for (UsuarioComunidad usuario : comunidad.getUsuariosConectados()) {
                contexto.desconectarUsuarioDeComunidad(usuario, comunidad);
            }

            // Eliminar la comunidad del contexto
            contexto.eliminarComunidad(comunidad);

            mostrarMensajeExito("Comunidad '" + nombreComunidad + "' eliminada exitosamente");

            // Limpiar el campo y actualizar la información
            txtComunidadEliminar.clear();
            actualizarInformacion();

            // Actualizar los ComboBox de comunidades
            cargarComunidades();
        }
    }

    @FXML
    public void listarUsuarios() {
        StringBuilder lista = new StringBuilder("👥 Lista de Usuarios por Comunidad:\n\n");

        for (Comunidad comunidad : contexto.getComunidades()) {
            lista.append("🌐 Comunidad: ").append(comunidad.getNombre()).append("\n");
            
            if (comunidad.getUsuariosMiembros().isEmpty()) {
                lista.append("   • Sin usuarios registrados\n");
            } else {
                for (UsuarioComunidad usuario : comunidad.getUsuariosMiembros()) {
                    boolean estaConectado = comunidad.getUsuariosConectados().stream()
                            .anyMatch(u -> u.getUsername().equals(usuario.getUsername()));
                    
                    lista.append("   • ").append(usuario.getUsername())
                         .append(" [").append(estaConectado ? "✅ Conectado" : "❌ Desconectado").append("]\n");
                }
            }
            lista.append("\n");
        }

        txtAreaInformacion.setText(lista.toString());
    }

    @FXML
    private void volver() {
        try {
            MetodosFrecuentes.cambiarVentana(
                    (Stage) btnVolver.getScene().getWindow(),
                    "/Modulo_Comunidad/Views/Admin/AdminComunidad.fxml",
                    "Sistema de Comunidad"
            );
        } catch (Exception e) {
            mostrarMensajeError("Error al volver: " + e.getMessage());
        }
    }

    private void actualizarInformacion() {
        listarUsuarios();
    }

    private void mostrarMensajeError(String mensaje) {
        txtAreaInformacion.setStyle("-fx-text-fill: red; -fx-background-color: #fff0f0; -fx-border-color: #ffcccc;");
        txtAreaInformacion.setText("❌ " + mensaje);
        restaurarEstilosDespuesDe(4);
    }

    private void mostrarMensajeExito(String mensaje) {
        txtAreaInformacion.setStyle("-fx-text-fill: green; -fx-background-color: #f0fff0; -fx-border-color: #90ee90;");
        txtAreaInformacion.setText("✅ " + mensaje);
        restaurarEstilosDespuesDe(3);
    }

    private void restaurarEstilosDespuesDe(int segundos) {
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.seconds(segundos), e -> {
                    txtAreaInformacion.setStyle("");
                    actualizarInformacion();
                })
        );
        timeline.play();
    }
    
    private void configurarControlesHistorial() {
        // Configurar los ComboBox
        comboComunidades.setItems(FXCollections.observableArrayList());
        comboTipoGrupo.setItems(FXCollections.observableArrayList(
            "Grupos de Discusión", "Grupos de Compartir"
        ));
        comboGrupos.setItems(FXCollections.observableArrayList());
        
        // Cargar comunidades disponibles
        cargarComunidades();
        
        // Listeners para actualizar las opciones dependientes
        comboComunidades.setOnAction(e -> comboTipoGrupo.setDisable(false));
        comboTipoGrupo.setOnAction(e -> cargarGruposDeComunidad());
        
        // Deshabilitar controles dependientes inicialmente
        comboTipoGrupo.setDisable(true);
        comboGrupos.setDisable(true);
        btnVerHistorial.setDisable(true);
    }
    
    private void cargarComunidades() {
        List<String> nombresComunidades = contexto.getComunidades().stream()
                .map(Comunidad::getNombre)
                .collect(Collectors.toList());
        comboComunidades.setItems(FXCollections.observableArrayList(nombresComunidades));
    }
    
    private void cargarGruposDeComunidad() {
        comboGrupos.getItems().clear();
        
        String nombreComunidad = comboComunidades.getValue();
        String tipoGrupo = comboTipoGrupo.getValue();
        if (nombreComunidad == null || tipoGrupo == null) return;
        
        Optional<Comunidad> comunidadOpt = contexto.getComunidades().stream()
                .filter(c -> c.getNombre().equals(nombreComunidad))
                .findFirst();
                
        if (comunidadOpt.isPresent()) {
            Comunidad comunidad = comunidadOpt.get();
            ForoGeneral foro = comunidad.getForoGeneral();
            
            List<String> nombresGrupos;
            if (tipoGrupo.equals("Grupos de Discusión")) {
                nombresGrupos = foro.getGruposDiscusion().stream()
                        .map(GrupoDiscusion::getTitulo)
                        .collect(Collectors.toList());
            } else {
                nombresGrupos = foro.getGruposCompartir().stream()
                        .map(GrupoCompartir::getTitulo)
                        .collect(Collectors.toList());
            }
            
            comboGrupos.setItems(FXCollections.observableArrayList(nombresGrupos));
            comboGrupos.setDisable(false);
            btnVerHistorial.setDisable(false);
        }
    }
    
    @FXML
    private void verHistorial() {
        String nombreComunidad = comboComunidades.getValue();
        String tipoGrupo = comboTipoGrupo.getValue();
        String nombreGrupo = comboGrupos.getValue();
        
        if (nombreComunidad == null || tipoGrupo == null || nombreGrupo == null) {
            mostrarMensajeError("Por favor, complete todas las selecciones.");
            return;
        }
        
        try {
            StringBuilder historial = new StringBuilder();
            historial.append("📜 Historial de la Comunidad\n\n");
            historial.append("🌐 Comunidad: ").append(nombreComunidad).append("\n");
            historial.append("📋 Tipo: ").append(tipoGrupo).append("\n");
            historial.append("📝 Grupo: ").append(nombreGrupo).append("\n\n");
            
            Comunidad comunidad = contexto.getComunidades().stream()
                    .filter(c -> c.getNombre().equals(nombreComunidad))
                    .findFirst().get();
                    
            ForoGeneral foro = comunidad.getForoGeneral();
            
            if (tipoGrupo.equals("Grupos de Discusión")) {
                GrupoDiscusion grupo = foro.getGruposDiscusion().stream()
                        .filter(g -> g.getTitulo().equals(nombreGrupo))
                        .findFirst().get();
                        
                // Mostrar información del grupo de discusión
                historial.append("Nivel Java: ").append(grupo.getNivelJava()).append("\n");
                historial.append("Tema: ").append(grupo.getTipoTema()).append("\n\n");
                
                // Mostrar miembros específicos del grupo
                historial.append("👥 Miembros del Grupo: ").append(grupo.getMiembros().size()).append("\n");
                for (UsuarioComunidad miembro : grupo.getMiembros()) {
                    historial.append("  • ").append(miembro.getUsername()).append(" [Unido al grupo]\n");
                }
                
                // Mostrar todos los miembros de la comunidad
                historial.append("\n👥 Todos los miembros de la comunidad: ").append(comunidad.getUsuariosMiembros().size()).append("\n");
                for (UsuarioComunidad miembro : comunidad.getUsuariosMiembros()) {
                    boolean estaEnGrupo = grupo.getMiembros().stream()
                            .anyMatch(m -> m.getUsername().equals(miembro.getUsername()));
                    boolean estaConectado = comunidad.getUsuariosConectados().stream()
                            .anyMatch(u -> u.getUsername().equals(miembro.getUsername()));
                    
                    String estado = estaEnGrupo ? "[✅ En grupo]" : "[⚪ Necesita unirse al grupo]";
                    String conexion = estaConectado ? " [🟢 Conectado]" : " [🔴 Desconectado]";
                    historial.append("  • ").append(miembro.getUsername()).append(" ").append(estado).append(conexion).append("\n");
                }
                
                if (grupo.getMiembros().isEmpty()) {
                    historial.append("\n💡 Tip: Los usuarios deben ir a 'Gestión de Foro' y usar 'Unirse a Grupo' para participar en este grupo específico.\n");
                }
                
            } else {
                GrupoCompartir grupo = foro.getGruposCompartir().stream()
                        .filter(g -> g.getTitulo().equals(nombreGrupo))
                        .findFirst().get();
                        
                // Mostrar información del grupo de compartir
                historial.append("Nivel Java: ").append(grupo.getNivelJava()).append("\n");
                historial.append("Tema: ").append(grupo.getTipoTema()).append("\n\n");
                
                // Mostrar miembros específicos del grupo
                historial.append("👥 Miembros del Grupo: ").append(grupo.getMiembros().size()).append("\n");
                for (UsuarioComunidad miembro : grupo.getMiembros()) {
                    historial.append("  • ").append(miembro.getUsername()).append(" [Unido al grupo]\n");
                }
                
                // Mostrar todos los miembros de la comunidad
                historial.append("\n👥 Todos los miembros de la comunidad: ").append(comunidad.getUsuariosMiembros().size()).append("\n");
                for (UsuarioComunidad miembro : comunidad.getUsuariosMiembros()) {
                    boolean estaEnGrupo = grupo.getMiembros().stream()
                            .anyMatch(m -> m.getUsername().equals(miembro.getUsername()));
                    boolean estaConectado = comunidad.getUsuariosConectados().stream()
                            .anyMatch(u -> u.getUsername().equals(miembro.getUsername()));
                    
                    String estado = estaEnGrupo ? "[✅ En grupo]" : "[⚪ Necesita unirse al grupo]";
                    String conexion = estaConectado ? " [🟢 Conectado]" : " [🔴 Desconectado]";
                    historial.append("  • ").append(miembro.getUsername()).append(" ").append(estado).append(conexion).append("\n");
                }
                
                if (grupo.getMiembros().isEmpty()) {
                    historial.append("\n💡 Tip: Los usuarios deben ir a 'Gestión de Foro' y usar 'Unirse a Grupo' para participar en este grupo específico.\n");
                }
                
                historial.append("\n💡 Soluciones Compartidas:\n");
                for (Solucion solucion : grupo.getSoluciones()) {
                    historial.append("  👤 Autor: ").append(solucion.getAutor().getUsername()).append("\n");
                    historial.append("  👍 Likes: ").append(solucion.getLikes()).append("\n\n");
                }
            }
            
            txtAreaHistorial.setText(historial.toString());
            
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar el historial: " + e.getMessage());
        }
    }
}