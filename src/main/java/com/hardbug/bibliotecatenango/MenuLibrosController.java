package com.hardbug.bibliotecatenango;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuLibrosController implements Initializable {

    @FXML
    private Label LabelCategoria;
    @FXML
    private ImageView ImagenCategoria;
    @FXML
    private ListView<MenuItems> ListViewItems;

    private boolean isListCard = false;
    private ArrayList<MenuItems> _items = new ArrayList<>(
            Arrays.asList(
                    new MenuItems("/assets/ico_addbook.png", "Agregar libro"),
                    new MenuItems("/assets/ico_editbook.png", "Editar libro"),
                    new MenuItems("/assets/ico_deletebook.png", "Eliminar libro")
            )
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LabelCategoria.setText("Vista cuadrícula");
        ImagenCategoria.setImage(new Image(Objects.requireNonNull(MenuLibrosController.class.getResourceAsStream("/assets/ico_cuadricula.png"))));
        CambiarALista();
        LabelCategoria.setOnMouseClicked(mouseEvent -> {
            isListCard = !isListCard;
            actualizarVista();
        });
    }

    private void actualizarVista() {
        if (isListCard) {
            LabelCategoria.setText("Vista de lista");
            ImagenCategoria.setImage(new Image(Objects.requireNonNull(MenuLibrosController.class.getResourceAsStream("/assets/ico_lista.png"))));
            CambiarACuadricula();
        } else {
            LabelCategoria.setText("Vista cuadrícula");
            ImagenCategoria.setImage(new Image(Objects.requireNonNull(MenuLibrosController.class.getResourceAsStream("/assets/ico_cuadricula.png"))));
            CambiarALista();
        }
    }

    private void CambiarALista(){
        ListViewItems.getItems().clear();
        ListViewItems.setOrientation(Orientation.VERTICAL);
        ListViewItems.setCellFactory(menuItemsListView -> {
            return new ViewListaIconosController();
        });
        ListViewItems.setItems(FXCollections.observableArrayList(_items));
    }

    private void CambiarACuadricula(){
        ListViewItems.getItems().clear();
        ListViewItems.setOrientation(Orientation.HORIZONTAL);
        ListViewItems.setCellFactory(menuItemsListView -> {
            return new ViewIconoGrandeController();
        });
        ListViewItems.setItems(FXCollections.observableArrayList(_items));
    }
}
