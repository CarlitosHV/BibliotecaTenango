package com.hardbug.bibliotecatenango;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookDetailsController implements Initializable {
    @FXML
    private Button ButtonCerrar, ButtonEliminar, ButtonEditar, ButtonSolicitar;
    @FXML
    private Label LabelTitulo, LabelAutor, LabelEditorial, LabelClaveRegistro, LabelEstante, LabelClasificacion, LabelDisponibilidad;
    @FXML
    private TextArea TextAreaDescripcion;
    BDController bd = new BDController();
    String Clave = "";
    int ALERTA_CONFIRMACION = 1, ALERTA_PRECAUCION = 2;

    public static int SOLICITAR = 0, OPERACION_CRUD = 1;
    public void initData(String titulo, String autor, String editorial, String clave, String estante, String clasificacion, String descripcion, int existencias, int operacion) {
        Clave = clave;
        LabelTitulo.setText(titulo);
        LabelAutor.setText("Autor: " + autor);
        LabelEditorial.setText("Editorial: " + editorial);
        LabelClaveRegistro.setText("Clave registro: " + clave);
        LabelEstante.setText("Estante: " + estante);
        LabelClasificacion.setText("Clasificación: " + clasificacion);
        LabelDisponibilidad.setText("Disponibles: " + String.valueOf(existencias));
        TextAreaDescripcion.setText(descripcion);
        if (SOLICITAR == operacion){
            ButtonEditar.setVisible(false);
            ButtonEliminar.setVisible(false);
            ButtonSolicitar.setVisible(true);
        }else{
            ButtonEditar.setVisible(true);
            ButtonEliminar.setVisible(true);
            ButtonSolicitar.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonCerrar.setOnAction(actionEvent -> {
            CerrarVista();
        });

        ButtonEliminar.setOnAction(actionEvent -> {
            Alert alerta = crearAlerta("Precaución", "¿Estás seguro de eliminar el libro? " + LabelTitulo.getText(),  ALERTA_PRECAUCION);
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alerta.close();
                boolean eliminado = bd.BorrarLibro(Clave);
                if (eliminado){
                    Alert al = crearAlerta("Eliminación correcta", "¡Se ha eliminado el libro el libro " + LabelTitulo.getText() + "!", ALERTA_CONFIRMACION);
                    Optional<ButtonType> result1 = al.showAndWait();
                    if (result1.isPresent() && result1.get() == ButtonType.OK) {
                        CerrarVista();
                    }
                }else{
                    Alert alertaR = crearAlerta("Error", "Ocurrió un error al eliminar el libro \n" + LabelTitulo.getText(), ALERTA_CONFIRMACION);
                    alertaR.showAndWait();
                }
            }
        });

        ButtonEditar.setOnAction(actionEvent -> {
            EditBooksController.clave_registro = Clave;
            Stage stage = (Stage) ViewSwitcher.getScene().getWindow();
            mostrarVentanaModal(stage);
        });
    }

    private void CerrarVista() {
        Parent bp = ViewSwitcher.getScene().getRoot();
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        Node right = pb.getRight();
        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
        menuTransition.setToX(400);
        menuTransition.setOnFinished(actionEvent1 -> {
            limpiar();
        });
        menuTransition.play();
    }

    public void limpiar(){
        LabelTitulo.setText("");
        LabelAutor.setText("");
        LabelEditorial.setText("");
        LabelClaveRegistro.setText("");
        LabelEstante.setText("");
        LabelClasificacion.setText("");
        LabelDisponibilidad.setText("");
        TextAreaDescripcion.setText("");
    }

    public Alert crearAlerta(String titulo, String contenido, int tipo){
        Alert alerta;
        if(tipo == ALERTA_CONFIRMACION){
            alerta = new Alert(Alert.AlertType.INFORMATION);
        }else{
            alerta = new Alert(Alert.AlertType.WARNING);
        }
        DialogPane dialogPane = alerta.getDialogPane();
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(BookDetailsController.class.getResourceAsStream("/assets/logotenangoNR.png"))));
        Label content = new Label(alerta.getContentText());
        alerta.setHeaderText(null);
        alerta.setTitle(titulo);
        content.setText(contenido);
        Button button = (Button) alerta.getDialogPane().lookupButton(ButtonType.OK);
        if (IndexApp.TEMA == 0) {
            dialogPane.setStyle("-fx-background-color: white;");
            content.setTextFill(Color.BLACK);
            alerta.getDialogPane().setContent(content);
            button.setStyle("-fx-background-color: gray; -fx-text-fill: black; -fx-border-color: black");
        } else {
            dialogPane.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white");
            content.setTextFill(Color.WHITESMOKE);
            alerta.getDialogPane().setContent(content);
            button.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: white");
        }
        return alerta;
    }

    private void mostrarVentanaModal(Stage ownerStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditBooksView.fxml"));
            Parent root = fxmlLoader.load();

            Stage modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Datos del libro");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            EditBooksController modalcontroller = fxmlLoader.getController();
            modalcontroller.setModalStage(modalStage);
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
                EditBooksController.clave_registro = "";
                e.consume();
                scaleOut.play();
            });

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
