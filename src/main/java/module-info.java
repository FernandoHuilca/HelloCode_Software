module HelloCode.Software {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens GestionAprendizaje_Modulo.Controladores to javafx.fxml;
    exports GestionAprendizaje_Modulo.Aplicacion;
}
