package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;

public class BookCrudController extends ListCell<Libro> {
    private FXMLLoader fxmlLoader;
    private GridPane fondoItem;
    private Label LabelTitulo, LabelAutor, LabelEditorial, LabelClave, LabelEstante, LabelClasificacion;
    private EventHandler<ActionEvent> onItemSelected;
    BDController bd = new BDController();
    private boolean isMenuOpen = false;

    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }

    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }


    public BookCrudController() {
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
            LabelClave.setText("Clave registro: " + libro.getClave_registro());
            LabelClasificacion.setText("Clasificación: " + libro.getClasificacion());
            LabelEditorial.setText("Editorial: " + libro.getEditorial());
            LabelEstante.setText(libro.getEstante());
            setGraphic(fondoItem);
        }

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !Empty) {
                Parent bp = ViewSwitcher.getScene().getRoot();
                BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
                Node right = pb.getRight();
                TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
                menuTransition.setToX(0);
                BookDetailsController controller = ViewSwitcher.getBookDetailsController();
                if (controller != null) {
                    controller.initData(libro.getTitulo_libro(), libro.getNombre_autor(), libro.getEditorial(), libro.getClave_registro(),
                            libro.getEstante(), libro.getClasificacion(), libro.getDescripcion_libro(), libro.getExistencias(), BookDetailsController.OPERACION_CRUD);
                }
                menuTransition.play();
            }
        });
    }
}
