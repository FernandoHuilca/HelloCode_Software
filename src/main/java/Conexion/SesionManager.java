package Conexion;

import Modulo_Usuario.Clases.Usuario;
import Modulo_Usuario.Clases.UsuarioComunidad;
import Modulo_Usuario.Clases.NivelJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de sesión global para mantener el usuario autenticado
 * a través de todos los módulos del sistema.
 */
public class SesionManager {
    private static SesionManager instancia;
    private Usuario usuarioAutenticado;
    private UsuarioComunidad usuarioComunidad;
    private List<Usuario> usuarios;
    private static final String ARCHIVO_USUARIOS = "src/main/java/Modulo_Usuario/Usuarios/usuarios.txt";

    private SesionManager() {}

    public static SesionManager getInstancia() {
        if (instancia == null) {
            instancia = new SesionManager();
        }
        return instancia;
    }

    public void guardarUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Inicia sesión con un usuario
     */
    public void iniciarSesion(Usuario usuario) {
        this.usuarioAutenticado = usuario;
        // Convertir Usuario a UsuarioComunidad para uso en el módulo de comunidad
        this.usuarioComunidad = convertirAUsuarioComunidad(usuario);
        System.out.println("Sesión iniciada para: " + usuario.getUsername());
        System.out.println("📊 Datos del usuario en sesión: XP=" + usuario.getXp() + ", Vidas=" + usuario.getVidas() + ", Nombre=" + usuario.getNombre());
    }

    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        this.usuarioAutenticado = null;
        this.usuarioComunidad = null;
        System.out.println("Sesión cerrada");
    }
    public List<Usuario> getUsuarios() {return usuarios;}
    /**
     * Verifica si hay un usuario autenticado
     */
    public boolean hayUsuarioAutenticado() {
        return usuarioAutenticado != null;
    }

    /**
     * Obtiene el usuario autenticado (formato original)
     */
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    /**
     * Obtiene el usuario autenticado en formato de comunidad
     */
    public UsuarioComunidad getUsuarioComunidad() {
        return usuarioComunidad;
    }

    /**
     * Obtiene el nombre del usuario actual
     */
    public String getNombreUsuarioActual() {
        if (usuarioAutenticado != null) {
            return usuarioAutenticado.getNombre() != null && !usuarioAutenticado.getNombre().equals("null")
                    ? usuarioAutenticado.getNombre()
                    : usuarioAutenticado.getUsername();
        }
        return "Usuario no autenticado";
    }

    /**
     * Obtiene el username del usuario actual
     */
    public String getUsernameActual() {
        return usuarioAutenticado != null ? usuarioAutenticado.getUsername() : null;
    }

    /**
     * Convierte un Usuario a UsuarioComunidad
     */
    private UsuarioComunidad convertirAUsuarioComunidad(Usuario usuario) {
        // Determinar nombre para mostrar
        String nombreDisplay = usuario.getNombre() != null && !usuario.getNombre().equals("null")
                ? usuario.getNombre()
                : usuario.getUsername();

        // Determinar email
        String emailDisplay = usuario.getEmail() != null && !usuario.getEmail().equals("null")
                ? usuario.getEmail()
                : "";

        // Crear UsuarioComunidad con datos del usuario original
        UsuarioComunidad usuarioComunidad = new UsuarioComunidad(
                usuario.getUsername(),
                usuario.getPassword(),
                nombreDisplay,
                emailDisplay,
                usuario.getUsername(), // usar username como ID
                NivelJava.PRINCIPIANTE, // nivel por defecto
                0 // reputación inicial
        );

        return usuarioComunidad;
    }

    /**
     * Actualiza el nivel Java del usuario en la sesión
     */
    public void actualizarNivelJava(NivelJava nuevoNivel) {
        if (usuarioComunidad != null) {
            usuarioComunidad.setNivelJava(nuevoNivel);
        }
    }

    /**
     * Actualiza la reputación del usuario en la sesión
     */
    public void actualizarReputacion(int nuevaReputacion) {
        if (usuarioComunidad != null) {
            usuarioComunidad.setReputacion(nuevaReputacion);
        }
    }
    public void notificarNuevoUsuario() {
        System.out.println("🔔 Notificación: Se ha creado un nuevo usuario, recargando lista...");
        forzarRecarga();
    }
    public void forzarRecarga() {
        System.out.println("🔄 Forzando recarga completa de usuarios...");
        cargarUsuariosDesdeArchivo();
    }
    public void cargarUsuariosDesdeArchivo() {
        this.usuarios = new ArrayList<>();

        try {
            File file = new File(ARCHIVO_USUARIOS);
            if (!file.exists()) {
                System.err.println("⚠️ Archivo usuarios.txt no encontrado: " + ARCHIVO_USUARIOS);
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    linea = linea.trim();
                    if (!linea.isEmpty()) {
                        Usuario usuario = Usuario.fromString(linea);
                        if (usuario != null) {
                            this.usuarios.add(usuario);
                        }
                    }
                }
            }

            System.out.println("✅ Cargados " + this.usuarios.size() + " usuarios desde archivo");

        } catch (IOException e) {
            System.err.println("❌ Error al cargar usuarios desde archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}