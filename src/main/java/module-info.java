module HelloCode.Software {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens GestionAprendizaje_Modulo.Controladores to javafx.fxml;

    opens GestionAprendizaje_Modulo.Aplicacion to javafx.graphics, javafx.fxml;

    exports GestionAprendizaje_Modulo.Aplicacion;
    exports GestionAprendizaje_Modulo.Ruta;
    exports GestionAprendizaje_Modulo.Modelo;
    exports GestionAprendizaje_Modulo.Repositorio;
    exports GestionAprendizaje_Modulo.Gestor;
}
