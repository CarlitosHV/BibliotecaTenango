package com.hardbug.bibliotecatenango;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class EscenaPrincipalController extends Application {

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException {
        new SceneController(stage);
    }
}
