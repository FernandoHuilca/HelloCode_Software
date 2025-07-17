package model;

public class DetalleLeccion {
    private Ejercicio ejercicio;
    private ResultadoEvaluacion resultado;

    public DetalleLeccion(Ejercicio ejercicio, ResultadoEvaluacion resultado) {
        this.ejercicio = ejercicio;
        this.resultado = resultado;
    }

    public Ejercicio getEjercicio() { return ejercicio; }
    public ResultadoEvaluacion getResultado() { return resultado; }

    // Modificado para aceptar tiempoEmpleado
    public void resolverEjercicio(String respuestaUsuario, long tiempoEmpleado) {
        ResultadoEvaluacion res = ejercicio.resolver(respuestaUsuario);
        // Creamos un nuevo ResultadoEvaluacion con el tiempo correcto
        ResultadoEvaluacion resultadoConTiempo = new ResultadoEvaluacion(
            res.isCorrecto(),
            res.getPuntuacionObtenida(),
            tiempoEmpleado,
            res.getObtenerMensaje(),
            false // o el valor que corresponda para hayEjercicioSiguiente
        );
        this.resultado = resultadoConTiempo;
    }
}