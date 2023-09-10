package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AltasUsersController extends BDController implements Initializable {
    @FXML
    private TextField Campo_correo, Campo_curp, Campo_telefono, Campo_nombre, Campo_edad, Campo_apellido_paterno,
            Campo_apellido_materno, Campo_codigo, Campo_calle;
    @FXML
    private PasswordField Campo_contrasenia;
    @FXML
    private ComboBox<String> Combo_sexo;
    @FXML
    private ComboBox<Catalogo> Combo_ocupacion;
    @FXML
    private ComboBox<Estados> Combo_estado;
    @FXML
    private ComboBox<Municipios> Combo_municipio;
    @FXML
    private ComboBox<Localidad> Combo_localidad;
    @FXML
    private ComboBox<Catalogo> Combo_grado;
    @FXML
    private Button BotonGuardar;
    @FXML
    private Label Label_cabecera;
    @FXML
    private Hyperlink Hyperlink_curp;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private AnchorPane Fondo;

    private static ArrayList<Municipios> _municipios = new ArrayList<>();
    private static ArrayList<Localidad> _localidades = new ArrayList<>();
    private static ArrayList<Estados> _estados = new ArrayList<>();
    private static final ArrayList<String> _sexos = new ArrayList<>();

    private MenuUsuariosController menuUsuariosController;
    private UserDetailsController userDetailsController;

    public void setUserDetailsController(UserDetailsController userDetailsController) {
        this.userDetailsController = userDetailsController;
    }

    public void setMenuUsuariosController(MenuUsuariosController menuUsuariosController) {
        this.menuUsuariosController = menuUsuariosController;
    }

    private boolean isTextFieldAction = false;
    BDController bd = new BDController();

    Alertas alertas = new Alertas();
    Usuario mUsuario;
    public static Usuario editUsuario;
    private Stage modalStage;

    public void setModalStage(Stage modalStage) {
        this.modalStage = modalStage;
    }

    public static int OPERACION = 1;
    /* Variables booleanas que marcan si los campos están correctos */
    private static boolean Titulo_correo_bol, Contrasenia_bol, Curp_bol, Telefono_bol, Nombre_bol, Edad_bol,
            Apellido_paterno_bol, Apellido_materno_bol, Calle_bol;

    boolean camposValidos() {
        return Titulo_correo_bol && Contrasenia_bol && Curp_bol && Telefono_bol && Nombre_bol && Edad_bol &&
                Apellido_paterno_bol && Apellido_materno_bol && Calle_bol;

    }

    public void validar_correo() {
        if (Campo_correo.isEditable()) {
            if (Campo_correo.getText().matches("\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}\\b")
                    && !Campo_correo.getText().isEmpty()) {
                Titulo_correo_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_correo.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_correo.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_correo.setStyle("-fx-border-color: red");
                Titulo_correo_bol = false;
            }
        }
    }

    //valida campos entre 6 y 12 carecteres y deve contener un número almenos con minuscusculas io mayúsculas o carecteres especiales
    public void validar_contrasenia() {
        if (Campo_contrasenia.isEditable()) {
            if (Campo_contrasenia.getText().matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,12}$")
                    && !Campo_contrasenia.getText().isEmpty()) {
                Contrasenia_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_contrasenia.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_contrasenia.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_contrasenia.setStyle("-fx-border-color: red");
                Contrasenia_bol = false;
            }
        }
    }

    public void validar_curp() {
        if (Campo_curp.isEditable()) {
            if (Campo_curp.getText().matches("^[A-Z]{4}\\d{6}[HM]{1}[A-Z]{5}[A0-9]{2}$")
                    && !Campo_curp.getText().isEmpty()) {
                Curp_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_curp.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_curp.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_curp.setStyle("-fx-border-color: red");
                Curp_bol = false;
            }
        }
    }

    public void validar_telefono() {
        if (Campo_telefono.isEditable()) {
            if (Campo_telefono.getText().matches("^\\d{10}$")
                    && !Campo_telefono.getText().isEmpty()) {
                Telefono_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_telefono.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_telefono.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_telefono.setStyle("-fx-border-color: red");
                Telefono_bol = false;

            }
        }
    }

    public void validar_nombre() {
        if (Campo_nombre.isEditable()) {
            if (Campo_nombre.getText().matches("^[A-Z][a-záéíóúñ]+(\\s[A-Z][a-záéíóúñ]+){0,4}$")
                    && !Campo_nombre.getText().isEmpty()) {
                Nombre_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_nombre.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_nombre.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_nombre.setStyle("-fx-border-color: red");
                Nombre_bol = false;


            }
        }
    }

    public void validar_edad() {
        if (Campo_edad.isEditable()) {
            if (Campo_edad.getText().matches("^(?:[1-9][0-9]?|100)$")
                    && !Campo_edad.getText().isEmpty()) {
                Edad_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_edad.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_edad.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_edad.setStyle("-fx-border-color: red");
                Edad_bol = false;


            }
        }
    }

    public void validar_apellido_paterno() {
        if (Campo_apellido_paterno.isEditable()) {
            if (Campo_apellido_paterno.getText().matches("^[A-Z][a-záéíóúñ]+(\\s[A-Z][a-záéíóúñ]+){0,4}$")
                    && !Campo_apellido_paterno.getText().isEmpty()) {
                Apellido_paterno_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_apellido_paterno.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_apellido_paterno.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_apellido_paterno.setStyle("-fx-border-color: red");
                Apellido_paterno_bol = false;


            }
        }
    }


    public void validar_apellido_materno() {
        if (Campo_apellido_materno.isEditable()) {
            if (Campo_apellido_materno.getText().matches("^[A-Z][a-záéíóúñ]+(\\s[A-Z][a-záéíóúñ]+){0,4}$")
                    && !Campo_apellido_materno.getText().isEmpty()) {
                Apellido_materno_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_apellido_materno.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_apellido_materno.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_apellido_materno.setStyle("-fx-border-color: red");
                Apellido_materno_bol = false;


            }
        }
    }


    public void validar_calle() {
        if (Campo_calle.isEditable()) {
            if (Campo_calle.getText().matches("[A-Z][a-záéíóúñ]+\\s*([A-Z][a-záéíóúñ]*\\s?){0,8}#*[0-9]*")
                    && !Campo_calle.getText().isEmpty()) {
                Calle_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_calle.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_calle.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_calle.setStyle("-fx-border-color: red");
                Calle_bol = false;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IconoCarga.setVisible(false);
        try {
            ConfigurarCombos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Entra al IF si se va a editar el usuario
        if (OPERACION == 2) {
            Label_cabecera.setText("Editar al usuario " + editUsuario.Nombre);
            Campo_nombre.setText(editUsuario.nombre.Nombre);
            Campo_apellido_paterno.setText(editUsuario.nombre.ApellidoPaterno);
            Campo_apellido_materno.setText(editUsuario.nombre.ApellidoMaterno);
            Campo_edad.setText(String.valueOf(editUsuario.getEdad()));
            Campo_correo.setText(editUsuario.Correo);
            Campo_contrasenia.setText(editUsuario.Contrasenia);
            Campo_curp.setText(editUsuario.Curp);
            Campo_telefono.setText(String.valueOf(editUsuario.getTelefono()));
            Campo_codigo.setText(editUsuario.direccion.CP);
            Campo_calle.setText(editUsuario.direccion.Calle);
            Campo_contrasenia.setText(editUsuario.getContrasenia());
            try {
                TareaCodigoPostal(Integer.parseInt(editUsuario.direccion.CP));
                Combo_localidad.setValue(new Localidad(editUsuario.direccion.IdLocalidad, editUsuario.direccion.Localidad,
                        editUsuario.direccion.Municipio, editUsuario.direccion.Estado, Integer.parseInt(editUsuario.direccion.CP)));
                Combo_sexo.setValue(editUsuario.sexo);
                Combo_ocupacion.setValue(editUsuario.Ocupacion);
                Combo_grado.setValue(editUsuario.GradoEscolar);
                Combo_municipio.setDisable(true);
                Combo_estado.setDisable(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        Combo_estado.setOnAction(actionEvent -> {
            Combo_localidad.setDisable(true);
            if (Combo_estado.getValue() != null && !isTextFieldAction) {
                Combo_municipio.setDisable(false);
                Combo_municipio.getItems().clear();
                _municipios.clear();
                IconoCarga.setVisible(true);
                IconoCarga.setProgress(-1.0);
                Fondo.setOpacity(0.5);
                TareaMunicipios();
            } else {
                Combo_municipio.setPromptText("Municipio");
                Combo_estado.setPromptText("Estado");
                Combo_municipio.setDisable(true);
                Combo_localidad.setDisable(true);
            }
        });

        Combo_municipio.setOnAction(actionEvent -> {
            Combo_localidad.setDisable(true);
            Combo_municipio.setPromptText("Municipio");
            if (Combo_municipio.getValue() != null && !isTextFieldAction) {
                Combo_localidad.setDisable(false);
                Combo_localidad.getItems().clear();
                _localidades.clear();
                Combo_localidad.setPromptText("Localidad");
                Campo_codigo.setText("");
                IconoCarga.setVisible(true);
                IconoCarga.setProgress(-1.0);
                Fondo.setOpacity(0.5);
                try {
                    TareaLocalidades();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Combo_localidad.setOnAction(actionEvent -> {
            Combo_localidad.setPromptText("Localidad");
            Localidad localidad = Combo_localidad.getValue();
            if (localidad != null && Campo_codigo.getText().isEmpty()){
                Campo_codigo.setText(localidad.getCP().toString());
            }
        });


        //crea un hipervinculo para buscar en curp
        Hyperlink_curp.setOnAction(actionEvent -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                String direccion = "https://www.gob.mx/curp/";
                desktop.browse(new URI(direccion));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

        Campo_codigo.setOnMouseClicked(event -> isTextFieldAction = true);
        Combo_estado.setOnMouseClicked(event -> isTextFieldAction = false);

        Campo_codigo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Campo_codigo.getLength() > 0) {
                Combo_municipio.setDisable(true);
                Combo_localidad.setDisable(true);
                Combo_estado.setDisable(true);
                if (Campo_codigo.getLength() == 4 || Campo_codigo.getLength() == 5) {
                    try {
                        TareaCodigoPostal(Integer.parseInt(Campo_codigo.getText()));
                        Combo_localidad.setPromptText("Localidad");
                        Combo_localidad.setValue(null);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Combo_estado.setValue(null);
                    Combo_municipio.setValue(null);
                    Combo_localidad.setPromptText("Localidad");
                    Combo_localidad.setValue(null);
                }
            } else {
                Combo_estado.setValue(null);
                Combo_municipio.setValue(null);
                Combo_localidad.setValue(null);
                Combo_estado.setDisable(false);
                Combo_localidad.setPromptText("Localidad");
            }
        });

        BotonGuardar.setOnAction(event -> {
            if (camposValidos()) {
                Alert alert;
                int IdNombre = 0;
                int IdDir = 0;
                mUsuario = new Usuario();
                if (editUsuario != null) {
                    mUsuario.IdUsuario = editUsuario.IdUsuario;
                    IdNombre = editUsuario.nombre.getIdNombre();
                    IdDir = editUsuario.direccion.IdDireccion;
                }
                mUsuario.setNombre(Campo_nombre.getText().trim());
                mUsuario.setApellidoPaterno(Campo_apellido_paterno.getText().trim());
                mUsuario.setApellidoMaterno(Campo_apellido_materno.getText().trim());
                mUsuario.setSexo(Combo_sexo.getValue());
                mUsuario.setEdad(Integer.parseInt(Campo_edad.getText().trim()));
                mUsuario.setCorreo(Campo_correo.getText().trim());
                mUsuario.nombre = new Nombres(IdNombre, mUsuario.getNombre(), mUsuario.getApellidoPaterno(), mUsuario.getApellidoMaterno());
                try {
                    String contraseniacifrada = ClaseCifrarContrasenia.encript(Campo_contrasenia.getText().trim());
                    mUsuario.setContrasenia(contraseniacifrada);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                mUsuario.setCurp(Campo_curp.getText().trim());
                mUsuario.setTelefono(new BigInteger(Campo_telefono.getText().trim()));
                mUsuario.setOcupacion(Combo_ocupacion.getValue());
                mUsuario.setGradoEscolar(Combo_grado.getValue());
                mUsuario.setCodigoPostal(Integer.parseInt(Campo_codigo.getText().trim()));
                mUsuario.setEstado(Combo_estado.getValue());
                mUsuario.setMunicipio(Combo_municipio.getValue());
                mUsuario.setLocalidad(Combo_localidad.getValue());
                mUsuario.setCalle(Campo_calle.getText().trim());
                mUsuario.direccion = new Direccion(IdDir, mUsuario.getCalle(), Campo_codigo.getText().trim(), mUsuario.Municipio.getId(), mUsuario.Estado.getId(), mUsuario.Localidad.Id);
                try {
                    if (InsertarActualizarUsuario(mUsuario)) {
                        if (OPERACION == 2) {
                            alert = new Alertas().CrearAlertaInformativa("¡Usuario actualizado!", "El usuario: " + mUsuario.getNombre() + " ha sido actualizado");
                            alert.showAndWait();
                        } else {
                            alert = new Alertas().CrearAlertaInformativa("¡Usuario registrado!", "El usuario: " + mUsuario.getNombre() + " ha sido registrado");
                            alert.showAndWait();
                        }
                        OPERACION = 0;
                        cerrarModalMenuUsuarios();
                    } else {
                        alert = new Alertas().CrearAlertaError("Error", "Ha ocurrido un error al registrar al usuario");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert alert = alertas.CrearAlertaError("Campos inválidos", "Verifica la información de los campos");
                alert.showAndWait();
            }
        });

        Campo_correo.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 30) {
                return null;
            }
            return change;
        }));

        Campo_contrasenia.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 12) {
                return null;
            }
            return change;
        }));

        Campo_curp.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 18) {
                return null;
            }
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        Campo_telefono.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 10) {
                return null;
            }
            return change;
        }));

        Campo_nombre.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 30) {
                return null;
            }
            return change;
        }));

        Campo_edad.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 2) {
                return null;
            }
            return change;
        }));

        Campo_apellido_paterno.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 20) {
                return null;
            }
            return change;
        }));

        Campo_apellido_materno.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 20) {
                return null;
            }
            return change;
        }));

        Campo_calle.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 50) {
                return null;
            }
            return change;
        }));

        Campo_codigo.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            Combo_localidad.setPromptText("Localidad");
            if (newText.length() > 5) {
                return null;
            }
            return change;
        }));
    }

    private void ConfigurarCombos() throws SQLException {
        _sexos.clear();
        _estados = bd.BuscarEstados();
        ConfigurarCellFactory();
        Combo_estado.getItems().addAll(_estados);
        ArrayList<Catalogo> _ocupaciones = bd.ConsultarOcupaciones(false);
        ArrayList<Catalogo> _grados = bd.ConsultarGradosEscolares(false);
        Combo_grado.getItems().addAll(_grados);
        Combo_ocupacion.getItems().addAll(_ocupaciones);
        _sexos.addAll(Arrays.asList("Masculino", "Femenino", "Otro"));
        Combo_sexo.getItems().addAll(_sexos);
        Combo_localidad.setDisable(true);
        Combo_municipio.setDisable(true);
    }

    private void TareaMunicipios() {
        Estados estado = Combo_estado.getValue();
        Task<ArrayList<Municipios>> traer_municipios = new Task<>() {
            @Override
            protected ArrayList<Municipios> call() {
                return bd.BuscarMunicipios(estado.getEstado());
            }
        };
        IconoCarga.setVisible(true);

        traer_municipios.setOnSucceeded(event -> {
            IconoCarga.setVisible(false);
            Fondo.setOpacity(1.0);
            Combo_municipio.getItems().setAll(traer_municipios.getValue());
        });

        traer_municipios.setOnFailed(event -> {
            IconoCarga.setVisible(false);
            Fondo.setOpacity(1.0);
        });
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(traer_municipios);
        executor.shutdown();
    }


    private void TareaLocalidades() throws SQLException {
        Estados estado = Combo_estado.getValue();
        Municipios municipio = Combo_municipio.getValue();
        IconoCarga.setVisible(true);
        _localidades = BuscarLocalidades(municipio.getMunicipio(), estado.getEstado());
        Combo_localidad.setPromptText("Localidad");
        Combo_localidad.getItems().setAll(_localidades);
        if (!_localidades.isEmpty()) {
            IconoCarga.setVisible(false);
            Fondo.setOpacity(1.0);
            Combo_localidad.setPromptText("Localidad");
        }else{
            Combo_localidad.setPromptText("Localidad");
        }
    }

    private void TareaCodigoPostal(int CP) throws SQLException {
        Municipios IdMuni = null;
        Combo_localidad.getItems().clear();
        Combo_municipio.getItems().clear();
        _localidades.clear();
        _municipios.clear();
        _localidades = BuscarCodigoPostal(CP);
        if (!_localidades.isEmpty()) {
            Estados IdEstado = new Estados(_localidades.get(0).IdEstado, _localidades.get(0).Estado);
            Combo_localidad.setPromptText("Localidad");
            Combo_municipio.getItems().addAll(_municipios);
            Combo_localidad.getItems().addAll(_localidades);
            IconoCarga.setVisible(false);
            Fondo.setOpacity(1.0);
            for (Estados estado : _estados) {
                if (estado.getId() == IdEstado.getId()) {
                    Combo_estado.setValue(IdEstado);
                    _municipios = BuscarMunicipios(IdEstado.getEstado());
                    IdMuni = new Municipios(_localidades.get(0).IdMunicipio, _localidades.get(0).Municipio);
                    break;
                }
            }

            for (Municipios municipio : _municipios) {
                if (municipio.getId() == IdMuni.getId()) {
                    Combo_municipio.setValue(IdMuni);
                    break;
                }
            }
            Combo_localidad.setPromptText("Localidad");
            Combo_localidad.setDisable(false);
        }else{
            Combo_localidad.setPromptText("Localidad");
            Combo_estado.setPromptText("Estado");
            Combo_municipio.setPromptText("Municipio");
            Combo_localidad.getItems().clear();
        }
        Platform.runLater(() -> {
            Combo_localidad.setPromptText("Localidad");
        });
    }

    //Carga los estados en el combo estados
    private void ConfigurarCellFactory() {

        Combo_estado.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Estados estado, boolean empty) {
                super.updateItem(estado, empty);
                if (estado != null) {
                    setText(estado.getEstado());
                } else {
                    setText(null);
                }
            }
        });

        Combo_municipio.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Municipios municipio, boolean empty) {
                super.updateItem(municipio, empty);
                if (municipio != null) {
                    setText(municipio.getMunicipio());
                } else {
                    setText(null);
                }
            }
        });

        Combo_localidad.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Localidad localidad, boolean empty) {
                super.updateItem(localidad, empty);
                if (localidad != null) {
                    setText(localidad.getLocalidad());
                } else {
                    setText(null);
                }
            }
        });
    }

    private void cerrarModalMenuUsuarios() {
        if (modalStage != null) {
            menuUsuariosController.configurarLista();
            modalStage.close();
        }
    }
}
