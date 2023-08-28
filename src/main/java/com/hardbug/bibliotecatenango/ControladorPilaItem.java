package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorPilaItem extends ListCell<Libro> {
    private final AnchorPane fondoItem;
    private final Label LabelAutor;
    private final Label LabelTitulo;
    private final Button BotonEliminar;
    private EventHandler<ActionEvent> onItemSelected;
    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }

    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }
    private BuscadorLibrosController buscadorLibrosController;
    private Stage modalStage;
    public void setModalStage(Stage modalStage) {
        this.modalStage = modalStage;
    }
    public ControladorPilaItem(BuscadorLibrosController buscadorLibrosController) {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VistaPila.fxml"));
        this.buscadorLibrosController = buscadorLibrosController;
        try {
            fondoItem = fxmlLoader.load();
            LabelAutor = (Label) fxmlLoader.getNamespace().get("LabelAutor");
            LabelTitulo = (Label) fxmlLoader.getNamespace().get("LabelTitulo");
            BotonEliminar = (Button) fxmlLoader.getNamespace().get("ButtonDelete");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Libro libro, boolean Empty) {
        super.updateItem(libro, Empty);
        if (Empty || libro == null) {
            setGraphic(null);
        } else {
            LabelTitulo.setText(libro.getTitulo_libro());
            LabelAutor.setText("Autor: " + libro.getNombre_autor());
            BotonEliminar.setVisible(false);
            setGraphic(fondoItem);
        }

        setOnMouseClicked(event -> {
            BotonEliminar.setVisible(true);
        });

        BotonEliminar.setOnAction(evt -> {
            for (int i = 0; i <= buscadorLibrosController._librosSeleccionados.size(); i++) {
                if (buscadorLibrosController._librosSeleccionados.get(i) == libro) {
                    buscadorLibrosController._librosSeleccionados.remove(i);
                    buscadorLibrosController.ContadorLibros = buscadorLibrosController.ContadorLibros - 1;
                    getListView().getItems().remove(libro);
                    if(buscadorLibrosController._librosSeleccionados.isEmpty()){
                        buscadorLibrosController.PilaLibros.setText("");
                        cerrarModalDetalles();
                    }
                }
            }
        });
    }

    private void cerrarModalDetalles() {
        if (modalStage != null) {
            modalStage.close();
        }
    }
}
