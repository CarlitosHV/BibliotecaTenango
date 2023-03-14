package com.hardbug.bibliotecatenango;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage mainStage;
    private BorderPane borderPane;

    public SceneController(Stage stage){
        this.mainStage = stage;
        CargarEscenaPrincipal();
    }

    public void CargarEscenaPrincipal(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EscenaPrincipalView.fxml"));
            borderPane = (BorderPane) loader.load();
            Scene s = new Scene(borderPane);
            mainStage.setScene(s);
            mainStage.show();
            SceneController sc = loader.getController();
            CargarBuscadorLibros();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CargarBuscadorLibros(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BuscadorLibrosView.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            borderPane.setCenter(anchorPane);
            BuscadorLibrosController blc = loader.getController();
            blc.setSceneController(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CargarLogin(){

    }
}
