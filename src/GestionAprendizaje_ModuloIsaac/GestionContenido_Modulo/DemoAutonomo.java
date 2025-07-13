package GestionAprendizaje_ModuloIsaac.GestionContenido_Modulo;

import java.util.Map;

public class DemoAutonomo {
    public static void main(String[] args) {
        RepositorioModulosEducativos repo = new RepositorioEnMemoria();
        GestorDeModuloEducativo gestor = new GestorDeModuloEducativo(repo);

        Map<String, Object> datos = Map.of(
                "id", "M1",
                "titulo", "Módulo Java",
                "version", "1.0",
                "metadatosCurriculares", "Programación orientada a objetos"
        );

        ModuloEducativo mod = gestor.crearModuloEducativo(datos);
        mod.agregarRecurso(new Video("Introducción a Java", "https://video.com/java", 120));
        mod.agregarRecurso(new DocumentoPDF("Guía PDF", "java-basics.pdf", 15));
        mod.agregarLeccion(new Leccion("L1", "POO", "Conceptos básicos", "ACTIVA"));

        try {
            gestor.publicarModuloEducativo("M1");
            System.out.println("✅ Módulo publicado");
        } catch (IllegalStateException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        System.out.println("Estado: " + mod.getEstado());
        mod.getRecursos().forEach(System.out::println);
        mod.getLecciones().forEach(l -> System.out.println("Lección: " + l.getTitulo()));
    }
}
