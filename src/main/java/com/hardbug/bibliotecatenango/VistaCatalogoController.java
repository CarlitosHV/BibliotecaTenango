package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Catalogo;
import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VistaCatalogoController extends ListCell<Catalogo> {

    @FXML
    private Label LabelNombre;
    @FXML
    private Button ButtonEdit, ButtonDelete;
    private AnchorPane Fondo;
    private FXMLLoader fxmlLoader;
    private EventHandler<ActionEvent> onItemSelected;
    BDController bd = new BDController();
    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }
    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    public VistaCatalogoController() {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("VistaCatalogo.fxml"));
        try {
            Fondo = fxmlLoader.load();
            LabelNombre = (Label) fxmlLoader.getNamespace().get("LabelNombre");
            ButtonEdit = (Button) fxmlLoader.getNamespace().get("ButtonEdit");
            ButtonDelete = (Button) fxmlLoader.getNamespace().get("ButtonDelete");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Catalogo catalogo, boolean Empty) {
        super.updateItem(catalogo, Empty);
        if (Empty || catalogo == null) {
            setGraphic(null);
        } else {
            LabelNombre.setText(catalogo.getNombre());
            LabelNombre.setVisible(true);
            ButtonDelete.setVisible(false);
            ButtonEdit.setVisible(false);
            setGraphic(Fondo);
        }

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !Empty) {
                LabelNombre.setVisible(true);
                ButtonEdit.setVisible(true);
                ButtonDelete.setVisible(true);
            }
        });
    }


}
