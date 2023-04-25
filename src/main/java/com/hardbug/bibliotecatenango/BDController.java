package com.hardbug.bibliotecatenango;

import java.sql.*;
import java.util.ArrayList;

public class BDController {


    private Connection c = null;
    public static ArrayList<Object> datosLibro = new ArrayList<>();

    public boolean BorrarLibro(String Titulo_libro)throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                IndexApp.usuario, IndexApp.contrasenia);
             CallableStatement stmt = conn.prepareCall("{call eliminar_libro(?)}")) {

            stmt.setString(1, Titulo_libro);
            stmt.execute();

            // Verifica si el libro ha sido eliminado correctamente
            PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM LIBROS WHERE titulo_libro = ?");
            selectStmt.setString(1, Titulo_libro);
            ResultSet rs = selectStmt.executeQuery();
            boolean libroEliminado = !rs.next();

            rs.close();
            selectStmt.close();
            stmt.close();
            conn.close();
            return libroEliminado;
        } catch (SQLException e) {
            System.err.println("Error al ejecutar stored procedure: " + e.getMessage());
            return false;
        }
    }

    public boolean BuscarLibro(String Titulo_libro)throws SQLException {
        Boolean encontrado = false;
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM buscar_libro(?)");

            stmt.setString(1, Titulo_libro);


            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                encontrado = true;
                String clave_registro = rs.getString("clave_registro");
                String estante = rs.getString("estante");
                String descripcion_libro = rs.getString("descripcion_libro");
                int existencias = rs.getInt("existencias");
                String titulo_libro = rs.getString("titulo_libro");
                String anio_edicion = rs.getString("anio_edicion");
                String nombre_autor = rs.getString("nombre_autor");
                String clasificacion = rs.getString("clasificacion");
                String registro_clasificacion = rs.getString("registro_clasificacion");
                String editorial = rs.getString("editorial");
                String lugar_edicion = rs.getString("lugar_edicion");

                System.out.println(clave_registro + " | " + estante + " | " + descripcion_libro + " | " + existencias + " | " + titulo_libro + " | " + anio_edicion + " | " + nombre_autor + " | " + clasificacion + " | " + registro_clasificacion + " | " + editorial + " | " + lugar_edicion);
            }

            rs.close();
            stmt.close();
            conn.close();
            return encontrado;
        } catch (SQLException e) {
            System.err.println("Error al ejecutar stored procedure: " + e.getMessage());
            return encontrado;
        }
    }

    public boolean InsertarLibro(String Clave_registro, String Estante, String Descripcion_libro, int Existencias,
                              String Titulo_libro, String Anio_edicion, String Nombre_autor, String Clasificacion,
                              String Registro_clasificacion, String Editorial, String Lugar_edicion) throws SQLException {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                IndexApp.usuario, IndexApp.contrasenia);
             CallableStatement stmt = conn.prepareCall("{ call insertar_libro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }")) {

            stmt.setString(1, Clave_registro);
            stmt.setString(2, Estante);
            stmt.setString(3, Descripcion_libro);
            stmt.setInt(4, Existencias);
            stmt.setString(5, Titulo_libro);
            stmt.setString(6, Anio_edicion);
            stmt.setString(7, Nombre_autor);
            stmt.setString(8, Clasificacion);
            stmt.setString(9, Registro_clasificacion);
            stmt.setString(10, Editorial);
            stmt.setString(11, Lugar_edicion);
            // Ejecutar el stored procedure
            boolean executeResult = stmt.execute();

            conn.close();
            stmt.close();
            return executeResult;
        } catch (SQLException e) {
            System.err.println("Error al ejecutar stored procedure: " + e.getMessage());
            return false;
        }
    }

    public void EditarLibro(String Clave_registro, String Estante, String Descripcion_libro, int Existencias,
                              String Titulo_libro, String Anio_edicion, String Nombre_autor, String Clasificacion,
                              String Registro_clasificacion, String Editorial, String Lugar_edicion) throws SQLException {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                IndexApp.usuario, IndexApp.contrasenia);
             CallableStatement stmt = conn.prepareCall("{ call editar_libro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }")) {

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
