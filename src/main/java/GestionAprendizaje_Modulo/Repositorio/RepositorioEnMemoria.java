/**package GestionAprendizaje_Modulo.Repositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GestionAprendizaje_Modulo.Modelo.ModuloEducativo;

public class RepositorioEnMemoria implements RepositorioModulosEducativos {

    private final Map<String, ModuloEducativo> store = new HashMap<>();

    @Override
    public void guardarModuloEducativo(ModuloEducativo modulo) {
        store.put(modulo.getId(), modulo);
    }

    @Override
    public void actualizarModuloEducativo(ModuloEducativo modulo) {
        store.put(modulo.getId(), modulo);
    }

    @Override
    public ModuloEducativo buscarPorId(String id) {
        return store.get(id);
    }

    @Override
    public List<ModuloEducativo> buscarTodos() {
        return List.of();
    }
}**/

package GestionAprendizaje_Modulo.Repositorio;

import GestionAprendizaje_Modulo.Modelo.*;
import GestionAprendizaje_Modulo.Ruta.NodoRuta;
import GestionAprendizaje_Modulo.Ruta.Ruta;

import java.util.*;

// Usamos el patrón Singleton para asegurar UNA SOLA fuente de datos en toda la GUI.
public class RepositorioEnMemoria {

    // ...
// Llama al método estático para obtener la única instancia existente.
    private static final RepositorioEnMemoria repositorio = RepositorioEnMemoria.getInstancia();
// ...

    // --- COLECCIONES QUE SIMULAN LA BASE DE DATOS ---
    private final Map<String, Curso> cursos = new HashMap<>();
    private final List<Ruta> rutasIndependientes = new ArrayList<>();
    private final List<ModuloEducativo> modulosIndependientes = new ArrayList<>(); // <-- REPOSITORIO PARA MÓDULOS AÑADIDO

    // Constructor privado para evitar que se creen más instancias.
    public RepositorioEnMemoria() {
        precargarDatos(); // Llenamos con datos de ejemplo al iniciar
    }

    public static RepositorioEnMemoria getInstancia() {
        return repositorio;
    }

    // --- MÉTODOS DE ACCESO A LOS DATOS (GETTERS) ---

    public Map<String, Curso> getCursos() {
        return cursos;
    }

    /**
     * Devuelve las rutas que aún no han sido asociadas a ningún curso.
     * Útil para que el admin pueda elegirlas.
     */
    public List<Ruta> getRutas() {
        return rutasIndependientes;
    }

    /**
     * Devuelve los módulos que aún no han sido asociados a ningún curso.
     * Útil para que el admin pueda elegirlos.
     */
    public List<ModuloEducativo> getModulos() {
        return modulosIndependientes;
    }


    /**
     * Esta función crea un escenario de ejemplo completo para que la aplicación
     * no comience vacía. Simula el trabajo que un administrador ya habría hecho.
     */
    private void precargarDatos() {
        System.out.println(">>> Repositorio: Precargando datos de ejemplo...");

        // --- 1. CREAR ENTIDADES INDEPENDIENTES (lo que harían otros equipos) ---

        // Lecciones
        Leccion leccionVars = new Leccion(UUID.randomUUID().toString(), "Variables y Tipos de Datos", "Aprende los fundamentos de las variables en Java.", "PUBLICADO");
        Leccion leccionOps = new Leccion(UUID.randomUUID().toString(), "Operadores Aritméticos", "Cómo realizar sumas, restas y más.", "PUBLICADO");
        Leccion leccionPoo = new Leccion(UUID.randomUUID().toString(), "Introducción a la POO", "Conceptos de Clases y Objetos.", "PUBLICADO");

        // Módulos Educativos
        ModuloEducativo moduloBasico = new ModuloEducativo(UUID.randomUUID().toString(), "Java Básico", "1.0", "Conceptos iniciales de Java", "activo");
        moduloBasico.getLecciones().add(leccionVars);
        moduloBasico.getLecciones().add(leccionOps);

        ModuloEducativo moduloAvanzado = new ModuloEducativo(UUID.randomUUID().toString(), "Java Intermedio", "1.0", "Programación Orientada a Objetos","activo");
        moduloAvanzado.getLecciones().add(leccionPoo);

        // Guardamos los módulos en su repositorio independiente
        modulosIndependientes.add(moduloBasico);
        modulosIndependientes.add(moduloAvanzado);

        // Rutas de Aprendizaje
        Ruta rutaPrincipiante = new Ruta(UUID.randomUUID().toString(), "Ruta del Novato en Java", "Camino guiado para empezar a programar.");
        NodoRuta nodo1 = new NodoRuta(1, leccionVars);
        NodoRuta nodo2 = new NodoRuta(2, leccionOps);
        rutaPrincipiante.agregarNodo(nodo1);
        rutaPrincipiante.agregarNodo(nodo2);

        Ruta rutaPoo = new Ruta(UUID.randomUUID().toString(), "Ruta de la Orientación a Objetos", "Domina los pilares de la POO.");
        rutaPoo.agregarNodo(new NodoRuta(1, leccionPoo));

        // Guardamos las rutas en su repositorio independiente
        rutasIndependientes.add(rutaPrincipiante);
        rutasIndependientes.add(rutaPoo);


        // --- 2. CREAR UN CURSO (lo que harías en tu módulo) ---

        Curso cursoJavaCompleto = new Curso("Curso Completo de Java", "De cero a profesional en Java.");


        // --- 3. CONECTAR TODO (el trabajo de asociación que hace el admin) ---

        // Asociar los módulos al curso
        cursoJavaCompleto.getModulos().add(moduloBasico);
        cursoJavaCompleto.getModulos().add(moduloAvanzado);

        // Asociar la ruta de principiantes al curso
        cursoJavaCompleto.getRutas().add(rutaPrincipiante);

        // Guardamos el curso ya ensamblado en nuestro repositorio de cursos
        cursos.put(cursoJavaCompleto.getId(), cursoJavaCompleto);


        // --- 4. ENRIQUECER EL CONTENIDO (tu principal responsabilidad) ---

        // Añadir material de apoyo al primer nodo de la ruta de principiantes
        RecursoAprendizaje videoIntro = new Video("Video: ¿Qué es una variable?", "youtube.com/watch?v=123", 450);
        RecursoAprendizaje pdfResumen = new DocumentoPDF("PDF: Tabla de Tipos de Datos", "misitio.com/tabla_tipos.pdf", 1);

        // El nodo1 ya está dentro de la ruta, que está dentro del curso.
        // ¡La conexión funciona en cadena!
        nodo1.agregarMaterialDeApoyo(videoIntro);
        nodo1.agregarMaterialDeApoyo(pdfResumen);

        // Añadir material al segundo nodo
        RecursoAprendizaje articuloOps = new Articulo("Artículo: Precedencia de Operadores", "dev.to/operadores-java");
        nodo2.agregarMaterialDeApoyo(articuloOps);

        System.out.println(">>> ¡Datos de ejemplo cargados con éxito! <<<");
        System.out.println("-> 1 Curso, 2 Módulos, 2 Rutas creadas y conectadas.");
    }
}