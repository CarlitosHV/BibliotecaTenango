package com.hardbug.bibliotecatenango;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class Alertas {

    public Alert CrearAlertaInformativa(String titulo, String contenido){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        configAlert(alerta, titulo, contenido, null);
        return alerta;
    }

    public Alert CrearAlertaInformativa(String titulo, String contenido, String cabecera){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        configAlert(alerta, titulo, contenido, cabecera);
        return alerta;
    }

    public Alert CrearAlertaPrecaucion(String titulo, String contenido){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        configAlert(alerta, titulo, contenido, null);
        return alerta;
    }

    public Alert CrearAlertaPrecaucion(String titulo, String contenido, String cabecera){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        configAlert(alerta, titulo, contenido, cabecera);
        return alerta;
    }

    public Alert CrearAlertaConfirmacion(String titulo, String contenido){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        configAlert(alerta, titulo, contenido, null);
        return alerta;
    }

    public Alert CrearAlertaConfirmacion(String titulo, String contenido, String cabecera){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        configAlert(alerta, titulo, contenido, cabecera);
        return alerta;
    }

    public Alert CrearAlertaError(String titulo, String contenido){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        configAlert(alerta, titulo, contenido, null);
        return alerta;
    }

    public Alert CrearAlertaError(String titulo, String contenido, String cabecera){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        configAlert(alerta, titulo, contenido, cabecera);
        return alerta;
    }

    private static void configAlert(Alert alerta, String titulo, String contenido, String cabecera){
        DialogPane dialogPane = alerta.getDialogPane();
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(BookDetailsController.class.getResourceAsStream("/assets/logotenangoNR.png"))));
        Label content = new Label(alerta.getContentText());
        if(cabecera != null){
            alerta.setHeaderText(cabecera);
        }else{
            alerta.setHeaderText(null);
        }
        alerta.setTitle(titulo);
        content.setText(contenido);
        Button button = (Button) alerta.getDialogPane().lookupButton(ButtonType.OK);
        setThemeAlert(alerta, dialogPane, content, button);
    }

    private static void setThemeAlert(Alert alerta, DialogPane dialogPane, Label content, Button button) {
        if (IndexApp.TEMA == 0) {
            dialogPane.setStyle("-fx-background-color: white;");
            content.setTextFill(Color.BLACK);
            alerta.getDialogPane().setContent(content);
            button.setStyle("-fx-background-color: gray; -fx-text-fill: black; -fx-border-color: black");
        } else {
            dialogPane.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white");
            content.setTextFill(Color.WHITESMOKE);
            alerta.getDialogPane().setContent(content);
            button.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: white");
        }
    }
}
