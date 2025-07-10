package EjerciciosIteractivos_Modulo;

import java.util.Scanner;

//Fernando Huilca
public class Main {
    public static void main(String[] args) {
    //Variables para el main______________________________________
        Scanner sc = new Scanner(System.in); //Input
        int opcionMenuGeneral = 1;
        Leccion leccionTipoDatos = new Leccion();
        Leccion leccionSintaxis = new Leccion();

    //____________________________________________________________


        System.out.println("_______________________ Bienvenido a HelloCode _______________________");
        while (opcionMenuGeneral != 0) {
            System.out.println("_________________ Ejemplo Contenido sobre tipo de datos _________________");
            System.out.println("Seleccione la lección que desea realizar: ");
            System.out.println("1. Leccion " + leccionTipoDatos.getNombre() );
            System.out.println("1. Leccion " + leccionTipoDatos.getNombre() );
            opcionMenuGeneral = sc.nextInt();
        }













    }
}