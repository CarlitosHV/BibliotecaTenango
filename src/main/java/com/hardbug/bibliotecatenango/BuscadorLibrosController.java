package com.hardbug.bibliotecatenango;

/*Clase que controla el buscador de libros
  Vista a la que está asociada la clase: BuscadorLibrosView.fxml
 */

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BuscadorLibrosController implements Initializable {
    //Instanciamos al controlador de escenas para controlar la escena

    BDController bd = new BDController();
    @FXML
    private ListView<Libro> LibrosListView;
    private static ArrayList<Libro> _libros = new ArrayList<>();
    private static FilteredList<Libro> _librosfiltrados;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private Button BotonBuscar;
    @FXML
    private TextField Buscador;
    @FXML
    private AnchorPane Fondo;
    @FXML
    private Label LabelSinLibros;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    void configurarLista(){
        _libros = bd.TraerLibros();
        IconoCarga.setVisible(false);
        if (!_libros.isEmpty()){
            LabelSinLibros.setVisible(false);
            LibrosListView.setVisible(true);
            _librosfiltrados = new FilteredList<>(FXCollections.observableArrayList(_libros));;
            LibrosListView.setCellFactory(lv -> new BookItemController(this, new MenuLibrosController()));
            LibrosListView.setItems(_librosfiltrados);
        }else{
            LabelSinLibros.setVisible(true);
            LibrosListView.setVisible(false);
        }
    }
}
