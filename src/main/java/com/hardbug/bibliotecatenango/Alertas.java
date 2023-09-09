package com.hardbug.bibliotecatenango;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    public TextInputDialog CrearAlertaInput(String titulo) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));

        setThemeTextInputDialog(dialog, dialog.getDialogPane(), dialog.getEditor(), (Button) dialog.getDialogPane().lookupButton(ButtonType.OK));

        return dialog;
    }

    private void setThemeTextInputDialog(TextInputDialog dialog, DialogPane dialogPane, TextField field, Button button) {
        Button buttonCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        if (IndexApp.TEMA == 0) {
            dialogPane.setStyle("-fx-background-color: white;");
            field.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-radius: 3px;");
            button.setStyle("-fx-background-color: #118511; -fx-text-fill: white; -fx-cursor: hand;");
            if (buttonCancel != null) {
                buttonCancel.setStyle("-fx-background-color: #118511; -fx-text-fill: white; -fx-cursor: hand;");
            }
        } else {
            dialogPane.setStyle("-fx-background-color: #3c3f41;");
            field.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: #aaacae; -fx-border-color: #595b5d; -fx-border-radius: 3px;");
            button.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
            if (buttonCancel != null) {
                buttonCancel.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
            }
        }
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
        Button buttonCancel = (Button) alerta.getDialogPane().lookupButton(ButtonType.CANCEL);
        if (IndexApp.TEMA == 0) {
            dialogPane.setStyle("-fx-background-color: white;");
            content.setTextFill(Color.BLACK);
            alerta.getDialogPane().setContent(content);
            button.setStyle("-fx-background-color: #118511; -fx-text-fill: white; -fx-cursor: hand;");
            if (buttonCancel != null) {
                buttonCancel.setStyle("-fx-background-color: #118511; -fx-text-fill: white; -fx-cursor: hand;");
            }
        } else {
            dialogPane.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white");
            content.setTextFill(Color.WHITESMOKE);
            alerta.getDialogPane().setContent(content);
            button.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
            if (buttonCancel != null) {
                buttonCancel.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
            }
        }
    }
}
