package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Catalogo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class VistaCatalogoController extends ListCell<Catalogo> {

    private int opcion = 0;

    @FXML
    private Label LabelNombre;
    @FXML
    private Button ButtonEdit, ButtonDelete;
    private AnchorPane Fondo;
    private FXMLLoader fxmlLoader;
    private EventHandler<ActionEvent> onItemSelected;
    BDController bd = new BDController();
    ControladorGradosEscolares CGE = new ControladorGradosEscolares();
    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }
    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    public VistaCatalogoController(int Opcion) {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("VistaCatalogo.fxml"));
        opcion = Opcion;
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
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Editar el grado: " + catalogo.getNombre());
                dialog.setHeaderText("Ingresa el nombre del grado");
                dialog.setContentText("Nombre: ");
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
                dialog.showAndWait().ifPresent(text -> {
                    if (text.isEmpty()) {
                        CGE.Alerta(Alert.AlertType.WARNING, "Campo vacío", "Por favor, ingrese un valor válido");
                    } else {
                        gradoseleccionado.setNombre(text);
                        if(bd.InsertarEditarGrado(gradoseleccionado)){
                            CGE.Alerta(Alert.AlertType.INFORMATION, "Editado con éxito", "Se editó el grado escolar: " + catalogo.getNombre());
                        }else{
                            CGE.Alerta(Alert.AlertType.WARNING, "Error", "Ha ocurrido un error al guardar el grado: " + catalogo.getNombre());
                        }
                    }
                });
            }else if (opcion == 2){
                Catalogo ocupacionseleccionada = catalogo;
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Editar la ocupación: " + catalogo.getNombre());
                dialog.setHeaderText("Ingresa el nombre de la ocupación");
                dialog.setContentText("Nombre: ");
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
                dialog.showAndWait().ifPresent(text -> {
                    if (text.isEmpty()) {
                        CGE.Alerta(Alert.AlertType.WARNING, "Campo vacío", "Por favor, ingrese un valor válido");
                    } else {
                        ocupacionseleccionada.setNombre(text);
                        if(bd.InsertarEditarOcupacion(ocupacionseleccionada)){
                            CGE.Alerta(Alert.AlertType.INFORMATION, "Editado con éxito", "Se editó la ocupación: " + catalogo.getNombre());
                        }else{
                            CGE.Alerta(Alert.AlertType.WARNING, "Error", "Ha ocurrido un error al guardar la ocupación: " + catalogo.getNombre());
                        }
                    }
                });
            }
        });

        ButtonDelete.setOnAction(event -> {
            if (opcion == 1){
                Catalogo gradoseleccionado = catalogo;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ATENCIÓN");
                alert.setContentText("¿Estás seguro de eliminar el grado: " + gradoseleccionado.getNombre() + "?");
                Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
                stagealert.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (bd.EliminarGrado(gradoseleccionado)){
                        CGE.Alerta(Alert.AlertType.INFORMATION, "Eliminación realizada", "Se ha eliminado el grado: " + gradoseleccionado.getNombre());
                    }
                }else{
                    alert.close();
                }
            }else if (opcion == 2){
                Catalogo ocupacionseleccionada = catalogo;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ATENCIÓN");
                alert.setContentText("¿Estás seguro de eliminar la ocupación: " + ocupacionseleccionada.getNombre() + "?");
                Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
                stagealert.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (bd.EliminarOcupacion(ocupacionseleccionada)){
                        CGE.Alerta(Alert.AlertType.INFORMATION, "Eliminación realizada", "Se ha eliminado la ocupación: " + ocupacionseleccionada.getNombre());
                    }
                }else{
                    alert.close();
                }
            }
        });
    }
}
