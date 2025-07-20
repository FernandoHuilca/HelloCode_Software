module com.example.gui_comunidad {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens Comunidad_Modulo.Controladores_GUI to javafx.fxml;
    opens Comunidad_Modulo.controladores to javafx.fxml;
    opens Comunidad_Modulo.App to javafx.fxml, javafx.graphics;

    exports Comunidad_Modulo.App;
    exports Comunidad_Modulo.integracion;
    exports Comunidad_Modulo.modelo;
    exports Comunidad_Modulo.controladores;
    exports Comunidad_Modulo.Controladores_GUI;
    exports Comunidad_Modulo.utilidades;
    exports Comunidad_Modulo.servicios;

}