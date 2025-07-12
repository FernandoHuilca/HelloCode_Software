package EjerciciosIteractivos_Modulo;

import java.util.ArrayList;

public class EjercicioSeleccion extends EjercicioBase {
    private ArrayList<String> opcionesDeSeleccion;

    public EjercicioSeleccion(String instruccion, ArrayList<String> opcionesDeSeleccion, ArrayList<String> respuestasCorrectas,
                              NivelDificultad nivelDeDificultad, Lenguaje lenguaje) {
        super(instruccion, respuestasCorrectas, nivelDeDificultad, lenguaje);
        this.opcionesDeSeleccion = opcionesDeSeleccion;
    }
    
    public ArrayList<String> getListOpciones() {
        return opcionesDeSeleccion;
    }

    public String getOpcion(int numOpcion) {
        return opcionesDeSeleccion.get(numOpcion);
    }

   
    public ArrayList<String> obtenerRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    @Override
    public boolean evaluarRespuestas(ArrayList<Respuesta> respuestasUsuario) {
        //Verificar que el usuario haya proporcionado al menos una respuesta
        if (respuestasUsuario == null || respuestasUsuario.isEmpty()) {
            return false;
        }
        
        //Verificar que el número de respuestas del usuario coincida con el número de respuestas correctas
        if (respuestasUsuario.size() != respuestasCorrectas.size()) {
            return false;
        }
        
        //Convertir las respuestas del usuario a strings para comparación
        ArrayList<String> respuestasUsuarioStrings = new ArrayList<>();
        for (Respuesta respuesta : respuestasUsuario) {
            respuestasUsuarioStrings.add(respuesta.getRespuesta().toString());
        }
        
        //Verificar que todas las respuestas del usuario estén en las respuestas correctas
        //y que todas las respuestas correctas estén en las del usuario
        return respuestasUsuarioStrings.containsAll(respuestasCorrectas) && 
               respuestasCorrectas.containsAll(respuestasUsuarioStrings);
    }
}
