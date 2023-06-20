package com.hardbug.bibliotecatenango;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {

    @FXML
    private ToggleButton ToggleActivar;
    IndexApp indexApp = new IndexApp();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (IndexApp.TEMA == ViewSwitcher.TEMA_OSCURO){
            ToggleActivar.setSelected(true);
            ToggleActivar.setText("Desactivar");
        }
        ToggleActivar.setOnAction(actionEvent -> {
            if (ToggleActivar.isSelected()){
                ViewSwitcher.applyCSS(ViewSwitcher.TEMA_OSCURO);
                ViewSwitcher.BANDERA_TEMA = 1;
                IndexApp.TEMA = 1;
                indexApp.EscribirPropiedades("theme", String.valueOf(ViewSwitcher.BANDERA_TEMA));
                ToggleActivar.setText("Desactivar");
            }else{
                ViewSwitcher.applyCSS(ViewSwitcher.TEMA_CLARO);
                ViewSwitcher.BANDERA_TEMA = 1;
                IndexApp.TEMA = 0;
                indexApp.EscribirPropiedades("theme", String.valueOf(ViewSwitcher.BANDERA_TEMA));
                ToggleActivar.setText("Activar");
            }
        });
    }
}
