import controladores.*;
import servicios.ComunidadService;
import utilidades.InputHelper;
import utilidades.MenuHelper;

/**
 * Clase principal del Sistema de Comunidad Java.
 * Esta clase actúa como punto de entrada y coordinador de módulos.
 * 
 * En un sistema multi-módulo, esta clase sería reemplazada por un main
 * general que coordine todos los módulos del sistema.
 */
public class AppNew {
    private static final ContextoSistema contexto = ContextoSistema.getInstancia();
    private static final ComunidadService comunidadService = new ComunidadService();
    
    // Controladores de cada módulo
    private static final UsuarioControlador usuarioControlador = 
            new UsuarioControlador(comunidadService, contexto);
    private static final ComunidadControlador comunidadControlador = 
            new ComunidadControlador(comunidadService, contexto);
    private static final ForoControlador foroControlador = 
            new ForoControlador(comunidadService, contexto);
    private static final ChatControlador chatControlador = 
            new ChatControlador(comunidadService, contexto);
    private static final EstadisticasControlador estadisticasControlador = 
            new EstadisticasControlador(comunidadService, contexto);
    
    public static void main(String[] args) {
        mostrarBienvenida();
        inicializarSistema();
        ejecutarMenuPrincipal();
        mostrarDespedida();
    }
    
    private static void mostrarBienvenida() {
        System.out.println("¡Bienvenido al Sistema de Comunidad Java!");
        System.out.println("Una plataforma para aprender y compartir conocimiento de programación.");
        System.out.println("Módulo: Comunidad - Versión 1.0");
        System.out.println("================================================");
    }
    
    private static void inicializarSistema() {
        // Inicializar con datos de ejemplo
        DatosEjemploFactory.inicializar(contexto);
        
        // Opcional: crear datos extendidos para testing
        if (contexto.tieneComunidadActiva()) {
            DatosEjemploFactory.crearDatosExtendidos(contexto);
        }
    }
    
    private static void ejecutarMenuPrincipal() {
        boolean continuar = true;
        while (continuar) {
            MenuHelper.mostrarMenuPrincipal();
            int opcion = InputHelper.leerEntero("Seleccione una opción");
            
            continuar = procesarOpcionPrincipal(opcion);
        }
    }
    
    private static boolean procesarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1:
                usuarioControlador.ejecutar();
                break;
            case 2:
                comunidadControlador.ejecutar();
                break;
            case 3:
                foroControlador.ejecutar();
                break;
            case 4:
                chatControlador.ejecutar();
                break;
            case 5:
                estadisticasControlador.ejecutar();
                break;
            case 6:
                return false; // Salir del sistema
            default:
                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
        }
        return true;
    }
    
    private static void mostrarDespedida() {
        System.out.println("¡Gracias por usar el Sistema de Comunidad Java!");
        System.out.println("¡Hasta la próxima!");
    }
    
    /**
     * Método para obtener el contexto del sistema.
     * Útil para testing o integración con otros módulos.
     * @return El contexto del sistema
     */
    public static ContextoSistema getContexto() {
        return contexto;
    }
    
    /**
     * Método para obtener el servicio de comunidad.
     * Útil para testing o integración con otros módulos.
     * @return El servicio de comunidad
     */
    public static ComunidadService getComunidadService() {
        return comunidadService;
    }
}
