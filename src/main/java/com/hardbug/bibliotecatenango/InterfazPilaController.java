package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
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

        BotonEliminarTodos.setOnAction(evt -> {
            _librosSeleccionados.clear();
            buscadorLibrosController.PilaLibros.setText("");
            cerrarModalMenuLibros();
            ContadorLibros = 0;
        });

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
