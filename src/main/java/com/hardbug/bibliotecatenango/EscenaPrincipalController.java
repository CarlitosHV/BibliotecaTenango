package com.hardbug.bibliotecatenango;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/*Clase principal que maneja el inicio de la aplicación y muestra el Stage*/

public class EscenaPrincipalController extends Application {

    //Método que inicia la aplicación
    public static void main(String[] args){
        launch(args);
    }

    //Método que comienza y manda a crear el stage
    @Override
    public void start(Stage stage) throws IOException {
        new SceneController(stage);
    }
}
