module Modulo_Ejercicio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    // Abre paquetes para FXML
    opens GestionAprendizaje_Modulo.Controladores to javafx.fxml;
    opens GestionAprendizaje_Modulo.Aplicacion to javafx.graphics, javafx.fxml;

    opens Modulo_Ejercicios.Controladores to javafx.fxml;
    opens Modulo_Usuario.Controladores to javafx.fxml;
    opens MetodosGlobales to javafx.fxml;

    opens Comunidad_Modulo.Controladores_GUI to javafx.fxml;
    opens Comunidad_Modulo.controladores to javafx.fxml;
    opens Comunidad_Modulo.App to javafx.fxml, javafx.graphics;

    opens GestorEjercicios.Controllers to javafx.fxml;

    // Exportaciones
    exports GestionAprendizaje_Modulo.Aplicacion;
    exports GestionAprendizaje_Modulo.Ruta;
    exports GestionAprendizaje_Modulo.Modelo;
    exports GestionAprendizaje_Modulo.Repositorio;
    exports GestionAprendizaje_Modulo.Gestor;

    exports Comunidad_Modulo.App;
    exports Comunidad_Modulo.integracion;
    exports Comunidad_Modulo.modelo;
    exports Comunidad_Modulo.controladores;
    exports Comunidad_Modulo.Controladores_GUI;
    exports Comunidad_Modulo.utilidades;
    exports Comunidad_Modulo.servicios;
    exports Comunidad_Modulo.enums;

    exports Modulo_Ejercicios.application;
    exports Modulo_Ejercicios.otrosModulos;
    exports Modulo_Ejercicios.exercises;
    exports Modulo_Ejercicios.DataBase;

    exports Modulo_Usuario.Clases;
    exports Modulo_Usuario.Controladores;
    exports Modulo_Usuario.application;

    exports GestorEjercicios.Controllers;
    exports GestorEjercicios.model;
    exports GestorEjercicios.main; //Siempre me daba fallo pporque falta este export xd

    exports MetodosGlobales;
}

