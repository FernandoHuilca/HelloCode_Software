package controladores;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contexto del sistema que mantiene el estado global de la aplicación.
 * Actúa como un singleton para compartir datos entre controladores.
 */
public class ContextoSistema {
    private static ContextoSistema instancia;
    
    private List<UsuarioTemp> usuarios;
    private List<Comunidad> comunidades;
    private List<Moderador> moderadores;
    private Comunidad comunidadActual;
    
    private ContextoSistema() {
        this.usuarios = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.moderadores = new ArrayList<>();
    }
    
    public static ContextoSistema getInstancia() {
        if (instancia == null) {
            instancia = new ContextoSistema();
        }
        return instancia;
    }
    
    // Getters
    public List<UsuarioTemp> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    public List<Comunidad> getComunidades() {
        return new ArrayList<>(comunidades);
    }
    
    public List<Moderador> getModeradores() {
        return new ArrayList<>(moderadores);
    }
    
    public Comunidad getComunidadActual() {
        return comunidadActual;
    }
    
    // Métodos para gestionar usuarios
    public void agregarUsuario(UsuarioTemp usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }
    
    public void eliminarUsuario(UsuarioTemp usuario) {
        usuarios.remove(usuario);
    }
    
    // Métodos para gestionar comunidades
    public void agregarComunidad(Comunidad comunidad) {
        if (!comunidades.contains(comunidad)) {
            comunidades.add(comunidad);
        }
    }
    
    public void eliminarComunidad(Comunidad comunidad) {
        comunidades.remove(comunidad);
        if (comunidadActual == comunidad) {
            comunidadActual = null;
        }
    }
    
    public void setComunidadActual(Comunidad comunidad) {
        this.comunidadActual = comunidad;
    }
    
    // Métodos para gestionar moderadores
    public void agregarModerador(Moderador moderador) {
        if (!moderadores.contains(moderador)) {
            moderadores.add(moderador);
        }
    }
    
    public void eliminarModerador(Moderador moderador) {
        moderadores.remove(moderador);
    }
    
    // Método para limpiar el contexto (útil para testing)
    public void limpiar() {
        usuarios.clear();
        comunidades.clear();
        moderadores.clear();
        comunidadActual = null;
    }
    
    // Métodos de utilidad
    public boolean tieneComunidadActiva() {
        return comunidadActual != null;
    }
    
    public int getTotalUsuarios() {
        return usuarios.size();
    }
    
    public int getTotalComunidades() {
        return comunidades.size();
    }
}
