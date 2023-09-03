package com.hardbug.bibliotecatenango;


import com.hardbug.bibliotecatenango.Models.Usuario;
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
import java.util.Objects;

public class UserItemController extends ListCell<Usuario> {
    private final GridPane fondoItem;
    private final Label LabelNombre, LabelEdad, LabelCurp, LabelCorreo, LabelTelefono;
    private final MenuUsuariosController menuUsuariosController;

    public UserItemController(MenuUsuariosController menuUsuariosController) {
        super();
        this.menuUsuariosController = menuUsuariosController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserView.fxml"));
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
                BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
                Node right = pb.getRight();
                TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
                menuTransition.setToX(0);
                UserDetailsController cont = ViewSwitcher.getUserDetailsController();
                cont.setMenuUsuariosController(menuUsuariosController);
                cont.initData(Objects.requireNonNull(usuario));
                menuTransition.play();
            }
        });
    }
}
