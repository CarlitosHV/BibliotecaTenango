package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Catalogo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControladorGradosEscolares extends BDController implements Initializable {
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

    public ListView<Catalogo> getLista(){
        return ListaGrados;
    }

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
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Crear nueva actividad");
            dialog.setHeaderText("Ingresa el nombre de la actividad");
            dialog.setContentText("Nombre: ");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            ImageView InformacionView = ControladorOcupaciones.CrearHooverInformacion("/assets/informacion.png", "Ejemplos: Café literario, Mesa de lectura");
            dialog.setGraphic(InformacionView);

            dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 20) {
                    dialog.getEditor().setText(oldValue);
                }
            });

            dialog.showAndWait().ifPresent(text -> {
                if (text.isEmpty()) {
                    Alerta(Alert.AlertType.WARNING, "Campo vacío", "Por favor, ingrese un valor válido");
                } else {
                    Catalogo actividad = new Catalogo(text);
                    if (text.matches("^(?:[a-zA-Z]\\s?){1,20}$")){
                        if(InsertarEditarActividad(actividad)){
                            Alerta(Alert.AlertType.INFORMATION, "Guardado con éxito", "Se guardó la actividad: " + text);
                            configurarLista();
                        }else{
                            Alerta(Alert.AlertType.WARNING, "Error", "Ha ocurrido un error al guardar la actividad: " + text);
                        }
                    }else{
                        Alerta(Alert.AlertType.WARNING, "Error", "La actividad: " + text + " es inválida");
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
            _actividadesfiltradas = new FilteredList<>(FXCollections.observableArrayList(_actividades));;
            ListaGrados.setCellFactory(lv -> {
                return new VistaCatalogoController(1, this, new ControladorOcupaciones());
            });
            ListaGrados.setItems(_actividadesfiltradas);
        }else{
            LabelSinGrados.setVisible(true);
            ListaGrados.setVisible(false);
        }
    }


}
