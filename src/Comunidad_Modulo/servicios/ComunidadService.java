package servicios;

import modelo.*;
import java.util.List;
import java.util.stream.Collectors;

public class ComunidadService {
    
    public void procesarUsuarioEnGrupo(UsuarioTemp usuario, GrupoDiscusion grupo) {
        if (grupo.esApropiado(usuario)) {
            grupo.unirseGrupo(usuario);
            System.out.println("Usuario " + usuario.getNombre() + " se unió al grupo: " + grupo.getTitulo());
        } else {
            System.out.println("El usuario no cumple con los requisitos para unirse al grupo");
        }
    }
    
    public void procesarUsuarioEnGrupoCompartir(UsuarioTemp usuario, GrupoCompartir grupo) {
        if (grupo.esApropiado(usuario)) {
            grupo.unirseGrupo(usuario);
            System.out.println("Usuario " + usuario.getNombre() + " se unió al grupo: " + grupo.getTitulo());
        } else {
            System.out.println("El usuario no cumple con los requisitos para unirse al grupo");
        }
    }
    
    public List<GrupoDiscusion> obtenerGruposRecomendados(UsuarioTemp usuario, ForoGeneral foro) {
        return foro.buscarGruposDiscusionRecomendados(usuario);
    }
    
    public List<GrupoCompartir> obtenerGruposCompartirRecomendados(UsuarioTemp usuario, ForoGeneral foro) {
        return foro.buscarGruposCompartirRecomendados(usuario);
    }
    
    public void procesarRespuestaEnHilo(HiloDiscusion hilo, String contenido, UsuarioTemp autor) {
        try {
            hilo.responder(contenido, autor);
            System.out.println("Respuesta agregada exitosamente");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void procesarSolucionEnGrupo(GrupoCompartir grupo, Solucion solucion) {
        try {
            grupo.compartirSolucion(solucion);
            System.out.println("Solución compartida exitosamente");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public String generarReporteComunidad(Comunidad comunidad) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE COMUNIDAD ===\n");
        reporte.append(comunidad.obtenerEstadisticas()).append("\n\n");
        
        // Estadísticas del foro
        ForoGeneral foro = comunidad.getForoGeneral();
        reporte.append("=== ESTADÍSTICAS DEL FORO ===\n");
        reporte.append(foro.toString()).append("\n\n");
        
        // Usuarios más activos
        reporte.append("=== USUARIOS MÁS ACTIVOS ===\n");
        List<UsuarioTemp> usuariosOrdenados = comunidad.getUsuariosConectados().stream()
                .sorted((u1, u2) -> u2.getReputacion().compareTo(u1.getReputacion()))
                .limit(5)
                .collect(Collectors.toList());
        
        for (UsuarioTemp usuario : usuariosOrdenados) {
            reporte.append(usuario.toString()).append("\n");
        }
        
        return reporte.toString();
    }
}
