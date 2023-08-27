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

    private void ConfigurarCombos () throws SQLException {
        _ocupaciones = bd.ConsultarOcupaciones(false);
        _grados = bd.ConsultarGradosEscolares(false);
        Combo_grado.getItems().addAll(_grados);
        Combo_ocupacion.getItems().addAll(_ocupaciones);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ConfigurarCombos();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int OPERACION;

    /* Variables traidas desde le FXML y que se necesitan*/
    @FXML
    private TextField Campo_nombre, Campo_direccion, Campo_edad;
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
    void aplicarTemaAlerta(String titulo, String contenido, int tipo) throws SQLException {
        Alert alerta;
        if (tipo == 2) {
            alerta = new Alert(Alert.AlertType.CONFIRMATION);
        } else {
            alerta = new Alert(Alert.AlertType.INFORMATION);
        }
        DialogPane dialogPane = alerta.getDialogPane();
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(BookDetailsController.class.getResourceAsStream("/assets/logotenangoNR.png"))));
        Label content = new Label(alerta.getContentText());
        alerta.setHeaderText(null);
        alerta.setTitle(titulo);
        content.setText(contenido);
        if (IndexApp.TEMA == 0) {
            dialogPane.setStyle("-fx-background-color: white; -fx-text-fill: white");
            content.setTextFill(Color.BLACK);
            alerta.getDialogPane().setContent(content);
        } else {
            dialogPane.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white");
            content.setTextFill(Color.WHITESMOKE);
            alerta.getDialogPane().setContent(content);
        }
        alerta.showAndWait();
    }

    void CrearAlerta(int TIPO_ALERTA) throws SQLException {
        switch (TIPO_ALERTA) {
            case 0 ->
                    aplicarTemaAlerta("¡Ocurrió un error!", "Error al guardar en base de datos", 0);
            case 1 ->
                    aplicarTemaAlerta("¡Bienvenido!", "¡La visita ha sido registrada!", 0);
            case 2 ->
                    aplicarTemaAlerta("¡Campos inválidos!", "Error en procesar la información, verifica que los campos estén llenados de forma correcta", 0);
        }
    }

    void Ingresar() throws Exception {

        boolean visita = bd.RegistarVisita(visitante.getId_visitante(), visitante.getEdad_visitante(), visitante.getId_grado_escolar(), visitante.getOcupacion(),
                visitante.isDiscapaciad(), visitante.getId_nombre(), visitante.getFecha_visita());
        if (visita) {
            CrearAlerta(ALERTA_VISITANTE_GUARDADO);

        } else {
            CrearAlerta(ALERTA_ERROR);

        }




    }
}