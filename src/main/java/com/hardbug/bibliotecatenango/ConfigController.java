package com.hardbug.bibliotecatenango;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConfigController extends BDController implements Initializable {

    @FXML
    private ToggleButton ToggleActivar;
    @FXML
    private Label LabelGrados, LabelOcupaciones;
    @FXML
    private AnchorPane fondo;

    IndexApp indexApp = new IndexApp();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (IndexApp.TEMA == ViewSwitcher.TEMA_OSCURO){
            ToggleActivar.setSelected(true);
            ToggleActivar.setText("Desactivar");
        }

        ToggleActivar.setOnAction(actionEvent -> {
            if (ToggleActivar.isSelected()){
                ViewSwitcher.applyCSS(ViewSwitcher.TEMA_OSCURO);
                ViewSwitcher.BANDERA_TEMA = 1;
                IndexApp.TEMA = 1;
                indexApp.EscribirPropiedades("theme", String.valueOf(ViewSwitcher.BANDERA_TEMA));
                ToggleActivar.setText("Desactivar");
            }else{
                ViewSwitcher.applyCSS(ViewSwitcher.TEMA_CLARO);
                ViewSwitcher.BANDERA_TEMA = 0;
                IndexApp.TEMA = 0;
                indexApp.EscribirPropiedades("theme", String.valueOf(ViewSwitcher.BANDERA_TEMA));
                ToggleActivar.setText("Activar");
            }
        });

        LabelGrados.setOnMouseClicked(event -> {
            Stage stage = (Stage) fondo.getScene().getWindow();
            abrirModalGrados(stage);
        });

        LabelOcupaciones.setOnMouseClicked(event -> {

        });
    }

    private void abrirModalGrados(Stage ownerStage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VistaGradosEscolares.fxml"));
            Parent root = fxmlLoader.load();

            Stage modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Grados Escolares");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            Scene modalScene = new Scene(root);
            if (IndexApp.TEMA == 0){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(MenuLibrosController.class.getResource("/styles/WhiteTheme.css").toExternalForm());
            }else if (IndexApp.TEMA == 1){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(MenuLibrosController.class.getResource("/styles/DarkTheme.css").toExternalForm());
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
            modalStage.setOnCloseRequest(e -> {
                e.consume();
                scaleOut.play();
            });

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
