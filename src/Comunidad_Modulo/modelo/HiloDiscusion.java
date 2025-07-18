package modelo;

import enums.EstadoHilo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HiloDiscusion {
    private String idHilo;
    private String titulo;
    private String problema;
    private UsuarioTemp autor;
    private EstadoHilo estado;
    private List<Respuesta> respuestas;
    private Integer votos;
    
    public HiloDiscusion(String titulo, String problema, UsuarioTemp autor) {
        this.idHilo = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.problema = problema;
        this.autor = autor;
        this.estado = EstadoHilo.ABIERTO;
        this.respuestas = new ArrayList<>();
        this.votos = 0;
    }
    
    // Getters y setters
    public String getIdHilo() {
        return idHilo;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getProblema() {
        return problema;
    }
    
    public void setProblema(String problema) {
        this.problema = problema;
    }
    
    public UsuarioTemp getAutor() {
        return autor;
    }
    
    public EstadoHilo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoHilo estado) {
        this.estado = estado;
    }
    
    public List<Respuesta> getRespuestas() {
        return new ArrayList<>(respuestas);
    }
    
    public Integer getVotos() {
        return votos;
    }
    
    // Métodos de negocio
    public void responder(String contenido, UsuarioTemp autor) {
        if (estado == EstadoHilo.CERRADO) {
            throw new IllegalStateException("No se puede responder a un hilo cerrado");
        }
        
        Respuesta respuesta = new Respuesta(contenido, autor);
        respuestas.add(respuesta);
        
        // Otorgar puntos de reputación por participar
        autor.aumentarReputacion(2);
    }
    
    public void votar(boolean esPositivo) {
        if (esPositivo) {
            this.votos++;
            autor.aumentarReputacion(1);
        } else {
            this.votos--;
            autor.disminuirReputacion(1);
        }
    }
    
    public void marcarResuelto() {
        this.estado = EstadoHilo.RESUELTO;
        // Otorgar puntos de reputación por resolver problema
        autor.aumentarReputacion(5);
    }
    
    public void cerrar() {
        this.estado = EstadoHilo.CERRADO;
    }
    
    public void reabrir() {
        if (estado == EstadoHilo.CERRADO) {
            this.estado = EstadoHilo.ABIERTO;
        }
    }
    
    public boolean tieneSolucion() {
        return respuestas.stream().anyMatch(Respuesta::getEsSolucion);
    }
    
    public List<Respuesta> getSoluciones() {
        return respuestas.stream()
                        .filter(Respuesta::getEsSolucion)
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public String toString() {
        return String.format("Hilo: %s por %s [%s] (%d votos, %d respuestas)", 
                           titulo, autor.getNombre(), estado, votos, respuestas.size());
    }
}
