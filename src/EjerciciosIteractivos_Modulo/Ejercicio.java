package EjerciciosIteractivos_Modulo;

public abstract class Ejercicio {
    protected String enunciado;

    public Ejercicio(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getEnunciado() {
        return enunciado;
    }
}
