package com.hardbug.bibliotecatenango;

import java.sql.Connection;
import java.sql.DriverManager;

public class BDController {


    private Connection c = null;

    public void ConectarPostgres(){
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                            IndexApp.usuario, IndexApp.contrasenia);


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
