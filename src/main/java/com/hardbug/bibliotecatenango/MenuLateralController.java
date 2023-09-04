package com.hardbug.bibliotecatenango;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuLateralController implements Initializable {
    @FXML
    private Button ButtonBooks, ButtonHome, ButtonConfig, ButtonUsers, ButtonVisitors, ButtonPrestamos;
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

        ButtonUsers.setOnAction(evt -> {
            cerrarVistaDerecha();
            ViewSwitcher.buttonAction(View.MENU_USUARIOS);
        });

        ButtonVisitors.setOnAction(evt ->{
            cerrarVistaDerecha();
            ViewSwitcher.buttonAction(View.REGISTRO_VISITANTES);
        });

        ButtonPrestamos.setOnAction(evt -> {
            cerrarVistaDerecha();
            ViewSwitcher.buttonAction(View.PRESTAMOS);
        });
    }

    void cerrarVistaDerecha(){
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        Node right = pb.getRight();
        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
        menuTransition.setToX(400);
        menuTransition.play();
    }
}
