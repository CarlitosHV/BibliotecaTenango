package com.hardbug.bibliotecatenango;

import java.sql.*;

public class BDController {


    private Connection c = null;

    public void ConectarPostgres() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                        .getConnection("jdbc:postgresql://" + IndexApp.servidor + "/"
                                + IndexApp.base_datos, IndexApp.usuario, IndexApp.contrasenia);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void InsertarLibro(String Clave_registro, String Estante, String Descripcion_libro, int Existencias,
                              String Titulo_libro, String Anio_edicion, String Nombre_autor, String Clasificacion,
                              String Registro_clasificacion, String Editorial, String Lugar_edicion) throws SQLException {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                IndexApp.usuario, IndexApp.contrasenia);
             CallableStatement stmt = conn.prepareCall("{ call insertar_libro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }")) {

            // Setear los parámetros del stored procedure
            stmt.setString(1, Clave_registro); // p_clave_registro
            stmt.setString(2, Estante); // p_estante
            stmt.setString(3, Descripcion_libro); // p_descripcion_libro
            stmt.setInt(4, Existencias); // p_existencias
            stmt.setString(5, Titulo_libro); // p_titulo_libro
            stmt.setString(6, Anio_edicion); // p_anio_edicion
            stmt.setString(7, Nombre_autor); // p_nombre_autor
            stmt.setString(8, Clasificacion); // p_nombre_clasificacion
            stmt.setString(9, Registro_clasificacion); // p_nombre_registro_clasificacion
            stmt.setString(10, Editorial); // p_nombre_editorial
            stmt.setString(11, Lugar_edicion); // p_nombre_lugar_edicion

            // Ejecutar el stored procedure
            stmt.execute();

            System.out.println("Stored procedure insertar_libro ejecutado correctamente");
        } catch (SQLException e) {
            System.err.println("Error al ejecutar stored procedure: " + e.getMessage());
        }
    }
}
