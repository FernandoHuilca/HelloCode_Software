package GestionAprendizaje_Modulo.Repositorio;

import java.util.HashMap;
import java.util.Map;

import GestionAprendizaje_Modulo.Modelo.ModuloEducativo;

public class RepositorioModulosEnMemoria implements RepositorioModulosEducativos {

    private Map<String, ModuloEducativo> almacenamiento = new HashMap<>();

    @Override
    public void guardarModuloEducativo(ModuloEducativo modulo) {
        almacenamiento.put(modulo.getId(), modulo);
        System.out.println("Módulo guardado con ID: " + modulo.getId());
    }

    @Override
    public void actualizarModuloEducativo(ModuloEducativo modulo) {
        if (almacenamiento.containsKey(modulo.getId())) {
            almacenamiento.put(modulo.getId(), modulo);
            System.out.println("Módulo actualizado: " + modulo.getId());
        } else {
            System.out.println("Módulo no encontrado para actualizar.");
        }
    }

    @Override
    public ModuloEducativo buscarPorId(String id) {
        return almacenamiento.get(id);
    }
}