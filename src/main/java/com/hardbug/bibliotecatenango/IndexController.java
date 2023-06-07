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

public class IndexController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button BotonMenu;
    private boolean isMenuOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewSwitcher.showTo(View.MENU_LATERAL, IndexApp.TEMA, rootPane);
        ViewSwitcher.showTo(View.BUSCADOR_LIBROS, IndexApp.TEMA, rootPane);
        Node contentNode = rootPane.getLeft();
        contentNode.setTranslateX(-250);
        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), contentNode);
        menuTransition.setToX(0);
        BotonMenu.setOnAction(actionEvent -> {
            if (isMenuOpen) {
                menuTransition.setToX(-250);
                isMenuOpen = false;
                BotonMenu.setTranslateX(0);
            } else {
                menuTransition.setToX(0);
                isMenuOpen = true;
                BotonMenu.setTranslateX(60);
            }
            menuTransition.play();
        });
    }
}
