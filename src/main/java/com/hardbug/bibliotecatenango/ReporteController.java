package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.GenerarPdf;
import com.hardbug.bibliotecatenango.Models.Reporte;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ReporteController extends BDController implements Initializable {

    @FXML
    private Button BotonDescargar;
    @FXML
    private DatePicker DateFechaFin, DateFechaInicio;
    @FXML
    private ProgressIndicator IconoCarga;
    Alert alert;

    Reporte reporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IconoCarga.setVisible(false);
        IconoCarga.setProgress(-1.0);
        DateFechaInicio.setValue(LocalDate.now());
        DateFechaFin.setValue(LocalDate.now().plus(30, java.time.temporal.ChronoUnit.DAYS));

        DateFechaInicio.setOnAction(event -> {
            LocalDate fechaInicio = DateFechaInicio.getValue();
            LocalDate fechaFin = DateFechaFin.getValue();

            if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
                alert = new Alertas().CrearAlertaError("Error", "La fecha de inicio no puede ser mayor a la fecha de fin");
                alert.showAndWait();
                DateFechaInicio.setValue(LocalDate.now());
                DateFechaFin.setValue(LocalDate.now().plus(30, java.time.temporal.ChronoUnit.DAYS));
            }
        });

        DateFechaFin.setOnAction(event -> {
            LocalDate fechaInicio = DateFechaInicio.getValue();
            LocalDate fechaFin = DateFechaFin.getValue();

            if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
                alert = new Alertas().CrearAlertaError("Error", "La fecha de inicio no puede ser mayor a la fecha de fin");
                alert.showAndWait();
                DateFechaInicio.setValue(LocalDate.now());
                DateFechaFin.setValue(LocalDate.now().plus(30, java.time.temporal.ChronoUnit.DAYS));
            }
        });

        BotonDescargar.setOnAction(evt -> {
            if (DateFechaInicio.getValue() == null || DateFechaFin.getValue() == null) {
                alert = new Alertas().CrearAlertaError("Error", "Debe seleccionar un rango de fechas");
                alert.showAndWait();
            } else {
                Date fechaInicio = Date.valueOf(DateFechaInicio.getValue());
                Date fechaFin = Date.valueOf(DateFechaFin.getValue());
                Callable<Reporte> miCallable = () -> {
                    Platform.runLater(() -> {
                        IconoCarga.setVisible(true);
                        IconoCarga.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                    });
                    Thread.sleep(2000);
                    return GenerarReporte(fechaInicio, fechaFin);
                };

                FutureTask<Reporte> futureTask = new FutureTask<>(miCallable);
                Thread thread = new Thread(futureTask);
                thread.start();
                try {
                    reporte = futureTask.get();

                    Platform.runLater(() -> {
                        IconoCarga.setVisible(false);
                        IconoCarga.setProgress(0);
                        GenerarPdf pdf = new GenerarPdf();
                        pdf.GenerarReporte(Fechas.obtenerFechaActual(),reporte);
                        if (reporte != null) {
                            alert = new Alertas().CrearAlertaInformativa("Reporte", "Reporte generado con éxito");
                            alert.showAndWait();
                        } else {
                            alert = new Alertas().CrearAlertaError("Error", "No se pudo generar el reporte");
                            alert.showAndWait();
                        }
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

