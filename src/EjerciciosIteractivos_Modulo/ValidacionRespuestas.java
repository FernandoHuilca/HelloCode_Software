package EjerciciosIteractivos_Modulo;

import java.util.ArrayList;

public class ValidacionRespuestas {
    ValidacionRespuestas(){

    }

    public boolean validarRespuestas(Ejercicio ejercicio, ArrayList<String> respuestas) {
        for(int i =0; i < respuestas.size(); i++){
            if(!respuestas.get(i).equals(ejercicio.respuestas.get(i))) return false;
        }
        return true;
    }
}
