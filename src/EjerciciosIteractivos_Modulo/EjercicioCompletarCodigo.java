package EjerciciosIteractivos_Modulo;

import java.util.ArrayList;

/**
 * Clase que representa un ejercicio de completar código
 * El usuario debe completar las partes faltantes del código
 */
public class EjercicioCompletarCodigo extends EjercicioBase {
    private String codigoIncompleto;
    private ArrayList<String> partesFaltantes;
    private ArrayList<String> respuestasEsperadas;

    /**
     * Constructor para ejercicios de completar código
     * @param instruccion La instrucción del ejercicio
     * @param codigoIncompleto El código con espacios en blanco para completar
     * @param partesFaltantes Las partes que el usuario debe completar
     * @param respuestasEsperadas Las respuestas correctas para cada parte
     * @param nivelDificultad El nivel de dificultad del ejercicio
     * @param lenguaje El lenguaje de programación
     */
    public EjercicioCompletarCodigo(String instruccion, String codigoIncompleto, 
                                   ArrayList<String> partesFaltantes, ArrayList<String> respuestasEsperadas,
                                   NivelDificultad nivelDificultad, Lenguaje lenguaje) {
        super(instruccion, respuestasEsperadas, nivelDificultad, lenguaje);
        this.codigoIncompleto = codigoIncompleto;
        this.partesFaltantes = partesFaltantes;
        this.respuestasEsperadas = respuestasEsperadas;
    }

    /**
     * Obtiene el código incompleto del ejercicio
     * @return El código con espacios en blanco
     */
    public String obtenerCodigoIncompleto() {
        return codigoIncompleto;
    }

    /**
     * Obtiene las partes que faltan por completar
     * @return Lista de partes faltantes
     */
    public ArrayList<String> obtenerPartesFaltantes() {
        return partesFaltantes;
    }

    /**
     * Obtiene las respuestas esperadas para cada parte
     * @return Lista de respuestas correctas
     */
    public ArrayList<String> obtenerRespuestasEsperadas() {
        return respuestasEsperadas;
    }

    /**
     * Obtiene el número de partes que faltan por completar
     * @return Número de partes faltantes
     */
    public int obtenerNumeroPartesFaltantes() {
        return partesFaltantes.size();
    }

    @Override
    public boolean evaluarRespuestas(ArrayList<Respuesta> respuestasUsuario) {
        //Verificar que el usuario haya proporcionado al menos una respuesta
        if (respuestasUsuario == null || respuestasUsuario.isEmpty()) {
            return false;
        }
        
        //Verificar que el número de respuestas del usuario coincida con el número de partes faltantes
        if (respuestasUsuario.size() != partesFaltantes.size()) {
            return false;
        }
        
        //Convertir las respuestas del usuario a strings para comparación
        ArrayList<String> respuestasUsuarioStrings = new ArrayList<>();
        for (Respuesta respuesta : respuestasUsuario) {
            respuestasUsuarioStrings.add(respuesta.getRespuesta().toString().trim());
        }
        
        //Comparar cada respuesta del usuario con la respuesta esperada correspondiente
        for (int i = 0; i < respuestasEsperadas.size(); i++) {
            String respuestaEsperada = respuestasEsperadas.get(i).trim();
            String respuestaUsuario = respuestasUsuarioStrings.get(i);
            
            //Comparación case-insensitive para ser más flexible
            if (!respuestaEsperada.equalsIgnoreCase(respuestaUsuario)) {
                return false;
            }
        }
        
        return true;
    }
} 