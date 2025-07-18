package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatPrivado {
    private String idChat;
    private List<UsuarioTemp> participantes;
    private List<Mensaje> mensajes;
    private LocalDateTime fechaCreacion;
    
    public ChatPrivado(List<UsuarioTemp> participantes) {
        this.idChat = UUID.randomUUID().toString();
        this.participantes = new ArrayList<>(participantes);
        this.mensajes = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters
    public String getIdChat() {
        return idChat;
    }
    
    public List<UsuarioTemp> getParticipantes() {
        return new ArrayList<>(participantes);
    }
    
    public List<Mensaje> getMensajes() {
        return new ArrayList<>(mensajes);
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    // Métodos de negocio
    public void enviarMensaje(String contenido, UsuarioTemp emisor) {
        if (participantes.contains(emisor)) {
            Mensaje mensaje = new Mensaje(contenido, emisor);
            mensajes.add(mensaje);
        } else {
            throw new IllegalArgumentException("El usuario no es participante del chat");
        }
    }
    
    public List<Mensaje> obtenerHistorial() {
        // Marcar todos los mensajes como leídos
        mensajes.forEach(Mensaje::marcarComoLeido);
        return new ArrayList<>(mensajes);
    }
    
    public void agregarParticipante(UsuarioTemp usuario) {
        if (!participantes.contains(usuario)) {
            participantes.add(usuario);
        }
    }
    
    public void eliminarParticipante(UsuarioTemp usuario) {
        participantes.remove(usuario);
    }
    
    public int getMensajesNoLeidos() {
        return (int) mensajes.stream()
                           .filter(m -> !m.isLeido())
                           .count();
    }
    
    @Override
    public String toString() {
        return String.format("Chat: %s participantes, %d mensajes", 
                           participantes.size(), mensajes.size());
    }
}
