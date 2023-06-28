package com.hardbug.bibliotecatenango;

/*Clase que controla el buscador de libros
  Vista a la que está asociada la clase: BuscadorLibrosView.fxml
 */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BuscadorLibrosController implements Initializable {
    //Instanciamos al controlador de escenas para controlar la escena

    BDController bd = new BDController();
    @FXML
    private ListView<ClaseLibro> LibrosListView;
    private static ArrayList<ClaseLibro> _libros = new ArrayList<>();
    private static FilteredList<ClaseLibro> _librosfiltrados;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private Button BotonBuscar;
    @FXML
    private TextField Buscador;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _libros = bd.TraerLibros();
        _librosfiltrados = new FilteredList<>(FXCollections.observableArrayList(_libros));;
        IconoCarga.setVisible(false);
        LibrosListView.setCellFactory(lv -> {
            return new BookItemController();
        });
        LibrosListView.setItems(_librosfiltrados);
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
}
