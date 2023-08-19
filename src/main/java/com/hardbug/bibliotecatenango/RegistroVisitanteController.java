package com.hardbug.bibliotecatenango;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroVisitanteController implements Initializable {

    public RegistroVisitanteController(int OPERACION) {
        this.OPERACION = OPERACION;
    }

    public RegistroVisitanteController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private int OPERACION;

    /* Variables traidas desde le FXML y que se necesitan*/
    @FXML
    private TextField Campo_nombre,Campo_direccion,Campo_edad;
    @FXML
    private ComboBox Combo_ocupacion,Combo_grado;
    @FXML
    private RadioButton Check_discapacidad_si,Check_discapacidad_no;


    private final IndexApp indexApp = new IndexApp();
    private final BDController bdController = new BDController();



}