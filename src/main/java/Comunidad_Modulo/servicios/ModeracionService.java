package Comunidad_Modulo.servicios;

import Modulo_Usuario.Clases.UsuarioComunidad;
import Comunidad_Modulo.modelo.SancionUsuario;
import Comunidad_Modulo.utilidades.FiltroContenido;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la moderación automática y manual de contenido.
 */
public class ModeracionService {

    // Instancia única y estática
    private static ModeracionService instancia = new ModeracionService();

    // Mapa de usuario -> lista de sanciones
    private Map<String, List<SancionUsuario>> sancionesPorUsuario;
    
    // Lista de todas las sanciones activas
    private List<SancionUsuario> sancionesActivas;
    
    private ModeracionService() {
        this.sancionesPorUsuario = new HashMap<>();
        this.sancionesActivas = new ArrayList<>();
    }

    // Método público para obtener la instancia única
    public static ModeracionService getInstance() {
        return instancia;
    }

    /**
     * Modera un mensaje automáticamente
     */
    public ResultadoModeracion moderarMensaje(String contenido, UsuarioComunidad autor, String moderadorResponsable) {
        // Verificar si el usuario ya está sancionado
        if (usuarioEstaSancionado(autor)) {
            SancionUsuario sancionActiva = obtenerSancionActiva(autor);
            return new ResultadoModeracion(
                false, 
                "Usuario sancionado. Tiempo restante: " + sancionActiva.getMinutosRestantes() + " minutos",
                sancionActiva
            );
        }
        
        // Analizar el contenido
        FiltroContenido.ResultadoModeración resultado = FiltroContenido.analizarContenido(contenido);
        
        if (resultado.esInapropiado()) {
            // Aplicar sanción
            SancionUsuario sancion = aplicarSancion(
                autor, 
                resultado.getRazonSancion(), 
                resultado.getDuracionSancionMinutos(),
                moderadorResponsable
            );
            
            return new ResultadoModeracion(
                false,
                "Mensaje bloqueado. " + resultado.getRazonSancion() + 
                ". Sanción de " + resultado.getDuracionSancionMinutos() + " minutos aplicada.",
                sancion
            );
        }
        
        return new ResultadoModeracion(true, "Mensaje aprobado", null);
    }
    
    /**
     * Aplica una sanción a un usuario
     */
    public SancionUsuario aplicarSancion(UsuarioComunidad usuario, String razon, int duracionMinutos, String moderadorResponsable) {
        SancionUsuario sancion = new SancionUsuario(usuario, razon, duracionMinutos, moderadorResponsable);
        
        // Agregar a la lista de sanciones del usuario
        sancionesPorUsuario.computeIfAbsent(usuario.getIdUsuario(), k -> new ArrayList<>()).add(sancion);
        
        // Agregar a la lista de sanciones activas
        sancionesActivas.add(sancion);
        
        System.out.println("🚫 SANCIÓN APLICADA: " + sancion.toString());
        
        return sancion;
    }
    
    /**
     * Verifica si un usuario está actualmente sancionado
     */
    public boolean usuarioEstaSancionado(UsuarioComunidad usuario) {
        return obtenerSancionActiva(usuario) != null;
    }
    
    /**
     * Obtiene la sanción activa de un usuario (si la tiene)
     */
    public SancionUsuario obtenerSancionActiva(UsuarioComunidad usuario) {
        List<SancionUsuario> sanciones = sancionesPorUsuario.get(usuario.getIdUsuario());
        if (sanciones == null) return null;
        
        return sanciones.stream()
                .filter(SancionUsuario::estaActiva)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Levanta una sanción manualmente
     */
    public boolean levantarSancion(UsuarioComunidad usuario, String moderadorResponsable) {
        SancionUsuario sancionActiva = obtenerSancionActiva(usuario);
        if (sancionActiva != null) {
            sancionActiva.levantarSancion();
            System.out.println("✅ Sanción levantada por " + moderadorResponsable + ": " + sancionActiva.toString());
            return true;
        }
        return false;
    }
    
    /**
     * Obtiene todas las sanciones activas
     */
    public List<SancionUsuario> getSancionesActivas() {
        // Limpiar sanciones expiradas
        sancionesActivas.removeIf(sancion -> !sancion.estaActiva());
        return new ArrayList<>(sancionesActivas);
    }
    
    /**
     * Obtiene el historial de sanciones de un usuario
     */
    public List<SancionUsuario> getHistorialSanciones(UsuarioComunidad usuario) {
        return sancionesPorUsuario.getOrDefault(usuario.getIdUsuario(), new ArrayList<>());
    }
    
    /**
     * Obtiene estadísticas de moderación
     */
    public EstadisticasModeración getEstadisticas() {
        int totalSanciones = sancionesPorUsuario.values().stream()
                .mapToInt(List::size)
                .sum();
        
        int sancionesActivas = getSancionesActivas().size();
        
        Map<String, Integer> tiposSanciones = sancionesPorUsuario.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                    SancionUsuario::getRazon,
                    Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
                ));
        
        return new EstadisticasModeración(totalSanciones, sancionesActivas, tiposSanciones);
    }
    
    /**
     * Clase para encapsular el resultado de la moderación
     */
    public static class ResultadoModeracion {
        private final boolean aprobado;
        private final String mensaje;
        private final SancionUsuario sancion;
        
        public ResultadoModeracion(boolean aprobado, String mensaje, SancionUsuario sancion) {
            this.aprobado = aprobado;
            this.mensaje = mensaje;
            this.sancion = sancion;
        }
        
        public boolean isAprobado() { return aprobado; }
        public String getMensaje() { return mensaje; }
        public SancionUsuario getSancion() { return sancion; }
    }
    
    /**
     * Clase para estadísticas de moderación
     */
    public static class EstadisticasModeración {
        private final int totalSanciones;
        private final int sancionesActivas;
        private final Map<String, Integer> tiposSanciones;
        
        public EstadisticasModeración(int totalSanciones, int sancionesActivas, Map<String, Integer> tiposSanciones) {
            this.totalSanciones = totalSanciones;
            this.sancionesActivas = sancionesActivas;
            this.tiposSanciones = tiposSanciones;
        }
        
        public int getTotalSanciones() { return totalSanciones; }
        public int getSancionesActivas() { return sancionesActivas; }
        public Map<String, Integer> getTiposSanciones() { return tiposSanciones; }
        
        @Override
        public String toString() {
            return String.format("📊 Estadísticas: %d sanciones totales, %d activas", 
                               totalSanciones, sancionesActivas);
        }
    }
}
