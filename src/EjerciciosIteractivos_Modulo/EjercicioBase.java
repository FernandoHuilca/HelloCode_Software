package EjerciciosIteractivos_Modulo;

import java.util.ArrayList;

public abstract class EjercicioBase implements EvaluadorRespuestas {
    protected String instruccion;
    protected ArrayList<String> respuestasCorrectas;
    protected NivelDificultad nivelDificultad;
    protected Lenguaje lenguaje;

    public EjercicioBase(String instruccion, ArrayList<String> respuestasCorrectas, NivelDificultad nivelDificultad, Lenguaje lenguaje) {
        this.instruccion = instruccion;
        this.respuestasCorrectas = respuestasCorrectas;
        this.nivelDificultad = nivelDificultad;
        this.lenguaje = lenguaje;
    }

    public String getInstruccion() {
        return instruccion;
    }
}
