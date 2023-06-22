package com.hardbug.bibliotecatenango;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
            ViewSwitcher.buttonAction(View.MENU_LIBROS);
        });

        ButtonHome.setOnAction(evt -> {
            ViewSwitcher.buttonAction(View.BUSCADOR_LIBROS);
        });

        ButtonConfig.setOnAction(evt -> {
            ViewSwitcher.buttonAction(View.MENU_CONFIGURACION);
        });
    }
}
