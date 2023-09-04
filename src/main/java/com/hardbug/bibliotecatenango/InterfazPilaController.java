package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class InterfazPilaController extends BuscadorLibrosController implements Initializable {
    @FXML
    private ListView<Libro> ListViewLibros;
    @FXML
    private Button BotonAceptar, BotonCancelar, BotonEliminarTodos;
    private Stage modalStage;

    private BuscadorLibrosController buscadorLibrosController;

    public void getBuscadorLibrosController(BuscadorLibrosController buscadorLibrosController){
        this.buscadorLibrosController = buscadorLibrosController;
    }

    public void setModalStage(Stage modalStage) {
        this.modalStage = modalStage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.configurarLista();
        BotonCancelar.setOnAction(event -> {
            if(!_librosSeleccionados.isEmpty()){
                buscadorLibrosController.PilaLibros.setText(String.valueOf(_librosSeleccionados.size()));
                ContadorLibros = _librosSeleccionados.size();
            }else{
                buscadorLibrosController.PilaLibros.setText("");
                ContadorLibros = 0;
            }
            cerrarModalMenuLibros();
        });

        BotonAceptar.setOnAction(evt -> {
            Stage stage = (Stage) ViewSwitcher.getScene().getWindow();
            mostrarVentanaModal(stage);
            cerrarModalMenuLibros();
        });

        BotonEliminarTodos.setOnAction(evt -> {
            _librosSeleccionados.clear();
            buscadorLibrosController.PilaLibros.setText("");
            cerrarModalMenuLibros();
            ContadorLibros = 0;
        });

    }

    private void mostrarVentanaModal(Stage ownerStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PrestamoView.fxml"));
            Parent root = fxmlLoader.load();

            Stage modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Datos del préstamo");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            PrestamoController modalcontroller = fxmlLoader.getController();
            modalcontroller.setModalStage(modalStage);
            modalcontroller.setBuscadorLibrosController(buscadorLibrosController);
            modalcontroller.setLibros(_librosSeleccionados);
            Scene modalScene = new Scene(root);
            if (IndexApp.TEMA == 0){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(Objects.requireNonNull(MenuLibrosController.class.getResource("/styles/WhiteTheme.css")).toExternalForm());
            }else if (IndexApp.TEMA == 1){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(Objects.requireNonNull(MenuLibrosController.class.getResource("/styles/DarkTheme.css")).toExternalForm());
            }
            modalStage.setScene(modalScene);

            // Crear la animación de escala para iniciar el modal
            ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.3), root);
            scaleIn.setFromX(0);
            scaleIn.setFromY(0);
            scaleIn.setToX(1);
            scaleIn.setToY(1);
            // Crear la animación de escala para cerrar el modal
            ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.3), root);
            scaleOut.setFromX(1);
            scaleOut.setFromY(1);
            scaleOut.setToX(0);
            scaleOut.setToY(0);
            scaleOut.setOnFinished(e -> modalStage.close());

            modalStage.setOnShowing(e -> scaleIn.play());

            modalStage.setOnCloseRequest(eve -> {
                Alert alert = alerta.CrearAlertaConfirmacion("¡Cuidado!", "Si cierras la ventana, se perderá el progreso del préstamo.\n ¿Estás seguro que deseas cerrar la ventana?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    _librosSeleccionados.clear();
                    buscadorLibrosController.PilaLibros.setText("");
                    ContadorLibros = 0;
                    alert.close();
                    eve.consume();
                    scaleOut.play();
                }else{
                    eve.consume();
                }
            });

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void configurarLista(){
        ListViewLibros.setCellFactory(lv -> {
            ControladorPilaItem controladorPilaItem = new ControladorPilaItem(buscadorLibrosController);
            controladorPilaItem.setModalStage(modalStage);
            return controladorPilaItem;
        });
        ListViewLibros.getItems().addAll(_librosSeleccionados);
    }

    private void cerrarModalMenuLibros() {
        if (modalStage != null) {
            modalStage.close();
        }
    }
}
