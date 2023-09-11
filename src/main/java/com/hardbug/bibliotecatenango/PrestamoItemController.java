package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Prestamo;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PrestamoItemController extends ListCell<Prestamo> {
    private AnchorPane fondoItem;
    private final Label LabelNombre, LabelCURP, LabelInicio, LabelLibros, LabelFin;


    public PrestamoItemController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PrestamoItemView.fxml"));
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
            if (prestamo.FechaFin.compareTo(Fechas.obtenerFechaActual()) < 0) {
                fondoItem.setStyle("-fx-background-color: #ff0000");
            }
            setGraphic(fondoItem);
        }
    }
}
