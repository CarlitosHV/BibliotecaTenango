package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Catalogo;
import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
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
    ArrayList<Catalogo> _grados = new ArrayList<>();
    private static FilteredList<Catalogo> _gradosfiltrados;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarLista();
        IconoCarga.setVisible(false);
        Buscador.textProperty().addListener((observable, oldValue, newValue) -> {
            Search();
        });
    }

    private void Search() {
        IconoCarga.setVisible(true);
        String searchText = Buscador.getText().toLowerCase();
        _gradosfiltrados.setPredicate(catalogo -> {
            boolean match = catalogo.getNombre().toLowerCase().contains(searchText);
            Platform.runLater(() -> IconoCarga.setVisible(false));
            return match;
        });
    }

    private void configurarLista(){
        _grados = ConsultarGradosEscolares(false);
        if (!_grados.isEmpty()){
            LabelSinGrados.setVisible(false);
            ListaGrados.setVisible(true);
            IconoCarga.setVisible(false);
            _gradosfiltrados = new FilteredList<>(FXCollections.observableArrayList(_grados));;
            ListaGrados.setCellFactory(lv -> {
                return new VistaCatalogoController();
            });
            ListaGrados.setItems(_gradosfiltrados);
        }else{
            LabelSinGrados.setVisible(true);
            ListaGrados.setVisible(false);
        }
    }
}
