package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Usuario;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;

public class ComboUser  extends ListCell<Usuario> {
    private FXMLLoader fxmlLoader;
    private AnchorPane fondoItem;
    private Label LabelNombre, LabelCurp, LabelCorreo;
    private EventHandler<ActionEvent> onItemSelected;
    BDController bd = new BDController();
    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }
    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    public ComboUser() {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("ComboUser.fxml"));
        try {
            fondoItem = fxmlLoader.load();
            LabelNombre = (Label) fxmlLoader.getNamespace().get("LabelNombre");
            LabelCurp = (Label) fxmlLoader.getNamespace().get("LabelCURP");
            LabelCorreo = (Label) fxmlLoader.getNamespace().get("LabelCorreo");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Usuario usuario, boolean Empty) {
        super.updateItem(usuario, Empty);
        if (Empty || usuario == null) {
            setGraphic(null);
        } else {
            LabelNombre.setText(usuario.nombre.GetNombreCompleto());
            LabelCurp.setText("CURP: " + usuario.getCurp());
            LabelCorreo.setText("Correo: " + usuario.getCorreo());
            setGraphic(fondoItem);
        }
    }
}
