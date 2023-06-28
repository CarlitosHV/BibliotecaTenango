package com.hardbug.bibliotecatenango;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BookDetailsController implements Initializable {
    @FXML
    private Button ButtonCerrar, ButtonEliminar, ButtonEditar, ButtonSolicitar;
    @FXML
    private Label LabelTitulo, LabelAutor, LabelEditorial, LabelClaveRegistro, LabelEstante, LabelClasificacion, LabelDisponibilidad;
    @FXML
    private TextArea TextAreaDescripcion;

    public static int SOLICITAR = 0, OPERACION_CRUD = 1;
    public void initData(String titulo, String autor, String editorial, String clave, String estante, String clasificacion, String descripcion, int existencias, int operacion) {
        LabelTitulo.setText(titulo);
        LabelAutor.setText("Autor: " + autor);
        LabelEditorial.setText("Editorial: " + editorial);
        LabelClaveRegistro.setText("Clave registro: " + clave);
        LabelEstante.setText("Estante: " + estante);
        LabelClasificacion.setText("Clasificación: " + clasificacion);
        LabelDisponibilidad.setText("Disponibles: " + String.valueOf(existencias));
        TextAreaDescripcion.setText(descripcion);
        if (SOLICITAR == operacion){
            ButtonEditar.setVisible(false);
            ButtonEliminar.setVisible(false);
            ButtonSolicitar.setVisible(true);
        }else{
            ButtonEditar.setVisible(true);
            ButtonEliminar.setVisible(true);
            ButtonSolicitar.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonCerrar.setOnAction(actionEvent -> {
            Parent bp = ViewSwitcher.getScene().getRoot();
            BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
            Node right = pb.getRight();
            TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
            menuTransition.setToX(400);
            menuTransition.setOnFinished(actionEvent1 -> {
                limpiar();
            });
            menuTransition.play();
        });
    }

    private void limpiar(){
        LabelTitulo.setText("");
        LabelAutor.setText("");
        LabelEditorial.setText("");
        LabelClaveRegistro.setText("");
        LabelEstante.setText("");
        LabelClasificacion.setText("");
        LabelDisponibilidad.setText("");
        TextAreaDescripcion.setText("");
    }
}
