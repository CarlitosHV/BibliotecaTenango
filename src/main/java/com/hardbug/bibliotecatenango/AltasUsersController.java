package com.hardbug.bibliotecatenango;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AltasUsersController implements Initializable {
    @FXML
    private TextField Campo_correo, Campo_contrasenia, Campo_curp, Campo_telefono, Campo_nombre, Campo_edad, Campo_apellido_paterno,
    Campo_apellido_materno, Campo_codigo, Campo_ocupacion, Campo_calle;
    @FXML
    private ComboBox Combo_sexo, Combo_estado, Combo_municipio, Combo_localidad, Combo_grado;
    @FXML
    private Button BotonGuardar;
    @FXML
    private Separator Separador_dos, Separador_tres, Separador_cuatro;
    @FXML
    private Label Label_datos_del_libro, Label_cabecera, Label_datos_referencia;
    @FXML
    private Hyperlink Hyperlink_curp;

    public void validar_correo(KeyEvent keyEvent) {
    }

    public void validar_contrasenia(KeyEvent keyEvent) {
    }

    public void validar_curp(KeyEvent keyEvent) {
    }

    public void validar_telefono(KeyEvent keyEvent) {
    }

    public void validar_nombre(KeyEvent keyEvent) {
    }

    public void validar_edad(KeyEvent keyEvent) {
    }

    public void validar_apellido_paterno(KeyEvent keyEvent) {
    }

    public void validar_apellido_materno(KeyEvent keyEvent) {
    }

    public void validar_ocupacion(KeyEvent keyEvent) {
    }

    public void validar_codigo(KeyEvent keyEvent) {
    }

    public void validar_calle(KeyEvent keyEvent) {
    }

    public void AccionesBotonGuardar(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Hyperlink_curp.setOnAction(actionEvent -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                String direccion = "https://www.gob.mx/curp/";
                desktop.browse(new URI(direccion));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }
}
