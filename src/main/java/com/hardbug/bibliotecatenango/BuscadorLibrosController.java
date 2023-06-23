package com.hardbug.bibliotecatenango;

/*Clase que controla el buscador de libros
  Vista a la que está asociada la clase: BuscadorLibrosView.fxml
 */

import javafx.collections.FXCollections;
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
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private Button BotonBuscar;
    @FXML
    private TextField Buscador;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            _libros = bd.TraerLibros();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        IconoCarga.setVisible(false);
        LibrosListView.setCellFactory(lv -> {
            return new BookItemController();
        });
        LibrosListView.setItems(FXCollections.observableArrayList(_libros));
        BotonBuscar.setOnAction(actionEvent -> buscar(Buscador.getText()));
        Buscador.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0){
                buscar(newValue);
            }else{
                if (!_libros.isEmpty()){
                    _libros.clear();
                }
                LibrosListView.getItems().clear();
                try {
                    _libros = bd.TraerLibros();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                LibrosListView.setItems(FXCollections.observableArrayList(_libros));
            }
        });
    }

    void buscar(String palabra){
        IconoCarga.setVisible(true);
        IconoCarga.setProgress(-1.0);

        Task<ArrayList<ClaseLibro>> traer_busqueda = new Task<ArrayList<ClaseLibro>>() {
            @Override
            protected ArrayList<ClaseLibro> call() throws Exception {
                return bd.BusquedaGeneral(palabra);
            }
        };

        new Thread(traer_busqueda).start();


        traer_busqueda.setOnSucceeded(event -> {
            Buscador.setEditable(true);
            IconoCarga.setVisible(false);
            if (!_libros.isEmpty()) {
                _libros.clear();
            }
            LibrosListView.getItems().clear();
            _libros = traer_busqueda.getValue();
            LibrosListView.setItems(FXCollections.observableArrayList(_libros));
        });

        if(traer_busqueda.isRunning()){
            Buscador.setEditable(false);
        }

    }
}
