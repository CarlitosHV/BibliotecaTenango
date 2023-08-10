package com.hardbug.bibliotecatenango;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @FXML
    public BorderPane rootPane;
    @FXML
    private Button BotonMenu;
    @FXML
    private Label LabelSaludo;
    private boolean isMenuOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewSwitcher.showTo(View.MENU_LATERAL, IndexApp.TEMA, rootPane);
        ViewSwitcher.showTo(View.BUSCADOR_LIBROS, IndexApp.TEMA, rootPane);
        ViewSwitcher.showTo(View.DETALLES_LIBROS, IndexApp.TEMA, rootPane);
        Node contentNodeRight = rootPane.getRight();
        contentNodeRight.setTranslateX(400);
        Node contentNodeLeft = rootPane.getLeft();
        contentNodeLeft.setTranslateX(-250);
        ObtenerFecha();

        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), contentNodeLeft);
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

    private void ObtenerFecha(){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        double decimalHour = hour + (minute / 60.0);
        String saludo;
        if (decimalHour >= 20.0) {
            saludo = "Buenas noches";
        } else if (decimalHour >= 12.0) {
            saludo = "Buenas tardes";
        } else {
            saludo = "Buenos días";
        }
        LabelSaludo.setText(saludo);
    }
}
