package com.hardbug.bibliotecatenango;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class ViewIconoGrandeController extends ListCell<MenuItems> {
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

    public ViewIconoGrandeController() {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("ViewIconoGrande.fxml"));
        try {
            anchorPane = fxmlLoader.load();
            Descripcion = (Label) fxmlLoader.getNamespace().get("TextoItem");
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
            imagen.setImage(new Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream(items.getImagePath()))));
            setGraphic(anchorPane);
        }
    }
}
