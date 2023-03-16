package com.hardbug.bibliotecatenango;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;

/*Clase que controla el login y la vista
  Vista a la que está asociada la clase: LoginView.fxml
 */
public class LoginController {
    //Instanciamos al controlador de escenas para controlar la escena
    SceneController sceneController;

    //Método para cambiar entre escenas, aún no tiene nada porque aún no se conecta el flujo
    void changescene(){

    }

    //Getters and setters del controlador de escenas
    public SceneController getSceneController() {
        return sceneController;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }


}
