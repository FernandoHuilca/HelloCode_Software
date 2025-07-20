package Comunidad_Modulo.Controladores_GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

// Importar las clases del modelo y controladores
import Comunidad_Modulo.controladores.ContextoSistema;
import Comunidad_Modulo.modelo.UsuarioTemp;
import Comunidad_Modulo.modelo.Comunidad;
import Comunidad_Modulo.enums.NivelJava;
import Comunidad_Modulo.integracion.ModuloUsuariosSimulado;
import Comunidad_Modulo.integracion.IModuloUsuarios;
import MetodosGlobales.MetodosFrecuentes;

/**
 * Controlador para la gestión de usuarios en la interfaz JavaFX
 * Implementa los principios SOLID y se integra con los controladores existentes
 */
public class GestionUsuarios_Controller implements Initializable {

    // Referencias FXML a los elementos de la interfaz
    @FXML private TextField txtNombreUsuario;
    @FXML private ComboBox<NivelJava> comboNivelJava;
    @FXML private Button btnCrearUsuario;

    @FXML private TextArea txtListaUsuarios;
    @FXML private Button btnListarUsuarios;

    @FXML private TextField txtIndiceUsuario;
    @FXML private Button btnConectarUsuario;
    @FXML private Button btnDesconectarUsuario;

    @FXML private TextArea txtInformacion;
    @FXML private Button btnVolver;

    // Controladores de negocio reutilizados del sistema existente
    private ContextoSistema contexto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inicializando controlador de Gestión de Usuarios...");

        // Inicializar los componentes del sistema
        inicializarSistema();

        // Configurar la interfaz
        configurarInterfaz();

        // Actualizar información inicial
        actualizarInformacion();

