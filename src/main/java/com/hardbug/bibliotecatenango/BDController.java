package com.hardbug.bibliotecatenango;

import java.sql.*;
import java.util.ArrayList;

public class BDController {


    private Connection c = null;
    ClaseLibro libro = new ClaseLibro();

    public boolean BorrarLibro(String Titulo_libro) throws SQLException {
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

    public boolean BuscarLibro(String Titulo_libro) throws SQLException {
        Boolean encontrado = false;
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM buscar_libro(?)");

            stmt.setString(1, Titulo_libro);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                encontrado = true;
                AltaLibrosController.datosLibro.clear();
                AltaLibrosController.datosLibro.add(rs.getString("clave_registro"));
                AltaLibrosController.datosLibro.add(rs.getString("clasificacion"));
                AltaLibrosController.datosLibro.add(rs.getString("anio_edicion"));
                AltaLibrosController.datosLibro.add(rs.getString("registro_clasificacion"));
                AltaLibrosController.datosLibro.add(rs.getString("estante"));
                AltaLibrosController.datosLibro.add(rs.getInt("existencias"));
                AltaLibrosController.datosLibro.add(rs.getString("editorial"));
                AltaLibrosController.datosLibro.add(rs.getString("lugar_edicion"));
                AltaLibrosController.datosLibro.add(rs.getString("titulo_libro"));
                AltaLibrosController.datosLibro.add(rs.getString("nombre_autor"));
                AltaLibrosController.datosLibro.add(rs.getString("descripcion_libro"));
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

    public boolean EditarLibro(String Clave_registro, String Estante, String Descripcion_libro, int Existencias,
                            String Titulo_libro, String Anio_edicion, String Nombre_autor, String Clasificacion,
                            String Registro_clasificacion, String Editorial, String Lugar_edicion) throws SQLException {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                IndexApp.usuario, IndexApp.contrasenia);
             CallableStatement stmt = conn.prepareCall("{ call editar_libro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }")) {

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
            return  false;
        }
    }
}
