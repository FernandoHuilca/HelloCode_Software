package GestionContenido_Modulo;

public class DocumentoPDF extends RecursoAprendizaje {
    private final String nombreArchivo;
    private final int numeroPaginas;

    public DocumentoPDF(String titulo, String nombreArchivo, int numeroPaginas) {
        super(titulo);
        this.nombreArchivo = nombreArchivo;
        this.numeroPaginas = numeroPaginas;
    }

    public DocumentoPDF(String titulo, String nombreArchivo) {
        this(titulo, nombreArchivo, 0); // constructor con número de páginas opcional
    }

    @Override
    public String obtenerDetalle() {
        return String.format("Archivo: %s (%d páginas)", nombreArchivo, numeroPaginas);
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }
}
