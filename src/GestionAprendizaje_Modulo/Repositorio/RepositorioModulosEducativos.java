package GestionAprendizaje_Modulo.Repositorio;

import GestionAprendizaje_Modulo.Modelo.ModuloEducativo;

public interface RepositorioModulosEducativos {
    void guardarModuloEducativo(ModuloEducativo modulo);
    void actualizarModuloEducativo(ModuloEducativo modulo);
    ModuloEducativo buscarPorId(String id);
}
