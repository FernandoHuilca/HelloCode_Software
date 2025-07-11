package EjerciciosIteractivos_Modulo;

import java.util.ArrayList;
import java.util.Collection;

public class EjercicioCompletar extends Ejercicio {
    private final ArrayList<String> partesACompletar;
    private final ArrayList<String> respuestas;

    public EjercicioCompletar(String enunciado, ArrayList<String> partesACompletar, ArrayList<String> respuestas) {
        super(enunciado);

        this.partesACompletar = partesACompletar;
        this.respuestas = respuestas;
    }

    public ArrayList<String> getPartesACompletar() {
        return this.partesACompletar;
    }

    public boolean validarRespuestas(ArrayList<String> respuestas) {
        for(int i =0; i < respuestas.size(); i++){
            if(!respuestas.get(i).equals(this.respuestas.get(i))) return false;
        }
        return true;
    }
}
