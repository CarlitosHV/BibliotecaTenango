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
                "jdbc:sqlserver://localhost\\SQLEXPRESS01:1433; Database=master; encrypt=true; trustServerCertificate=false; loginTimeout=30;";

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

    public void ConectarPostgres(){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/Biblioteca",
                            "postgres", "dwX48AogL8b");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
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
