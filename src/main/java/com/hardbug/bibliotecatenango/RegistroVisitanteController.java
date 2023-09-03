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
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class RegistroVisitanteController extends BDController implements Initializable {


    public RegistroVisitanteController(int OPERACION) {
        this.OPERACION = OPERACION;
    }

    public RegistroVisitanteController() {

    }


    private int OPERACION;

    /* Variables traidas desde le FXML y que se necesitan*/
    @FXML
    private TextField Campo_nombre, Campo_edad, Campo_Apellido_materno,Campo_Apellido_paterno;
    @FXML
    private RadioButton Check_discapacidad_si, Check_discapacidad_no;
    @FXML
    private ComboBox<Catalogo> Combo_grado;
    @FXML
    private ComboBox<Catalogo> Combo_ocupacion;
    @FXML
    private ComboBox<Catalogo> Combo_actividad;

    private static ArrayList<Catalogo> _ocupaciones = new ArrayList<>();
    private static ArrayList<Catalogo> _grados = new ArrayList<>();
    private static ArrayList<Catalogo> _actividades = new ArrayList<>();

    Visitante miVisitante;

    private final IndexApp indexApp = new IndexApp();
    BDController bd = new BDController();

    private static final int ALERTA_ERROR = 0, ALERTA_VISITANTE_GUARDADO = 1, ALERTA_CAMPOS_INVALIDOS = 2;

    private Stage modalStage;

    @FXML
    void AccionBotonIngresar() throws Exception {

        Ingresar();
    }


    private void ConfigurarCombos () throws SQLException {
        _ocupaciones = bd.ConsultarOcupaciones(false);
        _grados = bd.ConsultarGradosEscolares(false);
        _actividades = bd.ConsultarActividades(false);
        Combo_grado.getItems().addAll(_grados);
        Combo_ocupacion.getItems().addAll(_ocupaciones);
        Combo_actividad.getItems().addAll(_actividades);
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
        Check_discapacidad_no.setSelected(true);


    }

    public void validar_nombre() {
        if (Campo_nombre.getText().matches("^[A-Z][a-záéíóú]+(\\s[A-Z][a-záéíóú]+){1,3}$") && !Campo_nombre.getText().isEmpty()) {
            Campo_nombre_bol = true;
            if (IndexApp.TEMA == 1) {
                Campo_nombre.setStyle("-fx-border-color: #595b5d");
            } else {
                Campo_nombre.setStyle("-fx-border-color: black");
            }
        } else {
            Campo_nombre.setStyle("-fx-border-color: red");
            Campo_nombre_bol = false;
        }

    }

    public void validar_apellido_paterno() {
        if (Campo_Apellido_paterno.isEditable()) {
            if (Campo_Apellido_paterno.getText().matches("^[A-Z][a-záéíóúñ]+(\\s[A-Z][a-záéíóúñ]+){0,4}$")
                    && !Campo_Apellido_paterno.getText().isEmpty()) {
                Campo_Apellido_paterno_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_Apellido_paterno.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_Apellido_paterno.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_Apellido_paterno.setStyle("-fx-border-color: red");
                Campo_Apellido_paterno_bol = false;


            }
        }
    }

    public void validar_apellido_materno() {
        if (Campo_Apellido_materno.isEditable()) {
            if (Campo_Apellido_materno.getText().matches("^[A-Z][a-záéíóúñ]+(\\s[A-Z][a-záéíóúñ]+){0,4}$")
                    && !Campo_Apellido_materno.getText().isEmpty()) {
                Campo_Apellido_materno_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_Apellido_materno.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_Apellido_materno.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_Apellido_materno.setStyle("-fx-border-color: red");
                Campo_Apellido_materno_bol = false;


            }
        }
    }

    public void validar_edad() {
        if (Campo_edad.isEditable()) {
            if (Campo_edad.getText().matches("^(?:[1-9][0-9]?|100)$")
                    && !Campo_edad.getText().isEmpty()) {
                Campo_edad_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_edad.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_edad.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_edad.setStyle("-fx-border-color: red");
                Campo_edad_bol = false;
            }
        }
    }

    public void validar_grado() {
        if (Combo_grado.getValue() == null) {
            Combo_grado_bol = false;
            Combo_grado.setStyle("-fx-border-color: red");
        } else {
            Combo_grado.setStyle("-fx-border-color: transparent");
            Combo_grado_bol = true;
        }
    }

    public void validar_actividad() {
        if (Combo_actividad.getValue() == null) {
            Combo_actividad_bol = false;
            Combo_actividad.setStyle("-fx-border-color: red");
        } else {
            Combo_actividad.setStyle("-fx-border-color: transparent");
            Combo_actividad_bol = true;
        }
    }

    public void validar_ocupacion() {
        if (Combo_ocupacion.getValue() == null) {
            Combo_ocupacion_bol = false;
            Combo_ocupacion.setStyle("-fx-border-color: red");
        } else {
            Combo_ocupacion.setStyle("-fx-border-color: transparent");
            Combo_ocupacion_bol = true;
        }
    }


    //limpia los campos
    private void limpiarCampos() {

        Campo_edad.setText("");
        Campo_nombre.setText("");
        Combo_ocupacion.setValue(null);
        Combo_grado.setValue(null);
        Combo_actividad.setValue(null);
        Check_discapacidad_no.setSelected(false);
        Check_discapacidad_si.setSelected(false);
        Campo_Apellido_materno.setText("");
        Campo_Apellido_paterno.setText("");
    }

    void Ingresar() throws Exception {
        int IdNombre = 0;
        miVisitante = new Visitante();
        miVisitante.setNombres(Campo_nombre.getText().trim());
        miVisitante.setAp_paterno(Campo_Apellido_paterno.getText().trim());
        miVisitante.setAp_materno(Campo_Apellido_materno.getText().trim());
        Nombres nombre = new Nombres(IdNombre, miVisitante.getNombres(), miVisitante.getAp_paterno(), miVisitante.getAp_materno());
        miVisitante.nombre = nombre;

        miVisitante.setEdad(Integer.parseInt(Campo_edad.getText().trim()));
        miVisitante.ocupacion = Combo_ocupacion.getValue();
        miVisitante.grado_escolar = Combo_grado.getValue();
        miVisitante.setDiscapacidad(Check_discapacidad_si.selectedProperty().get());
        miVisitante.setFecha(Date.from(Instant.now()));
        miVisitante.Actividad = Combo_actividad.getValue();

        if (bd.InsertarVisitante(miVisitante)) {
            CrearAlerta(ALERTA_VISITANTE_GUARDADO);
            limpiarCampos();

        } else {
            CrearAlerta(ALERTA_ERROR);
            limpiarCampos();

        }
    }


}