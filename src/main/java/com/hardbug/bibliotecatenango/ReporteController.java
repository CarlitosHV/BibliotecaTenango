package com.hardbug.bibliotecatenango;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class ReporteController extends BDController implements Initializable {

    @FXML
    private Button BotonDescargar;
    @FXML
    private DatePicker DateFechaFin, DateFechaInicio;
    @FXML
    private ProgressIndicator IconoCarga;
    Alert alert;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IconoCarga.setVisible(false);
        IconoCarga.setProgress(-1.0);
        DateFechaInicio.setOnAction(event -> {
            LocalDate fechaInicio = DateFechaInicio.getValue();
            LocalDate fechaFin = DateFechaFin.getValue();

            if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
                alert = new Alertas().CrearAlertaError("Error", "La fecha de inicio no puede ser mayor a la fecha de fin");
                alert.showAndWait();
                DateFechaFin.setValue(null);
                DateFechaInicio.setValue(null);
            }
        });

        DateFechaFin.setOnAction(event -> {
            LocalDate fechaInicio = DateFechaInicio.getValue();
            LocalDate fechaFin = DateFechaFin.getValue();

            if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
                alert = new Alertas().CrearAlertaError("Error", "La fecha de inicio no puede ser mayor a la fecha de fin");
                alert.showAndWait();
                DateFechaFin.setValue(null);
                DateFechaInicio.setValue(null);
            }
        });

        BotonDescargar.setOnAction(evt -> {
            if(DateFechaInicio.getValue() == null || DateFechaFin.getValue() == null) {
                alert = new Alertas().CrearAlertaError("Error", "Debe seleccionar un rango de fechas");
                alert.showAndWait();
            }else{

            }
        });
    }
}
