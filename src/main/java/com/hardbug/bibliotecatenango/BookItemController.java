package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;

public class BookItemController extends ListCell<Libro> {
    private final GridPane fondoItem;
    private final Label LabelTitulo, LabelAutor, LabelEditorial, LabelClave, LabelEstante, LabelClasificacion, LabelExistencias;

    private BuscadorLibrosController buscadorLibrosController;

    public void setBuscadorLibrosController(BuscadorLibrosController buscadorLibrosController){
        this.buscadorLibrosController = buscadorLibrosController;
    }

    private MenuLibrosController menuLibrosController;

    public void setMenuLibrosController(MenuLibrosController menuLibrosController){
        this.menuLibrosController = menuLibrosController;
    }


    public BookItemController(BuscadorLibrosController buscadorLibrosController, MenuLibrosController menuLibrosController) {
        super();
        this.buscadorLibrosController = buscadorLibrosController;
        this.menuLibrosController = menuLibrosController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BookItem.fxml"));
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
            LabelExistencias.setText(String.valueOf(libro.getExistencias()));
            setGraphic(fondoItem);
        }

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !Empty) {
                BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
                Node right = pb.getRight();
                TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
                menuTransition.setToX(0);
                BookDetailsController controller = ViewSwitcher.getBookDetailsController();
                controller.setmenuLibrosController(menuLibrosController);
                controller.setBuscadorLibrosController(buscadorLibrosController);
                controller.initData(libro, BookDetailsController.SOLICITAR);
                menuTransition.play();
            }
        });
    }
}
