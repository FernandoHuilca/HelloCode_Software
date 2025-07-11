package Comunidad_Modulo.core;

public class App {

    public static void main(String[] args) {
        Comunidad comunidad = new Comunidad("Comunidad de Programadores", "Un lugar para compartir conocimientos de programación", 100);
        
        System.out.println("Nombre de la comunidad: " + comunidad.getNombre());
        System.out.println("Descripción: " + comunidad.getDescripcion());
        System.out.println("Número de miembros: " + comunidad.getNumeroMiembros());
        
        // Modificar los atributos
        comunidad.setNombre("Comunidad Avanzada de Programadores");
        comunidad.setDescripcion("Un lugar para programadores avanzados");
        comunidad.setNumeroMiembros(150);
        
        System.out.println("Nuevo nombre de la comunidad: " + comunidad.getNombre());
        System.out.println("Nueva descripción: " + comunidad.getDescripcion());
        System.out.println("Nuevo número de miembros: " + comunidad.getNumeroMiembros());
    }
    
}
