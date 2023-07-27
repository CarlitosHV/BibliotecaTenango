package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Estados;
import com.hardbug.bibliotecatenango.Models.Localidad;
import com.hardbug.bibliotecatenango.Models.Municipios;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AltasUsersController implements Initializable {
    @FXML
    private TextField Campo_correo, Campo_contrasenia, Campo_curp, Campo_telefono, Campo_nombre, Campo_edad, Campo_apellido_paterno,
    Campo_apellido_materno, Campo_codigo, Campo_ocupacion, Campo_calle;
    @FXML
    private ComboBox Combo_sexo;
    @FXML
    private ComboBox<Estados> Combo_estado;
    @FXML
    private ComboBox<Municipios> Combo_municipio;
    @FXML
    private ComboBox<Localidad> Combo_localidad;
    @FXML
    private ComboBox Combo_grado;
    @FXML
    private Button BotonGuardar;
    @FXML
    private Separator Separador_dos, Separador_tres, Separador_cuatro;
    @FXML
    private Label Label_datos_del_libro, Label_cabecera, Label_datos_referencia;
    @FXML
    private Hyperlink Hyperlink_curp;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private AnchorPane Fondo;

    private static ArrayList<Municipios> _municipios = new ArrayList<>();
    private static ArrayList<Localidad> _localidades = new ArrayList<>();

    Estados estados = new Estados();
    Municipios municipios = new Municipios();
    private static int Acumulador = 0;

    BDController bd = new BDController();

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
        IconoCarga.setVisible(false);
        ConfigurarCellFactory();
        try {
            ConfigurarCombos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

      //bloqueo los campos de municipio y localidad al iniciar para que lleven un orden al seleccionar los campos
        Combo_municipio.setDisable(true);
        Combo_localidad.setDisable(true);

        Combo_estado.setOnAction(actionEvent -> {
            Combo_localidad.setDisable(true);
            if (Combo_estado.getValue() != null){
                Combo_municipio.setDisable(false);
                Combo_municipio.getItems().clear();
                _municipios.clear();
                IconoCarga.setVisible(true);
                IconoCarga.setProgress(-1.0);
                Fondo.setOpacity(0.5);
                TareaMunicipios();
            } else{
                Combo_municipio.setDisable(true);
                Combo_localidad.setDisable(true);
            }
        });

        Combo_municipio.setOnAction(actionEvent -> {
            Combo_localidad.setDisable(true);
            Combo_municipio.setPromptText("Municipio");
            if(Combo_municipio.getValue() != null){
                Combo_localidad.setDisable(false);
                Combo_localidad.getItems().clear();
                _localidades.clear();
                Combo_localidad.setPromptText("Localidad");
                Campo_codigo.setText("");
                IconoCarga.setVisible(true);
                IconoCarga.setProgress(-1.0);
                Fondo.setOpacity(0.5);
                TareaLocalidades();
            }else{
                Combo_localidad.setDisable(true);
            }
        });

        Combo_localidad.setOnAction(actionEvent -> {
            Localidad localidad = Combo_localidad.getValue();
            if (localidad != null) {
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

        Campo_codigo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0){
                Combo_municipio.setDisable(true);
                Combo_localidad.setDisable(true);
                Combo_estado.setDisable(true);
            }else{
                Combo_estado.setDisable(false);
            }
            if (newValue.length() == 5) {
                /*try {
                    IconoCarga.setVisible(true);
                    IconoCarga.setProgress(-1.0);
                    Fondo.setOpacity(0.5);
                    TareaCodigoPostal(Integer.parseInt(Campo_codigo.getText()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }*/
            }else{
                Combo_localidad.getItems().clear();
                Combo_municipio.getItems().clear();
                Combo_municipio.setPromptText("Municipio");
                Combo_estado.setPromptText("Estado");
                Combo_localidad.setPromptText("Localidad");
            }
        });
    }

    private void ConfigurarCombos() throws SQLException {
        ArrayList<Estados> _estados = bd.BuscarEstados();
        ConfigurarCellFactory();
        Combo_estado.getItems().addAll(_estados);
    }

    private void TareaMunicipios() {
        Estados estado = Combo_estado.getValue();
        Task<ArrayList<Municipios>> traer_municipios = new Task<>() {
            @Override
            protected ArrayList<Municipios> call() throws SQLException {
                return bd.BuscarMunicipios(estado.getEstado());
            }
        };
        IconoCarga.setVisible(true);
        Fondo.setOpacity(0.5);

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

    private void TareaMunicipiosCP(){
        Estados estado = Combo_estado.getValue();
        Task<ArrayList<Municipios>> traer_municipios = new Task<>() {
            @Override
            protected ArrayList<Municipios> call() throws SQLException {return bd.BuscarMunicipios(estado.getEstado());
            }
        };
        new Thread(traer_municipios).start();
        traer_municipios.setOnSucceeded(event -> {
            _municipios = traer_municipios.getValue();
            Combo_municipio.getItems().addAll(_municipios);
        });
    }

    private void TareaLocalidades() {
        Estados estado = Combo_estado.getValue();
        Municipios municipio = Combo_municipio.getValue();
        IconoCarga.setVisible(true);
        Fondo.setOpacity(0.5);
        CompletableFuture<ArrayList<Localidad>> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return bd.BuscarLocalidades(municipio.getMunicipio(), estado.getEstado());
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        });
        completableFuture.thenAccept(localidades -> {
            IconoCarga.setVisible(false);
            Fondo.setOpacity(1.0);
            Combo_localidad.setPromptText("Localidad");
            Combo_localidad.getItems().setAll(localidades);
        });
    }

    /*private void TareaCodigoPostal(int CP) throws SQLException {
        ArrayList<Estados> _estados = bd.BuscarEstados();
        Combo_estado.getItems().addAll(_estados);
        Combo_localidad.getItems().clear();
        _localidades.clear();
        _localidades = bd.BuscarCodigoPostal(CP);
        if (!_localidades.isEmpty()){
            IconoCarga.setVisible(false);
            Fondo.setOpacity(1.0);
            Combo_localidad.getItems().addAll(_localidades);
            Combo_localidad.setPromptText("Localidad");
            Platform.runLater(() -> {
                estados.setEstado(_localidades.get(0).getEstado());
                municipios.setMunicipio(_localidades.get(0).getMunicipio());
                Combo_estado.setValue(estados);
                Combo_municipio.setValue(municipios);
            });

        }
    }*/
    //Carga los estados en el combo estados
    private void ConfigurarCellFactory(){
        Combo_estado.setCellFactory(new Callback<ListView<Estados>, ListCell<Estados>>() {
            @Override
            public ListCell<Estados> call(ListView<Estados> listView) {
                return new ListCell<Estados>() {
                    @Override
                    protected void updateItem(Estados estado, boolean empty) {
                        super.updateItem(estado, empty);
                        if (estado != null) {
                            setText(estado.getEstado());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        Combo_municipio.setCellFactory(new Callback<ListView<Municipios>, ListCell<Municipios>>() {
            @Override
            public ListCell<Municipios> call(ListView<Municipios> listView) {
                return new ListCell<Municipios>() {
                    @Override
                    protected void updateItem(Municipios municipio, boolean empty) {
                        super.updateItem(municipio, empty);
                        if (municipio != null) {
                            setText(municipio.getMunicipio());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        Combo_localidad.setCellFactory(new Callback<ListView<Localidad>, ListCell<Localidad>>() {
            @Override
            public ListCell<Localidad> call(ListView<Localidad> listView) {
                return new ListCell<Localidad>() {
                    @Override
                    protected void updateItem(Localidad localidad, boolean empty) {
                        super.updateItem(localidad, empty);
                        if (localidad != null) {
                            setText(localidad.getLocalidad());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }
}
