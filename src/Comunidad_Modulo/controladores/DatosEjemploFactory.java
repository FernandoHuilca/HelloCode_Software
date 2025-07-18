package controladores;

import modelo.*;
import enums.*;

/**
 * Factory para inicializar datos de ejemplo en el sistema.
 * Separa la lógica de inicialización de la clase principal.
 */
public class DatosEjemploFactory {
    
    /**
     * Inicializa el sistema con datos de ejemplo para pruebas y demostración.
     * @param contexto El contexto del sistema donde se cargarán los datos
     */
    public static void inicializar(ContextoSistema contexto) {
        // Crear usuarios ejemplo
        UsuarioTemp usuario1 = new UsuarioTemp("Juan Pérez", NivelJava.PRINCIPIANTE);
        UsuarioTemp usuario2 = new UsuarioTemp("Ana García", NivelJava.INTERMEDIO);
        UsuarioTemp usuario3 = new UsuarioTemp("Carlos López", NivelJava.AVANZADO);
        
        // Agregar usuarios al contexto
        contexto.agregarUsuario(usuario1);
        contexto.agregarUsuario(usuario2);
        contexto.agregarUsuario(usuario3);
        
        // Crear comunidad ejemplo
        Comunidad comunidad = new Comunidad("Comunidad Java Learners", "Espacio para aprender Java");
        contexto.agregarComunidad(comunidad);
        contexto.setComunidadActual(comunidad);
        
        // Conectar usuarios a la comunidad
        comunidad.conectarUsuario(usuario1);
        comunidad.conectarUsuario(usuario2);
        comunidad.conectarUsuario(usuario3);
        
        // Crear moderador
        Moderador moderador = new Moderador("Moderador Admin");
        contexto.agregarModerador(moderador);
        comunidad.setModerador(moderador);
        
        // Crear algunos grupos ejemplo
        ForoGeneral foro = comunidad.getForoGeneral();
        GrupoDiscusion grupoBasico = foro.crearGrupoDiscusion("Problemas Básicos", NivelJava.PRINCIPIANTE, TipoTema.SINTAXIS);
        GrupoCompartir grupoSoluciones = foro.crearGrupoCompartir("Soluciones POO", NivelJava.INTERMEDIO, TipoTema.POO);
        
        // Unir usuarios a grupos
        grupoBasico.unirseGrupo(usuario1);
        grupoSoluciones.unirseGrupo(usuario2);
        
        System.out.println("Sistema inicializado con datos de ejemplo.");
    }
    
    /**
     * Crea datos adicionales para testing más exhaustivo.
     * @param contexto El contexto del sistema
     */
    public static void crearDatosExtendidos(ContextoSistema contexto) {
        if (!contexto.tieneComunidadActiva()) {
            return;
        }
        
        Comunidad comunidad = contexto.getComunidadActual();
        ForoGeneral foro = comunidad.getForoGeneral();
        
        // Crear más grupos para diferentes niveles y temas
        foro.crearGrupoDiscusion("Algoritmos Avanzados", NivelJava.AVANZADO, TipoTema.ALGORITMOS);
        foro.crearGrupoDiscusion("Estructuras de Datos", NivelJava.INTERMEDIO, TipoTema.ESTRUCTURAS_DATOS);
        foro.crearGrupoCompartir("Frameworks Modernos", NivelJava.AVANZADO, TipoTema.FRAMEWORKS);
        foro.crearGrupoCompartir("Manejo de Excepciones", NivelJava.INTERMEDIO, TipoTema.EXCEPCIONES);
        
        System.out.println("Datos extendidos creados exitosamente.");
    }
}
