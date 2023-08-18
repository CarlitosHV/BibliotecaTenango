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
    private Button BotonAceptar, BotonCancelar;
    private Stage modalStage;

    public void setModalStage(Stage modalStage) {
        this.modalStage = modalStage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarLista();

        BotonCancelar.setOnAction(event -> {
            cerrarModalMenuLibros();
        });
    }

    void configurarLista(){
        ListViewLibros.setCellFactory(lv -> new ControladorPilaItem(this));
        ListViewLibros.getItems().addAll(_librosSeleccionados);
    }

    private void cerrarModalMenuLibros() {
        if (modalStage != null) {
            modalStage.close();
        }
    }
}
