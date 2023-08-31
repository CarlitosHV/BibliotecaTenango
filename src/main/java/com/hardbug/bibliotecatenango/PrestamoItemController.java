package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Prestamo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PrestamoItemController extends ListCell<Prestamo> {
    private FXMLLoader fxmlLoader;
    private AnchorPane fondoItem;
    private Label LabelNombre, LabelCURP, LabelInicio, LabelLibros, LabelFin;
    private EventHandler<ActionEvent> onItemSelected;
    BDController bd = new BDController();
    private boolean isMenuOpen = false;

    public EventHandler<ActionEvent> getOnItemSelected() {
        return onItemSelected;
    }

    public void setOnItemSelected(EventHandler<ActionEvent> onItemSelected) {
        this.onItemSelected = onItemSelected;
    }


    public PrestamoItemController() {
        super();
        fxmlLoader = new FXMLLoader(getClass().getResource("PrestamoItemView.fxml"));
        try {
            fondoItem = fxmlLoader.load();
            LabelFin = (Label) fxmlLoader.getNamespace().get("LabelFin");
            LabelLibros = (Label) fxmlLoader.getNamespace().get("LabelLibros");
            LabelInicio = (Label) fxmlLoader.getNamespace().get("LabelInicio");
            LabelCURP = (Label) fxmlLoader.getNamespace().get("LabelCURP");
            LabelNombre = (Label) fxmlLoader.getNamespace().get("LabelNombre");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Prestamo prestamo, boolean Empty) {
        super.updateItem(prestamo, Empty);
        if (Empty || prestamo == null) {
            setGraphic(null);
        } else {
            LabelInicio.setText("Fecha préstamo: " + Fechas.obtenerFechaInicio(prestamo.FechaInicio));
            LabelLibros.setText("Libros solicitados: " + prestamo.libros.size());
            LabelFin.setText("Fecha devolución: " + Fechas.obtenerFechaDevolucion(prestamo.FechaFin));
            LabelCURP.setText("CURP: " + prestamo.Usuario.getCurp());
            LabelNombre.setText("Usuario: " + prestamo.Usuario.nombre.GetNombreCompleto());
            setGraphic(fondoItem);
        }
    }
}
