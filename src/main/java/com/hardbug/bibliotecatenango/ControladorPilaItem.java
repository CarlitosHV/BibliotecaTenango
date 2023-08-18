package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ControladorPilaItem extends ListCell<Libro> {
    private FXMLLoader fxmlLoader;
    private AnchorPane fondoItem;
    private Label LabelAutor, LabelTitulo;
    private Button BotonEliminar;
    private EventHandler<ActionEvent> onItemSelected;
    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }

    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }
    private BuscadorLibrosController buscadorLibrosController;
    public ControladorPilaItem(BuscadorLibrosController buscadorLibrosController) {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("VistaPila.fxml"));
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
    }
}
