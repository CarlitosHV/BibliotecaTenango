package com.hardbug.bibliotecatenango;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewListaIconosController extends ListCell<MenuItems> {
    private int INSERTAR = 0, EDITAR = 1, ELIMINAR = 2;

    private FXMLLoader fxmlLoader;
    private AnchorPane anchorPane;
    private ImageView imagen;
    private Label Descripcion;
    private EventHandler<ActionEvent> onItemSelected;
    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }


    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    public ViewListaIconosController() {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("ViewListaIconos.fxml"));
        try {
            anchorPane = fxmlLoader.load();
            Descripcion = (Label) fxmlLoader.getNamespace().get("LabelDescription");
            imagen = (ImageView) fxmlLoader.getNamespace().get("ImageIcon");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(MenuItems items, boolean Empty) {
        super.updateItem(items, Empty);
        if (Empty || items == null) {
            setGraphic(null);
        } else {
            Descripcion.setText(items.getDescription());
            imagen.setImage(new Image(Objects.requireNonNull(ViewListaIconosController.class.getResourceAsStream(items.getImagePath()))));
            setGraphic(anchorPane);
        }

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !Empty) {
                switch (items.getDescription()){
                    case "Agregar libro" -> {
                        new AltaLibrosController(INSERTAR);
                        ViewSwitcher.buttonAction(View.CRUD_LIBROS);
                    }
                }
            }
        });
    }
}
