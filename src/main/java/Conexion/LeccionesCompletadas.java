package Conexion;

import Gamificacion_Modulo.clases.Desafio;
import Gamificacion_Modulo.clases.ProgresoEstudiante;
import Gamificacion_Modulo.clases.Ranking;
import Modulo_Usuario.Clases.Usuario;

public class LeccionesCompletadas {

    private static Ranking ran = Ranking.getInstance();
    private static Usuario usr = SesionManager.getInstancia().getUsuarioAutenticado();
    private static ProgresoEstudiante progresoActual = encontrarProgresoActual();

    public static void set(int cantidad){
        // Lazy init en caso de que aún no exista usuario/progreso al primer clic
        if (progresoActual == null) {
            usr = SesionManager.getInstancia().getUsuarioAutenticado();
            progresoActual = encontrarProgresoActual();
            if (progresoActual == null) {
                System.err.println("[LeccionesCompletadas] Progreso no disponible todavía. Se omite actualización de desafíos.");
                return; // evitar NPE y permitir que el siguiente intento funcione
            }
        }
        if(!progresoActual.getDesafiosActivos().isEmpty()){
            for(Desafio desafio: progresoActual.getDesafiosActivos()){
                desafio.actualizarAvance(1);
                if(desafio.estaCompletado())
                    desafio.completarDesafio(progresoActual);
            }
        }
    }

    private static boolean compare(Usuario u1, Usuario u2){
        return u1.getUsername().equals(u2.getUsername());
    }

    public static void aumentarXP(int cantidad){
        if (progresoActual == null) {
            usr = SesionManager.getInstancia().getUsuarioAutenticado();
            progresoActual = encontrarProgresoActual();
        }
        if (progresoActual != null) {
            progresoActual.sumarPuntos(cantidad);
        } else {
            System.err.println("[LeccionesCompletadas] No se pudo aumentar XP: progreso no disponible.");
        }
    }
    private static ProgresoEstudiante encontrarProgresoActual(){
        if (ran == null) ran = Ranking.getInstance();
        if (usr == null) return null;
        try {
            for(ProgresoEstudiante progreso: ran.obtenerRankingGeneral()){
                if (progreso == null || progreso.getUsuario() == null) continue;
                progreso.verificarDesafios();
                if(compare(progreso.getUsuario(), usr))
                    return progreso;
            }
        } catch (Exception ignored) { }
        return null;
    }
}
