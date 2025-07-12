package GestionAprendizaje_ModuloIsaac;

import GestionAprendizaje_ModuloIsaac.Modelo.Leccion;
import GestionAprendizaje_ModuloIsaac.Modelo.ModuloEducativo;
import GestionAprendizaje_ModuloIsaac.Repositorio.RepositorioModulosEducativos;
import GestionAprendizaje_ModuloIsaac.Repositorio.RepositorioModulosEnMemoria;
import GestionAprendizaje_ModuloIsaac.Ruta.NodoRuta;
import GestionAprendizaje_ModuloIsaac.Ruta.Ruta;


public class Main {
    public static void main(String[] args) {
        // Crear el repositorio
        RepositorioModulosEducativos repo = new RepositorioModulosEnMemoria();

        // Crear un módulo educativo
        ModuloEducativo modulo1 = new ModuloEducativo("Introducción a Java", "v1.0", "borrador");

        // Guardarlo
        repo.guardarModuloEducativo(modulo1);

        // Consultarlo
        ModuloEducativo encontrado = repo.buscarPorId(modulo1.getId());
        System.out.println("Módulo encontrado: " + encontrado);

        // Actualizar el título
        modulo1.setTitulo("Introducción a Java - Actualizado");
        repo.actualizarModuloEducativo(modulo1);

        // Verificar que se haya actualizado
        ModuloEducativo actualizado = repo.buscarPorId(modulo1.getId());
        System.out.println("Módulo actualizado: " + actualizado);



        // RUTA

         // Crear lecciones de prueba
        Leccion l1 = new Leccion("L1", "Variables", "Conceptos básicos de variables", "activa");
        Leccion l2 = new Leccion("L2", "Condicionales", "Uso de if, else", "activa");
        Leccion l3 = new Leccion("L3", "Bucles", "Uso de while y for", "activa");

        // Crear nodos de ruta
        NodoRuta n1 = new NodoRuta(1, l1);
        NodoRuta n2 = new NodoRuta(2, l2);
        NodoRuta n3 = new NodoRuta(3, l3);

        // Crear ruta
        Ruta ruta = new Ruta("R1", "Ruta básica de programación", "Empezando desde cero");

        ruta.agregarNodo(n1);
        ruta.agregarNodo(n2);
        ruta.agregarNodo(n3);

        // Ver progreso inicial
        System.out.println(ruta); // progreso: 0%

        // Marcar una como completada
        n1.marcarComoCompletado();
        System.out.println(ruta); // progreso: 33.33%

        // Marcar otra
        n2.marcarComoCompletado();
        System.out.println(ruta); // progreso: 66.66%
    }
}