package com.hardbug.bibliotecatenango;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @FXML
    public BorderPane rootPane;
    @FXML
    private Button BotonMenu;
    private boolean isMenuOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewSwitcher.showTo(View.MENU_LATERAL, IndexApp.TEMA, rootPane);
        ViewSwitcher.showTo(View.BUSCADOR_LIBROS, IndexApp.TEMA, rootPane);
        ViewSwitcher.showTo(View.DETALLES_LIBROS, IndexApp.TEMA, rootPane);
        Parent bp = ViewSwitcher.getScene().getRoot();
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        Node contentNodeLeft = pb.getLeft();
        Node contentNodeCenter = pb.getCenter();
        Node contentNodeRight = pb.getRight();
        contentNodeLeft.setTranslateX(-250);
        contentNodeCenter.setTranslateX(-150);
        contentNodeRight.setTranslateX(400);

        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), contentNodeLeft);
        TranslateTransition menuTransitionC = new TranslateTransition(Duration.seconds(0.3), contentNodeCenter);
        menuTransition.setToX(0);
        menuTransitionC.setToX(0);
        BotonMenu.setOnAction(actionEvent -> {
            if (isMenuOpen) {
                menuTransition.setToX(-250);
                isMenuOpen = false;
                BotonMenu.setTranslateX(0);
                menuTransitionC.setToX(-150);
            } else {
                menuTransition.setToX(0);
                isMenuOpen = true;
                BotonMenu.setTranslateX(60);
                menuTransitionC.setToX(0);
            }
            menuTransition.play();
            menuTransitionC.play();
        });
    }
}
