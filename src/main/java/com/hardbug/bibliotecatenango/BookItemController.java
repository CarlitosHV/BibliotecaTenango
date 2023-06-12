package com.hardbug.bibliotecatenango;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class BookItemController extends ListCell<ClaseLibro> {
    private FXMLLoader fxmlLoader;
    private GridPane fondoItem;
    private Label LabelTitulo, LabelAutor, LabelEditorial, LabelClave, LabelEstante, LabelClasificacion, LabelExistencias;
    private EventHandler<ActionEvent> onItemSelected;
    BDController bd = new BDController();

    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }

    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    public BookItemController() {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("BookItem.fxml"));
        try {
            fondoItem = fxmlLoader.load();
            LabelAutor = (Label) fxmlLoader.getNamespace().get("LabelAutor");
            LabelTitulo = (Label) fxmlLoader.getNamespace().get("LabelTitulo");
            LabelEditorial = (Label) fxmlLoader.getNamespace().get("LabelEditorial");
            LabelClave = (Label) fxmlLoader.getNamespace().get("LabelClave");
            LabelClasificacion = (Label) fxmlLoader.getNamespace().get("LabelClasificacion");
            LabelEstante = (Label) fxmlLoader.getNamespace().get("LabelEstante");
            LabelExistencias = (Label) fxmlLoader.getNamespace().get("LabelExistencias");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(ClaseLibro libro, boolean Empty) {
        super.updateItem(libro, Empty);
        if (Empty || libro == null) {
            setGraphic(null);
        } else {
            LabelTitulo.setText("Título: " + libro.getTitulo_libro());
            LabelAutor.setText("Autor: " + libro.getNombre_autor());
            LabelClave.setText("Clave registro: " + libro.getClave_registro());
            LabelClasificacion.setText("Clasificación: " + libro.getClasificacion());
            LabelEditorial.setText("Editorial: " + libro.getEditorial());
            LabelEstante.setText(libro.getEstante());
            LabelExistencias.setText(String.valueOf(libro.getExistencias()));
            setGraphic(fondoItem);
        }
    }
}
