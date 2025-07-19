package model;

public class DetalleLeccion {
    private model.Ejercicio ejercicio;
    private model.ResultadoEvaluacion resultado;

    public DetalleLeccion(model.Ejercicio ejercicio, model.ResultadoEvaluacion resultado) {
        this.ejercicio = ejercicio;
        this.resultado = resultado;
    }

    public model.Ejercicio getEjercicio() { return ejercicio; }
    public model.ResultadoEvaluacion getResultado() { return resultado; }

    public void resolverEjercicio(String respuestaUsuario) {
        this.resultado = ejercicio.resolver(respuestaUsuario);
    }
}