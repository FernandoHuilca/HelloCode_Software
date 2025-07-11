package EjerciciosIteractivos_Modulo;

import java.util.ArrayList;

public abstract class Ejercicio {
    protected String enunciado;
    protected ArrayList<String> respuestas;
    protected ValidacionRespuestas validador;

    public Ejercicio(String enunciado, ArrayList<String> respuestas) {
        this.enunciado = enunciado;
        this.respuestas = respuestas;
        validador = new ValidacionRespuestas();
    }

    public String getEnunciado() {
        return enunciado;
    }
}
