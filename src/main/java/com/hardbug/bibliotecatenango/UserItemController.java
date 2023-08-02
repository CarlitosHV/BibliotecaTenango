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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;

public class UserItemController extends ListCell<Usuario> {
    private FXMLLoader fxmlLoader;
    private GridPane fondoItem;
    private Label LabelNombre, LabelEdad, LabelCurp, LabelCorreo, LabelTelefono;
    private EventHandler<ActionEvent> onItemSelected;
    BDController bd = new BDController();
    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }
    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    public UserItemController() {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("UserView.fxml"));
        try {
            fondoItem = fxmlLoader.load();
            LabelNombre = (Label) fxmlLoader.getNamespace().get("LabelNombre");
            LabelEdad = (Label) fxmlLoader.getNamespace().get("LabelEdad");
            LabelCurp = (Label) fxmlLoader.getNamespace().get("LabelCurp");
            LabelCorreo = (Label) fxmlLoader.getNamespace().get("LabelCorreo");
            LabelTelefono = (Label) fxmlLoader.getNamespace().get("LabelTelefono");
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
            LabelEdad.setText("Edad: " + usuario.getEdad());
            LabelCurp.setText("CURP: " + usuario.getCurp());
            LabelCorreo.setText("Correo: " + usuario.getCorreo());
            LabelTelefono.setText("Teléfono: " + usuario.getTelefono());
            setGraphic(fondoItem);
        }

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !Empty) {
                Parent bp = ViewSwitcher.getScene().getRoot();
                BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
                Node right = pb.getRight();
                TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
                menuTransition.setToX(0);
                UserDetailsController cont = ViewSwitcher.getUserDetailsController();
                if (cont != null) {
                    cont.initData(usuario);
                }
                menuTransition.play();
            }
        });
    }
}
