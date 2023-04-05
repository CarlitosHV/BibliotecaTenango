package com.hardbug.bibliotecatenango;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/*Clase principal que maneja el inicio de la aplicación y muestra el Stage*/

public class IndexApp extends Application {

    //Método que inicia la aplicación
    public static void main(String[] args){
        launch(args);
    }

    //Método que comienza y manda a crear el stage
    @Override
    public void start(Stage stage) throws IOException {
        var scene = new Scene(new Pane());
        scene.getStylesheets().clear();
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.CRUD_LIBROS, ViewSwitcher.TEMA_CLARO);
        stage.setScene(scene);
        stage.getIcons().add(new Image(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")));
        stage.setTitle("Biblioteca Pública Municipal Lic. Abel C. Salazar");
        stage.show();
    }
}
