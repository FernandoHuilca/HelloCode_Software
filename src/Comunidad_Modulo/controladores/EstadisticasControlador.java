package controladores;

import servicios.ComunidadService;
import utilidades.InputHelper;

/**
 * Controlador para la visualización de estadísticas del sistema.
 * Maneja todas las operaciones relacionadas con reportes y métricas.
 */
public class EstadisticasControlador implements IControlador {
    private final ComunidadService comunidadService;
    private final ContextoSistema contexto;
    
    public EstadisticasControlador(ComunidadService comunidadService, ContextoSistema contexto) {
        this.comunidadService = comunidadService;
        this.contexto = contexto;
    }
    
    @Override
    public void ejecutar() {
        if (!contexto.tieneComunidadActiva()) {
            System.out.println("No hay comunidad activa.");
            return;
        }
        
        System.out.println("\n=== ESTADÍSTICAS COMPLETAS ===");
        String reporte = comunidadService.generarReporteComunidad(contexto.getComunidadActual());
        System.out.println(reporte);
        
        InputHelper.presionarEnterParaContinuar();
    }
    
    @Override
    public String getNombreModulo() {
        return "Ver Estadísticas";
    }
}
