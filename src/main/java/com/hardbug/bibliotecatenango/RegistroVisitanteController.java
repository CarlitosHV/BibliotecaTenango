package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistroVisitanteController extends BDController implements Initializable {


    public RegistroVisitanteController(int OPERACION) {
        this.OPERACION = OPERACION;
    }

    public RegistroVisitanteController() {

    }


    private int OPERACION;

    /* Variables traidas desde le FXML y que se necesitan*/
    @FXML
    private TextField Campo_nombre, Campo_direccion, Campo_edad, Campo_Apellido_materno,Campo_Apellido_paterno;
    @FXML
    private RadioButton Check_discapacidad_si, Check_discapacidad_no;
    @FXML
    private ComboBox<Catalogo> Combo_grado;
    @FXML
    private ComboBox<Catalogo> Combo_ocupacion;

    private static ArrayList<Catalogo> _ocupaciones = new ArrayList<>();
    private static ArrayList<Catalogo> _grados = new ArrayList<>();


    private final Visitante visitante = new Visitante();
    private final IndexApp indexApp = new IndexApp();
    BDController bd = new BDController();

    private static final int ALERTA_ERROR = 0, ALERTA_VISITANTE_GUARDADO = 1, ALERTA_CAMPOS_INVALIDOS = 2;

    private Stage modalStage;

    @FXML
    void AccionBotonIngresar() throws Exception {

        Ingresar();
    }

    /*
      Método que aplica el tema dependiendo el seleccionado y también aplica el texto y la cabecera
   */


    private void ConfigurarCombos () throws SQLException {
        _ocupaciones = bd.ConsultarOcupaciones(false);
        _grados = bd.ConsultarGradosEscolares(false);
        Combo_grado.getItems().addAll(_grados);
        Combo_ocupacion.getItems().addAll(_ocupaciones);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Cargamos los combos de ocupacion y grado
        try {
            ConfigurarCombos();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Hacemos un toglegroup para que solo un radiobutton se active a la vez
        ToggleGroup Si_No = new ToggleGroup();
        Check_discapacidad_si.setToggleGroup(Si_No);
        Check_discapacidad_no.setToggleGroup(Si_No);


    }

//creamos y usamos las alertas como en altaslibros
    void CrearAlerta(int TIPO_ALERTA) throws SQLException {
        switch (TIPO_ALERTA) {
            case 0 ->
                    AltaLibrosController.aplicarTemaAlerta("¡Ocurrió un error!", "Error al guardar en base de datos", 0);
            case 1 ->
                    AltaLibrosController.aplicarTemaAlerta("¡Bienvenido!", "¡La visita ha sido registrada!", 0);
            case 2 ->
                    AltaLibrosController.aplicarTemaAlerta("¡Campos inválidos!", "Error en procesar la información, verifica que los campos estén llenados de forma correcta", 0);

        }
    }



//limpia los campos
    private void limpiarCampos() {
        Campo_direccion.setText("");
        Campo_edad.setText("");
        Campo_nombre.setText("");
        Combo_ocupacion.setValue(null);
        Combo_grado.setValue(null);
        Check_discapacidad_no.setSelected(false);
        Check_discapacidad_si.setSelected(false);
        Campo_Apellido_materno.setText("");
        Campo_Apellido_paterno.setText("");
    }
    void Ingresar() throws Exception {
        llenarvisitante();

        if (bd.InsertarVisitante(visitante)) {
            CrearAlerta(ALERTA_VISITANTE_GUARDADO);
            limpiarCampos();

        } else {
            CrearAlerta(ALERTA_ERROR);
            limpiarCampos();

        }
    }

    private void llenarvisitante() {



    }

}