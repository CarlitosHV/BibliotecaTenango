package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ComboUser  extends ListCell<Usuario> {
    private final AnchorPane fondoItem;
    private final Label LabelNombre, LabelCurp, LabelCorreo;

    public ComboUser() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ComboUser.fxml"));
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
