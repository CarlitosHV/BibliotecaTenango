package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuLibrosController implements Initializable {

    @FXML
    private Label LabelCrearLibro, LabelSinLibros;
    @FXML
    private ListView<Libro> LibrosListView;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private Button BotonBuscar;
    @FXML
    private TextField Buscador;
    @FXML
    private AnchorPane rootPane;
    private static FilteredList<Libro> _librosfiltrados;
    BDController bd = new BDController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewSwitcher.showTo(View.DETALLES_LIBROS, IndexApp.TEMA, IndexController.getRootPane);
        Node contentNodeRight = IndexController.getRootPane.getRight();
        contentNodeRight.setTranslateX(400);
        configurarLista();
        BotonBuscar.setOnAction(actionEvent -> Search());
        Buscador.textProperty().addListener((observable, oldValue, newValue) -> Search());
        LabelCrearLibro.setOnMouseClicked(event -> {
            Buscador.setText("");
            Stage stage = (Stage) rootPane.getScene().getWindow();
            mostrarVentanaModal(stage);
        });
        LibrosListView.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                LibrosListView.getSelectionModel().clearSelection();
            }
        });
    }

    private void Search() {
        IconoCarga.setVisible(true);
        String searchText = Buscador.getText().toLowerCase();
        _librosfiltrados.setPredicate(libro -> {
            boolean match = libro.getTitulo_libro().toLowerCase().contains(searchText)
                    || libro.getNombre_autor().toLowerCase().contains(searchText)
                    || libro.getEditorial().toLowerCase().contains(searchText)
                    || libro.getEstante().toLowerCase().contains(searchText)
                    || libro.getClasificacion().toLowerCase().contains(searchText)
                    || libro.getAnio_edicion().toLowerCase().contains(searchText)
                    || libro.getClave_registro().toLowerCase().contains(searchText);
            Platform.runLater(() -> IconoCarga.setVisible(false));
            return match;
        });
    }
    private void mostrarVentanaModal(Stage ownerStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AltaLibrosView.fxml"));
            Parent root = fxmlLoader.load();

            Stage modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Datos del libro");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
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

            AltaLibrosController modalcontroller = fxmlLoader.getController();
            modalcontroller.setModalStage(modalStage);
            modalcontroller.setBuscadorLibrosController(new BuscadorLibrosController());
            modalcontroller.setMenuLibrosController(this);

            modalStage.setOnShowing(e -> scaleIn.play());
            modalStage.setOnCloseRequest(e -> {
                configurarLista();
                e.consume();
                scaleOut.play();
            });

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void configurarLista(){
        ArrayList<Libro> _libros = bd.TraerLibros();
        IconoCarga.setVisible(false);
        IconoCarga.setProgress(-1.0);
        if (!_libros.isEmpty()){
            LabelSinLibros.setVisible(false);
            LibrosListView.setVisible(true);
            _librosfiltrados = new FilteredList<>(FXCollections.observableArrayList(_libros));
            LibrosListView.setCellFactory(lv -> new BookCrudController(new BuscadorLibrosController() ,this));
            LibrosListView.setItems(_librosfiltrados);
        }else{
            LabelSinLibros.setVisible(true);
            LibrosListView.setVisible(false);
        }
    }
}
