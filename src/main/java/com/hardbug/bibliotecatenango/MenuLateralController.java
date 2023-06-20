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
            ButtonAction(evt, View.MENU_LIBROS);
        });

        ButtonHome.setOnAction(evt -> {
            ButtonAction(evt, View.BUSCADOR_LIBROS);
        });

        ButtonConfig.setOnAction(evt -> {
            ButtonAction(evt, View.MENU_CONFIGURACION);
        });
    }

    private void ButtonAction(ActionEvent evt, View view){
        Node sourceNode = (Node) evt.getSource();
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();
        BorderPane rootPane = (BorderPane) currentStage.getScene().getRoot();
        Node newView = rootPane.getCenter();
        FadeTransition transition = new FadeTransition(Duration.millis(200), newView);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.setOnFinished(actionEvent -> {
            ViewSwitcher.showTo(view, IndexApp.TEMA, rootPane);
        });
        transition.play();
    }
}
