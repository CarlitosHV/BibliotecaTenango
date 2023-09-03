package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Prestamo;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuPrestamosController extends BDController implements Initializable {

    @FXML
    private Label LabelSinPrestamos;
    @FXML
    private ListView<Prestamo> PrestamosListView;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private Button BotonBuscar;
    @FXML
    private TextField Buscador;
    private static FilteredList<Prestamo> _prestamosfiltrados;
    Prestamo prestamo = new Prestamo();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewSwitcher.showTo(View.PRESTAMO_DETAIL, IndexApp.TEMA, IndexController.getRootPane);
        Node contentNodeRight = IndexController.getRootPane.getRight();
        contentNodeRight.setTranslateX(400);
        configurarLista();
        BotonBuscar.setOnAction(actionEvent -> Search());
        Buscador.textProperty().addListener((observable, oldValue, newValue) -> Search());
    }

    private void Search() {
        IconoCarga.setVisible(true);
        String searchText = Buscador.getText().toLowerCase();
        _prestamosfiltrados.setPredicate(prestamo -> {
            boolean match = prestamo.Usuario.nombre.GetNombreCompleto().toLowerCase().contains(searchText)
                    || prestamo.FechaInicio.toString().toLowerCase().contains(searchText)
                    || prestamo.FechaFin.toString().toLowerCase().contains(searchText)
                    || prestamo.Usuario.getCurp().toLowerCase().contains(searchText);
            Platform.runLater(() -> IconoCarga.setVisible(false));
            return match;
        });
    }

    void configurarLista(){
        ArrayList<Prestamo> _prestamos = ConsultarPrestamos();
        IconoCarga.setVisible(false);
        if (!_prestamos.isEmpty()){
            LabelSinPrestamos.setVisible(false);
            PrestamosListView.setVisible(true);
            _prestamosfiltrados = new FilteredList<>(FXCollections.observableArrayList(_prestamos));
            PrestamosListView.setCellFactory(lv -> new PrestamoItemController());
            PrestamosListView.setItems(_prestamosfiltrados);
            PrestamosListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    PrestamosListView.setOnMouseClicked(mouseEvent -> {
                        if(mouseEvent.getButton() == MouseButton.PRIMARY){
                            BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
                            Node right = pb.getRight();
                            TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
                            menuTransition.setToX(0);
                            PrestamoDetailController cont = ViewSwitcher.getPrestamoDetailController();
                            cont.setMenuPrestamosController(this);
                            prestamo = newValue;
                            cont.initData(prestamo);
                            menuTransition.play();
                        }
                    });
                }
            });
        }else{
            LabelSinPrestamos.setVisible(true);
            PrestamosListView.setVisible(false);
        }
    }
}
