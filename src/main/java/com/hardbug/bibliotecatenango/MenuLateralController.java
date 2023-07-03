package com.hardbug.bibliotecatenango;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuLateralController implements Initializable {
    @FXML
    private Button ButtonBooks, ButtonHome, ButtonConfig;
    @FXML
    private GridPane fondo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonBooks.setOnAction(evt -> {
            cerrarVistaDerecha();
            ViewSwitcher.buttonAction(View.MENU_LIBROS);
        });

        ButtonHome.setOnAction(evt -> {
            cerrarVistaDerecha();
            ViewSwitcher.buttonAction(View.BUSCADOR_LIBROS);
        });

        ButtonConfig.setOnAction(evt -> {
            cerrarVistaDerecha();
            ViewSwitcher.buttonAction(View.MENU_CONFIGURACION);
        });
    }

    void cerrarVistaDerecha(){
        Parent bp = ViewSwitcher.getScene().getRoot();
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        Node right = pb.getRight();
        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
        menuTransition.setToX(400);
        menuTransition.play();
    }
}
