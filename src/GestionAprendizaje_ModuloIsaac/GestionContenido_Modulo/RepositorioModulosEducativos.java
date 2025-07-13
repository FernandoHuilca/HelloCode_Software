package GestionAprendizaje_ModuloIsaac.GestionContenido_Modulo;

public interface RepositorioModulosEducativos {
    void guardarModuloEducativo(ModuloEducativo modulo);
    void actualizarModuloEducativo(ModuloEducativo modulo);
    ModuloEducativo buscarPorId(String id);
}
