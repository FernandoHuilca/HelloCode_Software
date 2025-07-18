package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Moderador {
    private String idModerador;
    private String nombre;
    private List<Comunidad> comunidadesGestionadas;
    
    public Moderador(String nombre) {
        this.idModerador = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.comunidadesGestionadas = new ArrayList<>();
    }
    
    // Getters y setters
    public String getIdModerador() {
        return idModerador;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public List<Comunidad> getComunidadesGestionadas() {
        return new ArrayList<>(comunidadesGestionadas);
    }
    
    // Métodos de negocio
    public void asignarComunidad(Comunidad comunidad) {
        if (!comunidadesGestionadas.contains(comunidad)) {
            comunidadesGestionadas.add(comunidad);
        }
    }
    
    public void removerComunidad(Comunidad comunidad) {
        comunidadesGestionadas.remove(comunidad);
    }
    
    public void moderarForo(ForoGeneral foro) {
        // Lógica de moderación del foro
        System.out.println("Moderando foro: " + foro.toString());
    }
    
    public void supervisarChats(List<ChatPrivado> chats) {
        // Lógica de supervisión de chats
        System.out.println("Supervisando " + chats.size() + " chats privados");
    }
    
    public void cerrarHilo(HiloDiscusion hilo) {
        hilo.cerrar();
        System.out.println("Hilo cerrado por moderador: " + hilo.getTitulo());
    }
    
    public void eliminarMensaje(ChatPrivado chat, String idMensaje) {
        // Lógica para eliminar mensaje (simulada)
        System.out.println("Mensaje eliminado por moderador en chat: " + chat.getIdChat());
    }
    
    public void suspenderUsuario(UsuarioTemp usuario) {
        // Lógica para suspender usuario (simulada)
        System.out.println("Usuario suspendido: " + usuario.getNombre());
    }
    
    @Override
    public String toString() {
        return String.format("Moderador: %s (gestiona %d comunidades)", 
                           nombre, comunidadesGestionadas.size());
    }
}
