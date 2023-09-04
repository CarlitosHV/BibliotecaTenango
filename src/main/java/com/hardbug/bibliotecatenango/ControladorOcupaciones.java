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
import javafx.util.Duration;

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
            if (newValue.length() > 20) {
                Buscador.setText(oldValue);

            }
            Search();
        });
        LabelCrearOcupacion.setOnMouseClicked(event -> {

           TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Crear nueva ocupación");
            dialog.setHeaderText("Ingresa el nombre de la ocupación");
            dialog.setContentText("Nombre: ");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            ImageView InformacionView = CrearHooverInformacion("/assets/informacion.png", "Ejemplos: Estudiante, Ingeriero Industrial");
            dialog.setGraphic(InformacionView);

            //limitar el campo a 20 caracteres
            dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

                if (newValue.length() > 20) {
                    dialog.getEditor().setText(oldValue);

                }
            });
            dialog.showAndWait().ifPresent(text -> {
                Alert alert;
                if (text.isEmpty()) {
                    alert = new Alertas().CrearAlertaPrecaucion("Campo vacío", "Por favor, ingrese una ocpación válida");
                    alert.showAndWait();
                } else {
                    Catalogo ocupacion = new Catalogo(text);
                    if (text.matches("^(?:[a-zA-Z]\\s?){1,20}$")){
                        if(InsertarEditarOcupacion(ocupacion)){
                            alert = new Alertas().CrearAlertaInformativa("Guardado con éxito", "Se guardó la ocupación: " + text);
                            alert.showAndWait();
                            configurarLista();
                        }else{
                            alert = new Alertas().CrearAlertaError("Error", "Ha ocurrido un error al guardar la ocupación: " + text);
                            alert.showAndWait();
                        }
                    }else{
                        alert = new Alertas().CrearAlertaError("Error", "La ocupación: " + text +" es inválida");
                        alert.showAndWait();
                    }
                }
            });
        });
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

    protected void configurarLista(){
        _ocupaciones = ConsultarOcupaciones(false);
        if (!_ocupaciones.isEmpty()){
            LabelSinOcupaciones.setVisible(false);
            ListaOcupaciones.setVisible(true);
            IconoCarga.setVisible(false);
            _ocupacionesfiltradas = new FilteredList<>(FXCollections.observableArrayList(_ocupaciones));
            ListaOcupaciones.setCellFactory(lv -> new VistaCatalogoController(2, new ControladorGradosEscolares(), this));
            ListaOcupaciones.setItems(_ocupacionesfiltradas);
        }else{
            LabelSinOcupaciones.setVisible(true);
            ListaOcupaciones.setVisible(false);
        }
    }

    public static ImageView CrearHooverInformacion(String rutaImagen,String tooltipText){
        //Imagen con hoover de informacion
        Image Informacion = new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream(rutaImagen))));
        ImageView InformacionView = new ImageView(Informacion);
        InformacionView .setFitWidth(32);
        InformacionView .setFitHeight(32);
        InformacionView.setPickOnBounds(true);
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.setShowDelay(Duration.millis(500));
        Tooltip.install(InformacionView, tooltip);
        return InformacionView;
    }
}
