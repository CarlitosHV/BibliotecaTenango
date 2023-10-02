package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.GenerarPdf;
import com.hardbug.bibliotecatenango.Models.Reporte;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*Clase principal que maneja el inicio de la aplicación y muestra el Stage*/

public class IndexApp extends Application {
    InputStream configInput = null;
    OutputStream configOutput = null;
    public static int TEMA;
    public static String servidor, usuario, contrasenia, base_datos, key, correo;
    Reporte reporte;
    //Método que inicia la aplicación
    public static void main(String[] args) {
        launch(args);
    }

    //Método que comienza y manda a crear el stage
    @Override
    public void start(Stage stage) {
        ObtenerPropiedades();
        /*var scene = new Scene(new Pane());
        scene.getStylesheets().clear();
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.MENU_PRINCIPAL, TEMA);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png"))));
        stage.setTitle("Biblioteca Pública Municipal Lic. Abel C. Salazar");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setMaximized(true);
        stage.setOnCloseRequest(evt -> {
            System.exit(0);
        });
        stage.show();*/
        BDController bd = new BDController();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Date startDate = Date.valueOf(year + "-01-01");
        Date endDate = Date.valueOf(year + "-12-31");

        Callable<Reporte> miCallable = () -> {
            Platform.runLater(() -> {
            });
            Thread.sleep(2000);
            return bd.GenerarReporte(startDate, endDate);
        };

        FutureTask<Reporte> futureTask = new FutureTask<>(miCallable);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            reporte = futureTask.get();

            Platform.runLater(() -> {
                GenerarPdf pdf = new GenerarPdf();
                pdf.GenerarReporte(Fechas.obtenerFechaActual(),reporte);
                if (reporte != null) {
                    System.out.println("Creado");
                } else {
                    System.out.println("Error");
                }
                System.exit(0);
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void ObtenerPropiedades() {
        try {
            Properties config = new Properties();
            configInput = new FileInputStream("src/main/resources/config.properties");
            config.load(configInput);
            TEMA = Integer.parseInt(config.getProperty("theme"));
            servidor = config.getProperty("server");
            usuario = config.getProperty("user");
            contrasenia = config.getProperty("password");
            base_datos = config.getProperty("database");
            key = config.getProperty("key");
            correo = config.getProperty("email");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EscribirPropiedades(String property, String value) {
        try {
            configOutput = new FileOutputStream("src/main/resources/config.properties");
            Properties config = new Properties();
            config.setProperty(property, value);
            config.setProperty("server", servidor);
            config.setProperty("user", usuario);
            config.setProperty("password", contrasenia);
            config.setProperty("database", base_datos);
            config.setProperty("key", key);
            config.setProperty("email", correo);
            config.store(configOutput, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