        System.out.println("✅ Controlador de Gestión de Usuarios inicializado correctamente");
    }

    /**
     * Inicializar el sistema con los componentes existentes
     */
    private void inicializarSistema() {
        // Obtener la instancia del contexto (singleton)
        this.contexto = ContextoSistema.getInstance();

        // Si no hay módulo de usuarios configurado, usar el simulado
        if (contexto.getModuloUsuarios() == null) {
            IModuloUsuarios moduloUsuarios = new ModuloUsuariosSimulado();
            contexto.setModuloUsuarios(moduloUsuarios);
        }
    }

    /**
     * Configurar elementos de la interfaz
     */
    private void configurarInterfaz() {
        // Configurar ComboBox de niveles de Java
        comboNivelJava.setItems(FXCollections.observableArrayList(NivelJava.values()));
        comboNivelJava.setValue(NivelJava.PRINCIPIANTE); // Valor por defecto

        // Configurar áreas de texto como solo lectura
        txtListaUsuarios.setEditable(false);
        txtListaUsuarios.setWrapText(true);
        txtInformacion.setEditable(false);
        txtInformacion.setWrapText(true);

        // Configurar placeholder texts
        txtNombreUsuario.setPromptText("Ingrese el nombre del usuario");
        txtIndiceUsuario.setPromptText("Ej: 1");

        // Estilo para las áreas de información
        txtListaUsuarios.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc;");
        txtInformacion.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc;");
    }

    /**
     * Método FXML para crear un nuevo usuario
     * Respeta Single Responsibility: Solo se encarga de crear usuarios
     */
    @FXML
    private void crearUsuario() {
        try {
            // Validar entrada de datos
            String nombre = txtNombreUsuario.getText().trim();
            NivelJava nivel = comboNivelJava.getValue();

            if (nombre.isEmpty()) {
                mostrarMensajeError("El nombre del usuario es obligatorio.");
                txtNombreUsuario.requestFocus();
                return;
            }

            if (nombre.length() < 2) {
                mostrarMensajeError("El nombre debe tener al menos 2 caracteres.");
                txtNombreUsuario.requestFocus();
                return;
            }

            if (nivel == null) {
                mostrarMensajeError("Debe seleccionar un nivel de Java.");
                comboNivelJava.requestFocus();
                return;
            }

            // Verificar si ya existe un usuario con ese nombre
            boolean nombreExiste = contexto.getUsuarios().stream()
                    .anyMatch(u -> u.getNombre().equalsIgnoreCase(nombre));

            if (nombreExiste) {
                mostrarMensajeError("Ya existe un usuario con ese nombre: " + nombre);
                txtNombreUsuario.requestFocus();
                return;
            }

            // Crear el usuario usando el modelo existente
            UsuarioTemp nuevoUsuario = new UsuarioTemp(nombre, nivel);
            contexto.agregarUsuario(nuevoUsuario);

            // Mostrar mensaje de éxito
            mostrarMensajeExito("✅ Usuario creado exitosamente: " + nuevoUsuario.toString());

            // Limpiar campos
            limpiarCamposCreacion();

            // Actualizar listas e información
            listarUsuarios();
            actualizarInformacion();

        } catch (Exception e) {
            mostrarMensajeError("Error al crear usuario: " + e.getMessage());
            System.err.println("Error en crearUsuario: " + e.getMessage());
        }
    }

    /**
     * Método FXML para listar todos los usuarios
     * Respeta Interface Segregation Principle: Interfaz específica para visualización
     */
    @FXML
    private void listarUsuarios() {
        try {
            List<UsuarioTemp> usuarios = contexto.getUsuarios();

            StringBuilder lista = new StringBuilder();
            lista.append("📋 USUARIOS REGISTRADOS\n");
            lista.append("========================\n\n");

            if (usuarios.isEmpty()) {
                lista.append("No hay usuarios registrados en el sistema.\n");
                lista.append("💡 Cree un nuevo usuario para comenzar.");
            } else {
                lista.append("Total: ").append(usuarios.size()).append(" usuario(s)\n\n");

                for (int i = 0; i < usuarios.size(); i++) {
                    UsuarioTemp usuario = usuarios.get(i);
                    lista.append(String.format("%d. %s\n", i + 1, usuario.toString()));
                    lista.append(String.format("   ID: %s\n", usuario.getIdUsuario()));
                    lista.append(String.format("   Amigos: %d\n", usuario.getAmigos().size()));

                    // Mostrar estado de conexión
                    boolean conectado = false;
                    if (contexto.getComunidadActual() != null) {
                        conectado = contexto.getComunidadActual().getUsuariosConectados().contains(usuario);
                    }
                    lista.append(String.format("   Estado: %s\n", conectado ? "✅ Conectado" : "❌ Desconectado"));
                    lista.append("\n");
                }
            }

            txtListaUsuarios.setText(lista.toString());
            mostrarMensajeInformacion("Lista actualizada con " + usuarios.size() + " usuario(s).");

        } catch (Exception e) {
            mostrarMensajeError("Error al listar usuarios: " + e.getMessage());
        }
    }

    /**
     * Método FXML para conectar un usuario a la comunidad activa
     * Respeta Open/Closed Principle: Extensible sin modificar código existente
     */
    @FXML
    private void conectarUsuario() {
        try {
            // Verificar que hay una comunidad activa
            if (!contexto.tieneComunidadActiva()) {
                mostrarMensajeError("No hay comunidad activa. Cree o seleccione una comunidad primero.");
                return;
            }

            // Validar entrada de datos
            String indiceTexto = txtIndiceUsuario.getText().trim();
            if (indiceTexto.isEmpty()) {
                mostrarMensajeError("Ingrese el índice del usuario (número de la lista).");
                txtIndiceUsuario.requestFocus();
                return;
            }

            int indice;
            try {
                indice = Integer.parseInt(indiceTexto);
            } catch (NumberFormatException e) {
                mostrarMensajeError("El índice debe ser un número válido.");
                txtIndiceUsuario.requestFocus();
                return;
            }

            // Validar que el índice esté en el rango correcto
            List<UsuarioTemp> usuarios = contexto.getUsuarios();
            if (usuarios.isEmpty()) {
                mostrarMensajeError("No hay usuarios registrados para conectar.");
                return;
            }

            if (indice < 1 || indice > usuarios.size()) {
                mostrarMensajeError(String.format("Índice inválido. Debe estar entre 1 y %d.", usuarios.size()));
                txtIndiceUsuario.requestFocus();
                return;
            }

            // Obtener el usuario y verificar si ya está conectado
            UsuarioTemp usuario = usuarios.get(indice - 1);
            Comunidad comunidadActual = contexto.getComunidadActual();

            if (comunidadActual.getUsuariosConectados().contains(usuario)) {
                mostrarMensajeError("El usuario '" + usuario.getNombre() + "' ya está conectado a la comunidad.");
                return;
            }

            // Conectar el usuario
            comunidadActual.conectarUsuario(usuario);

            mostrarMensajeExito(String.format("✅ Usuario '%s' conectado exitosamente a la comunidad '%s'.",
                    usuario.getNombre(), comunidadActual.getNombre()));

            // Limpiar campo y actualizar información
            txtIndiceUsuario.clear();
            listarUsuarios();
            actualizarInformacion();

        } catch (Exception e) {
            mostrarMensajeError("Error al conectar usuario: " + e.getMessage());
        }
    }

    /**
     * Método FXML para desconectar un usuario de la comunidad activa
     * Respeta Dependency Inversion Principle: Depende de abstracciones del contexto
     */
    @FXML
    private void desconectarUsuario() {
        try {
            // Verificar que hay una comunidad activa
            if (!contexto.tieneComunidadActiva()) {
                mostrarMensajeError("No hay comunidad activa.");
                return;
            }

            // Validar entrada de datos
            String indiceTexto = txtIndiceUsuario.getText().trim();
            if (indiceTexto.isEmpty()) {
                mostrarMensajeError("Ingrese el índice del usuario (número de la lista).");
                txtIndiceUsuario.requestFocus();
                return;
            }

            int indice;
            try {
                indice = Integer.parseInt(indiceTexto);
            } catch (NumberFormatException e) {
                mostrarMensajeError("El índice debe ser un número válido.");
                txtIndiceUsuario.requestFocus();
                return;
            }

            // Validar que el índice esté en el rango correcto
            List<UsuarioTemp> usuarios = contexto.getUsuarios();
            if (usuarios.isEmpty()) {
                mostrarMensajeError("No hay usuarios registrados.");
                return;
            }

            if (indice < 1 || indice > usuarios.size()) {
                mostrarMensajeError(String.format("Índice inválido. Debe estar entre 1 y %d.", usuarios.size()));
                txtIndiceUsuario.requestFocus();
                return;
            }

            // Obtener el usuario y verificar si está conectado
            UsuarioTemp usuario = usuarios.get(indice - 1);
            Comunidad comunidadActual = contexto.getComunidadActual();

            if (!comunidadActual.getUsuariosConectados().contains(usuario)) {
                mostrarMensajeError("El usuario '" + usuario.getNombre() + "' no está conectado a la comunidad.");
                return;
            }

            // Desconectar el usuario
            comunidadActual.desconectarUsuario(usuario);

            mostrarMensajeExito(String.format("✅ Usuario '%s' desconectado exitosamente de la comunidad '%s'.",
                    usuario.getNombre(), comunidadActual.getNombre()));

            // Limpiar campo y actualizar información
            txtIndiceUsuario.clear();
            listarUsuarios();
            actualizarInformacion();

        } catch (Exception e) {
            mostrarMensajeError("Error al desconectar usuario: " + e.getMessage());
        }
    }

    /**
     * Método FXML para volver a la pantalla anterior
     */
    @FXML
    private void volver() {
        try {
            // Cambiar a la ventana principal de comunidad
            MetodosFrecuentes.cambiarVentana(
                    (Stage) btnVolver.getScene().getWindow(),
                    "/Modulo_Comunidad/Views/Comunidad.fxml",
                    "Sistema de Comunidad"
            );
        } catch (Exception e) {
            mostrarMensajeError("Error de navegación: " + e.getMessage());
        }
    }

    /**
     * Actualizar la información mostrada en el área de información
     * Respeta Single Responsibility: Solo se encarga de actualizar la visualización
     */
    private void actualizarInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("=== INFORMACIÓN DEL SISTEMA ===\n\n");

        // Estadísticas generales
        info.append("📊 ESTADÍSTICAS:\n");
        info.append("• Total usuarios registrados: ").append(contexto.getTotalUsuarios()).append("\n");
        info.append("• Total comunidades: ").append(contexto.getTotalComunidades()).append("\n");
        info.append("• Total moderadores: ").append(contexto.getModeradores().size()).append("\n\n");

        // Información de la comunidad activa
        if (contexto.tieneComunidadActiva()) {
            Comunidad comunidad = contexto.getComunidadActual();
            info.append("🏛️ COMUNIDAD ACTIVA:\n");
            info.append("• Nombre: ").append(comunidad.getNombre()).append("\n");
            info.append("• Usuarios conectados: ").append(comunidad.getUsuariosConectados().size()).append("\n");
            info.append("• Chats privados: ").append(comunidad.getChatsPrivados().size()).append("\n");
            info.append("• Grupos discusión: ").append(comunidad.getForoGeneral().getGruposDiscusion().size()).append("\n");
            info.append("• Grupos compartir: ").append(comunidad.getForoGeneral().getGruposCompartir().size()).append("\n\n");
        } else {
            info.append("⚠️ COMUNIDAD ACTIVA:\n");
            info.append("No hay comunidad activa.\n");
            info.append("Los usuarios no se pueden conectar sin una comunidad activa.\n\n");
        }

        // Distribución por niveles
        if (!contexto.getUsuarios().isEmpty()) {
            info.append("📈 DISTRIBUCIÓN POR NIVELES:\n");
            long principiantes = contexto.getUsuarios().stream()
                    .filter(u -> u.getNivelJava() == NivelJava.PRINCIPIANTE)
                    .count();
            long intermedios = contexto.getUsuarios().stream()
                    .filter(u -> u.getNivelJava() == NivelJava.INTERMEDIO)
                    .count();
            long avanzados = contexto.getUsuarios().stream()
                    .filter(u -> u.getNivelJava() == NivelJava.AVANZADO)
                    .count();

            info.append(String.format("• Principiantes: %d\n", principiantes));
            info.append(String.format("• Intermedios: %d\n", intermedios));
            info.append(String.format("• Avanzados: %d\n", avanzados));
        }

        info.append("\n💡 Consejo: Use los números de la lista para conectar/desconectar usuarios.");

        txtInformacion.setText(info.toString());
    }

    /**
     * Limpiar los campos de creación de usuario
     */
    private void limpiarCamposCreacion() {
        txtNombreUsuario.clear();
        comboNivelJava.setValue(NivelJava.PRINCIPIANTE);
    }

    // ========== MÉTODOS DE UTILIDAD PARA MENSAJES ==========

    private void mostrarMensajeExito(String mensaje) {
        mostrarMensaje("✅ " + mensaje, "#d4edda");
    }

    private void mostrarMensajeInformacion(String mensaje) {
        mostrarMensaje("ℹ️ " + mensaje, "#d1ecf1");
    }

    private void mostrarMensajeError(String mensaje) {
        mostrarMensaje("❌ " + mensaje, "#f8d7da");
    }

    private void mostrarMensaje(String mensaje, String colorFondo) {
        // Mostrar mensaje temporal en el área de información
        String contenidoActual = txtInformacion.getText();
        txtInformacion.setText(mensaje + "\n\n" + contenidoActual);
        txtInformacion.setStyle(String.format("-fx-background-color: %s; -fx-border-color: #cccccc;", colorFondo));

        // Restaurar color original después de 3 segundos
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            txtInformacion.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc;");
        }));
        timeline.play();
    }
}
