package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import com.hardbug.bibliotecatenango.Models.Prestamo;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
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
    @FXML
    private AnchorPane rootPane;
    private static ArrayList<Prestamo> _prestamos = new ArrayList<>();
    private static FilteredList<Prestamo> _prestamosfiltrados;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Parent bp = ViewSwitcher.getScene().getRoot();
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        ViewSwitcher.showTo(View.DETALLES_LIBROS, IndexApp.TEMA, pb);
        Node contentNodeRight = pb.getRight();
        contentNodeRight.setTranslateX(400);*/
        configurarLista();
        BotonBuscar.setOnAction(actionEvent -> {
            Search();
        });
        Buscador.textProperty().addListener((observable, oldValue, newValue) -> {
            Search();
        });
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
        _prestamos = ConsultarPrestamos();
        IconoCarga.setVisible(false);
        if (!_prestamos.isEmpty()){
            LabelSinPrestamos.setVisible(false);
            PrestamosListView.setVisible(true);
            _prestamosfiltrados = new FilteredList<>(FXCollections.observableArrayList(_prestamos));;
            PrestamosListView.setCellFactory(lv -> new PrestamoItemController());
            PrestamosListView.setItems(_prestamosfiltrados);
        }else{
            LabelSinPrestamos.setVisible(true);
            PrestamosListView.setVisible(false);
        }
    }
}
