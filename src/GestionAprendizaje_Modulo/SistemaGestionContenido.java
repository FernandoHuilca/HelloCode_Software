package GestionAprendizaje_Modulo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import GestionAprendizaje_Modulo.Gestor.GestorDeModuloEducativo;
import GestionAprendizaje_Modulo.Modelo.Articulo;
import GestionAprendizaje_Modulo.Modelo.Curso;
import GestionAprendizaje_Modulo.Modelo.Leccion;
import GestionAprendizaje_Modulo.Modelo.ModuloEducativo;
import GestionAprendizaje_Modulo.Modelo.RecursoAprendizaje;
import GestionAprendizaje_Modulo.Modelo.Video;
import GestionAprendizaje_Modulo.Repositorio.RepositorioEnMemoria;
import GestionAprendizaje_Modulo.Repositorio.RepositorioModulosEducativos;
import GestionAprendizaje_Modulo.Ruta.NodoRuta;
import GestionAprendizaje_Modulo.Ruta.Ruta;

public class SistemaGestionContenido {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Curso> cursos = new HashMap<>();
    private static final RepositorioModulosEducativos repositorio = new RepositorioEnMemoria();
    private static final GestorDeModuloEducativo gestor = new GestorDeModuloEducativo(repositorio);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Bienvenido al Sistema de Gestión de Contenido");
            System.out.println("Seleccione su rol:");
            System.out.println("1. Administrador de contenido");
            System.out.println("2. Estudiante");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    menuAdministrador();
                    break;
                case "2":
                    menuEstudiante();
                    break;
                case "3":
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    // Menú para Administrador
    private static void menuAdministrador() {
        while (true) {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Crear curso");
            System.out.println("2. Crear módulo educativo");
            System.out.println("3. Crear ruta de aprendizaje");
            System.out.println("4. Crear recurso de aprendizaje");
            System.out.println("5. Asociar módulo o ruta a curso");
            System.out.println("6. Volver al menú principal");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    crearCurso();
                    break;
                case "2":
                    crearModulo();
                    break;
                case "3":
                    crearRuta();
                    break;
                case "4":
                    crearRecurso();
                    break;
                case "5":
                    asociarContenidoACurso();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    // Menú para Estudiante
    private static void menuEstudiante() {
        while (true) {
            System.out.println("\n--- MENÚ ESTUDIANTE ---");
            System.out.println("1. Ver cursos disponibles");
            System.out.println("2. Seguir ruta de aprendizaje");
            System.out.println("3. Volver al menú principal");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    verCursos();
                    break;
                case "2":
                    seguirRuta();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void crearCurso() {
        System.out.print("Nombre del curso: ");
        String nombre = scanner.nextLine();
        if (cursos.containsKey(nombre)) {
            System.out.println("Ya existe un curso con ese nombre.");
            return;
        }
        System.out.print("Descripción del curso: ");
        String descripcion = scanner.nextLine();
        Curso curso = new Curso(nombre, descripcion);
        cursos.put(curso.getId(), curso);
        System.out.println("Curso creado con éxito: " + curso.getId());
    }

    private static void crearModulo() {
        System.out.print("ID del módulo: ");
        String id = scanner.nextLine();
        System.out.print("Título del módulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Versión del módulo: ");
        String version = scanner.nextLine();
        System.out.print("Metadatos curriculares: ");
        String metadatos = scanner.nextLine();

        ModuloEducativo modulo = gestor.crearModuloEducativo(Map.of(
                "id", id,
                "titulo", titulo,
                "version", version,
                "metadatosCurriculares", metadatos
        ));
        System.out.println("Módulo creado: " + modulo.getTitulo());
    }

    private static void crearRuta() {
        System.out.print("ID de la ruta: ");
        String id = scanner.nextLine();
        System.out.print("Nombre de la ruta: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción de la ruta: ");
        String descripcion = scanner.nextLine();

        Ruta ruta = new Ruta(id, nombre, descripcion);

        int orden = 1;
        while (true) {
            System.out.print("Agregar lección a la ruta (dejar en blanco para terminar): ");
            String titulo = scanner.nextLine();
            if (titulo.isBlank()) break;
            System.out.print("Descripción de la lección: ");
            String desc = scanner.nextLine();
            Leccion leccion = new Leccion(UUID.randomUUID().toString(), titulo, desc, "BORRADOR");
            NodoRuta nodo = new NodoRuta(orden++, leccion);
            ruta.agregarNodo(nodo);
        }

        rutas.add(ruta);
        System.out.println("Ruta creada exitosamente.");
    }

    // Crear recurso de aprendizaje (Video, PDF, Artículo)
    private static void crearRecurso() {
        System.out.println("Seleccione tipo de recurso:");
        System.out.println("1. Video");
        System.out.println("2. PDF");
        System.out.println("3. Artículo");
        System.out.print("Opción: ");
        String tipo = scanner.nextLine();

        System.out.print("Título del recurso: ");
        String titulo = scanner.nextLine();

        RecursoAprendizaje recurso = null;

        switch (tipo) {
            case "1":
                System.out.print("URL del video: ");
                String url = scanner.nextLine();
                System.out.print("Duración (segundos): ");
                int duracion = Integer.parseInt(scanner.nextLine());
                recurso = new Video(titulo, url, duracion);
                break;
            case "3":
                System.out.print("Resumen del artículo: ");
                String resumen = scanner.nextLine();
                recurso = new Articulo(titulo, resumen);
                break;
            default:
                System.out.println("Tipo inválido.");
                return;
        }

        System.out.println("Recurso creado exitosamente: " + recurso);
    }

    private static void asociarContenidoACurso() {
        // Implementar asociación de módulos y rutas a un curso existente
    }

    private static void verCursos() {
        // Mostrar los cursos disponibles al estudiante
    }

    private static void seguirRuta() {
        // Permitir al estudiante recorrer una ruta y marcar progreso
    }

    // Colección de rutas para asociar a cursos o seguirlas
    private static final List<Ruta> rutas = new ArrayList<>();
}
