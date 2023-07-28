package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Catalogo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControladorOcupaciones extends BDController implements Initializable {

    @FXML
    private Label LabelCrearOcupacion, LabelSinOcupaciones;
    @FXML
    private ListView<Catalogo> ListaOcupaciones;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private TextField Buscador;
    ArrayList<Catalogo> _ocupaciones = new ArrayList<>();
    private static FilteredList<Catalogo> _ocupacionesfiltradas;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarLista();
        IconoCarga.setVisible(false);
        Buscador.textProperty().addListener((observable, oldValue, newValue) -> {
            Search();
        });
        LabelCrearOcupacion.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Crear nueva ocupación");
            dialog.setHeaderText("Ingresa el nombre de la ocupación");
            dialog.setContentText("Nombre: ");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            dialog.showAndWait().ifPresent(text -> {
                if (text.isEmpty()) {
                    Alerta(Alert.AlertType.WARNING, "Campo vacío", "Por favor, ingrese un valor válido");
                } else {
                    Catalogo ocupacion = new Catalogo(text);
                    if(InsertarEditarOcupacion(ocupacion)){
                        Alerta(Alert.AlertType.INFORMATION, "Guardado con éxito", "Se guardó la ocupación: " + text);
                        configurarLista();
                    }else{
                        Alerta(Alert.AlertType.WARNING, "Error", "Ha ocurrido un error al guardar la ocupación: " + text);
                    }
                }
            });
        });
    }

    public void Alerta (Alert.AlertType TipoAlerta, String Titulo, String Contenido){
        Alert alert;
        if (TipoAlerta == Alert.AlertType.WARNING){
            alert = new Alert(Alert.AlertType.WARNING);
        }else{
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        }
        alert.setTitle(Titulo);
        alert.setHeaderText(null);
        alert.setContentText(Contenido);
        Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
        stagealert.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
        alert.showAndWait();
    }

    private void Search() {
        IconoCarga.setVisible(true);
        String searchText = Buscador.getText().toLowerCase();
        _ocupacionesfiltradas.setPredicate(catalogo -> {
            boolean match = catalogo.getNombre().toLowerCase().contains(searchText);
            Platform.runLater(() -> IconoCarga.setVisible(false));
            return match;
        });
    }

    private void configurarLista(){
        _ocupaciones = ConsultarOcupaciones(false);
        if (!_ocupaciones.isEmpty()){
            LabelSinOcupaciones.setVisible(false);
            ListaOcupaciones.setVisible(true);
            IconoCarga.setVisible(false);
            _ocupacionesfiltradas = new FilteredList<>(FXCollections.observableArrayList(_ocupaciones));;
            ListaOcupaciones.setCellFactory(lv -> {
                return new VistaCatalogoController(2);
            });
            ListaOcupaciones.setItems(_ocupacionesfiltradas);
        }else{
            LabelSinOcupaciones.setVisible(true);
            ListaOcupaciones.setVisible(false);
        }
    }
}
