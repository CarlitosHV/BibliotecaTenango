package com.hardbug.bibliotecatenango;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

/*Clase principal que maneja el inicio de la aplicación y muestra el Stage*/

public class IndexApp extends Application {
    InputStream configInput = null;
    OutputStream configOutput = null;
    public static int TEMA;
    public static String servidor, usuario, contrasenia, base_datos, key, correo, password_email, username_email;
    //Método que inicia la aplicación
    public static void main(String[] args) {
        launch(args);
    }

    //Método que comienza y manda a crear el stage
    @Override
    public void start(Stage stage) {
        ObtenerPropiedades();
        var scene = new Scene(new Pane());
        scene.getStylesheets().clear();
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.MENU_PRINCIPAL, TEMA);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png"))));
        stage.setTitle("Biblioteca Pública Municipal Lic. Abel C. Salazar");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setMaximized(true);
        stage.setOnCloseRequest(evt -> System.exit(0));
        stage.show();
    }

    public void ObtenerPropiedades() {
        try {
            Properties config = new Properties();
            String path = new File(".").getCanonicalPath();
            configInput = new FileInputStream(path + "/config.properties");
            if (configInput != null) {
                config.load(configInput);
            } else {
                throw new FileNotFoundException("Archivo de propiedades no encontrado");
            }
            TEMA = Integer.parseInt(config.getProperty("theme"));
            servidor = config.getProperty("server");
            usuario = config.getProperty("user");
            contrasenia = config.getProperty("password");
            base_datos = config.getProperty("database");
            key = config.getProperty("key");
            correo = config.getProperty("email");
            password_email = config.getProperty("password_email");
            username_email = config.getProperty("username_email");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void EscribirPropiedades(String property, String value) {
        try {
            String path = new File(".").getCanonicalPath();
            configOutput = new FileOutputStream(path + "/config.properties");
            Properties config = new Properties();
            config.setProperty(property, value);
            config.setProperty("server", servidor);
            config.setProperty("user", usuario);
            config.setProperty("password", contrasenia);
            config.setProperty("database", base_datos);
            config.setProperty("key", key);
            config.setProperty("email", correo);
            config.setProperty("password_email", password_email);
            config.setProperty("username_email", username_email);
            config.store(configOutput, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
