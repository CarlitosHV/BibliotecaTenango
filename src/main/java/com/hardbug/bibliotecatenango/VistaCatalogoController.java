package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Catalogo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class VistaCatalogoController extends ListCell<Catalogo> {

    private int opcion = 0;

    @FXML
    private final Label LabelNombre;
    @FXML
    private final  Button ButtonEdit, ButtonDelete;
    private final AnchorPane Fondo;
    private final FXMLLoader fxmlLoader;
    BDController bd = new BDController();
    private ControladorGradosEscolares controladorGradosEscolares;
    private ControladorOcupaciones controladorOcupaciones;

    public VistaCatalogoController(int Opcion, ControladorGradosEscolares controladorGradosEscolares,
                                   ControladorOcupaciones controladorOcupaciones) {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("VistaCatalogo.fxml"));
        opcion = Opcion;
        this.controladorGradosEscolares = controladorGradosEscolares;
        this.controladorOcupaciones = controladorOcupaciones;
        try {
            Fondo = fxmlLoader.load();
            LabelNombre = (Label) fxmlLoader.getNamespace().get("LabelNombre");
            ButtonEdit = (Button) fxmlLoader.getNamespace().get("ButtonEdit");
            ButtonDelete = (Button) fxmlLoader.getNamespace().get("ButtonDelete");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Catalogo catalogo, boolean Empty) {
        super.updateItem(catalogo, Empty);
        if (Empty || catalogo == null) {
            setGraphic(null);
        } else {
            LabelNombre.setText(catalogo.getNombre());
            LabelNombre.setVisible(true);
            ButtonDelete.setVisible(false);
            ButtonEdit.setVisible(false);
            setGraphic(Fondo);
        }

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !Empty) {
                LabelNombre.setVisible(true);
                ButtonEdit.setVisible(true);
                ButtonDelete.setVisible(true);
            }
        });

        ButtonEdit.setOnAction(event -> {
            if (opcion == 1){
                Catalogo gradoseleccionado = catalogo;
                TextInputDialog dialog = new Alertas().CrearAlertaInput("Editar la actividad: " + catalogo.getNombre());
                ImageView InformacionEditarGradosView = ControladorOcupaciones.CrearHooverInformacion("/assets/informacion.png", "Ejemplos: Café literario, Mesa de lectura");
                dialog.setGraphic(InformacionEditarGradosView);
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
                        gradoseleccionado.setNombre(text);
                        if(bd.InsertarEditarActividad(gradoseleccionado)){
                            alert = new Alertas().CrearAlertaInformativa("Editado con éxito", "Se editó la actividad: " + catalogo.getNombre());
                            alert.showAndWait();
                            controladorGradosEscolares.configurarLista();
                        }else{
                            alert = new Alertas().CrearAlertaError("Error", "Ha ocurrido un error al guardar la actividad: " + catalogo.getNombre());
                            alert.showAndWait();
                        }
                    }
                });
            }else if (opcion == 2){
                Catalogo ocupacionseleccionada = catalogo;
                TextInputDialog dialog = new Alertas().CrearAlertaInput("Editar la ocupación: " + catalogo.getNombre());
                ImageView InformacionEditarOcupacionesView = ControladorOcupaciones.CrearHooverInformacion("/assets/informacion.png", "Ejemplos: Estudiante, Ingeriero Industrial");
                dialog.setGraphic(InformacionEditarOcupacionesView);
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
                        ocupacionseleccionada.setNombre(text);

                        if (text.matches("^(?:[a-zA-Z]\\s?){1,20}$")){
                            if(bd.InsertarEditarOcupacion(ocupacionseleccionada)){
                                alert = new Alertas().CrearAlertaInformativa("Editado con éxito", "Se editó la ocupación: " + catalogo.getNombre());
                                alert.showAndWait();
                                controladorOcupaciones.configurarLista();
                            }else{
                                alert = new Alertas().CrearAlertaError("Error", "Ha ocurrido un error al guardar la ocupación: " + catalogo.getNombre());
                                alert.showAndWait();
                            }
                        }else{
                            alert = new Alertas().CrearAlertaError("Error", "Por favor, ingrese un valor válido " + text);
                            alert.showAndWait();
                        }
                    }
                });
            }
        });

        ButtonDelete.setOnAction(event -> {
            if (opcion == 1){
                Catalogo gradoseleccionado = catalogo;
                Alert alert = new Alertas().CrearAlertaConfirmacion("ATENCIÓN", "¿Estás seguro de eliminar la actividad: " + gradoseleccionado.getNombre() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Alert alerta;
                    if (bd.EliminarActividad(gradoseleccionado)){
                        alerta = new Alertas().CrearAlertaInformativa( "Eliminación realizada", "Se ha eliminado la actividad: " + gradoseleccionado.getNombre());
                        alerta.showAndWait();
                        controladorGradosEscolares.configurarLista();
                    }else{
                        alerta = new Alertas().CrearAlertaError("Error", "No se puede eliminar una actividad si alguna visita la ha marcado");
                        alerta.showAndWait();
                    }
                }else{
                    alert.close();
                }
            }else if (opcion == 2){
                Catalogo ocupacionseleccionada = catalogo;
                Alert alert = new Alertas().CrearAlertaConfirmacion("ATENCIÓN", "¿Estás seguro de eliminar la ocupación: " + ocupacionseleccionada.getNombre() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Alert alerta;
                    if (bd.EliminarOcupacion(ocupacionseleccionada)){
                        alerta = new Alertas().CrearAlertaInformativa("Eliminación realizada", "Se ha eliminado la ocupación: " + ocupacionseleccionada.getNombre());
                        alerta.showAndWait();
                        controladorOcupaciones.configurarLista();
                    }else{
                        alerta = new Alertas().CrearAlertaError("Error", "No se puede eliminar una ocupación si algún usuario la tiene en su perfil");
                        alerta.showAndWait();
                    }
                }else{
                    alert.close();
                }
            }
        });
    }
}
