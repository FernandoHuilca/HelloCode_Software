package Conexion;

import Gamificacion_Modulo.clases.Desafio;
import Gamificacion_Modulo.clases.ProgresoEstudiante;
import Gamificacion_Modulo.clases.Ranking;
import Modulo_Usuario.Clases.Usuario;

public class LeccionesCompletadas {
    public static void set(int cantidad){
        Ranking ran = Ranking.getInstance();
        Usuario usr = SesionManager.getInstancia().getUsuarioAutenticado();

        for(ProgresoEstudiante progreso: ran.obtenerRankingGeneral()){
           progreso.verificarDesafios();
           if(compare(progreso.getUsuario(), usr) && !progreso.getDesafiosActivos().isEmpty()){
               for(Desafio desafio: progreso.getDesafiosActivos()){
                   desafio.actualizarAvance(1);
                   if(desafio.estaCompletado())
                       desafio.completarDesafio(progreso);
               }
               System.out.println("puntos: " + progreso.getPuntosTotal());
               System.out.println("xp: " + usr.getXp());
               break;
           }
        }
    }

    private static boolean compare(Usuario u1, Usuario u2){
        return u1.getUsername().equals(u2.getUsername());
    }
}
