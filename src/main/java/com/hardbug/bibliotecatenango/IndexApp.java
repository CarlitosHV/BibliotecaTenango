package com.hardbug.bibliotecatenango;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;

/*Clase principal que maneja el inicio de la aplicación y muestra el Stage*/

public class IndexApp extends Application {


    InputStream configInput = null;
    OutputStream configOutput = null;
    public static int TEMA;
    public static String servidor, usuario, contrasenia, base_datos;
    BDController bdController = new BDController();

    //Método que inicia la aplicación
    public static void main(String[] args) {
        launch(args);
    }

    //Método que comienza y manda a crear el stage
    @Override
    public void start(Stage stage) throws IOException {
        ObtenerPropiedades();
        bdController.ConectarPostgres();
        var scene = new Scene(new Pane());
        scene.getStylesheets().clear();
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.CRUD_LIBROS, TEMA);
        stage.setScene(scene);
        stage.getIcons().add(new Image(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")));
        stage.setTitle("Biblioteca Pública Municipal Lic. Abel C. Salazar");
        stage.setResizable(false);
        stage.show();
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
            config.store(configOutput, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
