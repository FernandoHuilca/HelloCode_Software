package GestionAprendizaje_ModuloIsaac.GestionContenido_Modulo;

import java.util.HashMap;
import java.util.Map;

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
}
