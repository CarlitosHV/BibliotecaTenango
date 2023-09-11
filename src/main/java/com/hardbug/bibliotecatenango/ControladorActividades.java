package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Catalogo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControladorActividades extends BDController implements Initializable {
    @FXML
    private Label LabelCrearGrado, LabelSinGrados;
    @FXML
    private ListView<Catalogo> ListaGrados;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private TextField Buscador;
    ArrayList<Catalogo> _actividades = new ArrayList<>();
    private static FilteredList<Catalogo> _actividadesfiltradas;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarLista();
        IconoCarga.setVisible(false);
        Buscador.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20) {
                Buscador.setText(oldValue);
            }
            Search();
        });
        LabelCrearGrado.setOnMouseClicked(event -> {
            TextInputDialog dialog = new Alertas().CrearAlertaInput("Ingresa el nombre de la actividad: ");
            ImageView InformacionView = ControladorOcupaciones.CrearHooverInformacion("/assets/informacion.png", "Ejemplos: Café literario, Mesa de lectura");
            dialog.setGraphic(InformacionView);

            dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 20) {
                    dialog.getEditor().setText(oldValue);
                }
            });

            dialog.showAndWait().ifPresent(text -> {
                Alert alert;
                if (text.isEmpty()) {
                    alert = new Alertas().CrearAlertaPrecaucion("Campo vacío", "Por favor, ingrese un valor válido");
                    alert.showAndWait();
                } else {
                    Catalogo actividad = new Catalogo(text);
                    if (text.matches("^(?:[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]\\s?){1,20}$")){
                        if(InsertarEditarActividad(actividad)){
                            alert = new Alertas().CrearAlertaInformativa("Guardado con éxito", "Se guardó la actividad: " + text);
                            alert.showAndWait();
                            configurarLista();
                        }else{
                            alert = new Alertas().CrearAlertaError("Error", "Ha ocurrido un error al guardar la actividad: " + text);
                            alert.showAndWait();
                        }
                    }else{
                        alert = new Alertas().CrearAlertaError("Error", "La actividad: " + text + " es inválida");
                        alert.showAndWait();
                    }
                }
            });
        });
    }

    private void Search() {
        IconoCarga.setVisible(true);
        String searchText = Buscador.getText().toLowerCase();
        _actividadesfiltradas.setPredicate(catalogo -> {
            boolean match = catalogo.getNombre().toLowerCase().contains(searchText);
            Platform.runLater(() -> IconoCarga.setVisible(false));
            return match;
        });
    }

    protected void configurarLista(){
        _actividades = ConsultarActividades(false);
        if (!_actividades.isEmpty()){
            LabelSinGrados.setVisible(false);
            ListaGrados.setVisible(true);
            IconoCarga.setVisible(false);
            _actividadesfiltradas = new FilteredList<>(FXCollections.observableArrayList(_actividades));
            ListaGrados.setCellFactory(lv -> new VistaCatalogoController(1, this, new ControladorOcupaciones()));
            ListaGrados.setItems(_actividadesfiltradas);
        }else{
            LabelSinGrados.setVisible(true);
            ListaGrados.setVisible(false);
        }
    }


}
