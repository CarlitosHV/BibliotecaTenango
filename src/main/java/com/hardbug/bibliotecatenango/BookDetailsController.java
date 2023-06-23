package com.hardbug.bibliotecatenango;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BookDetailsController implements Initializable {
    @FXML
    private Button ButtonCerrar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonCerrar.setOnAction(actionEvent -> {
            Parent bp = ViewSwitcher.getScene().getRoot();
        });
    }
}
