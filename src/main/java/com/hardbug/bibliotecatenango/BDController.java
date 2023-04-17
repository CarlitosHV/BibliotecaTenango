package com.hardbug.bibliotecatenango;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BDController {

    private String servidor;
    private String usuario;
    private String contrasenia;
    private String base_datos;
    public void ConectarBD() {
        ObtenerBD();
        String connectionUrl =
                "jdbc:sqlserver://" + servidor + ";"
                        + "database=" + base_datos + ";"
                        + "user=" + usuario + ";"
                        + "password=" + contrasenia + ";"
                        + "encrypt=true;"
                        + "loginTimeout=30;"
                        + "integratedSecurity=true;";

        try (Connection connection = DriverManager.getConnection(connectionUrl);) {
            if(connection.isValid(500)){
                System.out.println("Conexión exitosa");
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ObtenerBD() {
        try {
            Properties bd = new Properties();
            InputStream configInput = new FileInputStream("src/main/resources/bd.properties");
            bd.load(configInput);
            base_datos = bd.getProperty("database");
            usuario = bd.getProperty("user");
            contrasenia = bd.getProperty("password");
            servidor = bd.getProperty("server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
