package com.hardbug.bibliotecatenango;

import java.sql.*;
import java.util.ArrayList;

public class BDController {

    public boolean BorrarLibro(String ClaveRegistro) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                IndexApp.usuario, IndexApp.contrasenia);
             CallableStatement stmt = conn.prepareCall("CALL spEliminarLibro(?)")) {

            stmt.setString(1, ClaveRegistro);
            stmt.execute();

            // Verifica si el libro ha sido eliminado correctamente
            PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM LIBROS WHERE clave_registro = ?");
            selectStmt.setString(1, ClaveRegistro);
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

    public ArrayList<ClaseLibro> TraerLibros (){
        ArrayList<ClaseLibro> _libros = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnseleccionartodoslibros()");

            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                ClaseLibro libro = new ClaseLibro();
                libro.setClave_registro(rs.getString("clave_registro"));
                libro.setEstante(rs.getString("Estante"));
                libro.setDescripcion_libro(rs.getString("descripcion_libro"));
                libro.setExistencias(rs.getInt("existencias"));
                libro.setTitulo_libro(rs.getString("titulo_libro"));
                libro.setAnio_edicion(rs.getString("anio_edicion"));
                libro.setNombre_autor(rs.getString("nombre_autor"));
                libro.setClasificacion(rs.getString("clasificacion"));
                libro.setRegistro_clasificacion(rs.getString("registro_clasificacion"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setLugar_edicion(rs.getString("lugar_edicion"));
                _libros.add(libro);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _libros;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ClaseLibro TraerLibro(String clave_registro) {
        ClaseLibro book = null;

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnBuscarLibroClave(?)");
            stmt.setString(1, clave_registro);

            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            if (rs.next()) {
                book = new ClaseLibro();
                book.setClave_registro(rs.getString("clave_registro"));
                book.setEstante(rs.getString("Estante"));
                book.setDescripcion_libro(rs.getString("descripcion_libro"));
                book.setExistencias(rs.getInt("existencias"));
                book.setTitulo_libro(rs.getString("titulo_libro"));
                book.setAnio_edicion(rs.getString("anio_edicion"));
                book.setNombre_autor(rs.getString("nombre_autor"));
                book.setClasificacion(rs.getString("clasificacion"));
                book.setRegistro_clasificacion(rs.getString("registro_clasificacion"));
                book.setEditorial(rs.getString("editorial"));
                book.setLugar_edicion(rs.getString("lugar_edicion"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return book;
    }


    public ArrayList<ClaseLibro> BusquedaGeneral (String palabra) throws SQLException{
        ArrayList<ClaseLibro> _libros = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnbusquedageneral(?)");
            stmt.setString(1, palabra);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                ClaseLibro libro = new ClaseLibro();
                libro.setClave_registro(rs.getString("clave_registro"));
                libro.setEstante(rs.getString("Estante"));
                libro.setDescripcion_libro(rs.getString("descripcion_libro"));
                libro.setExistencias(rs.getInt("existencias"));
                libro.setTitulo_libro(rs.getString("titulo_libro"));
                libro.setAnio_edicion(rs.getString("anio_edicion"));
                libro.setNombre_autor(rs.getString("nombre_autor"));
                libro.setClasificacion(rs.getString("clasificacion"));
                libro.setRegistro_clasificacion(rs.getString("registro_clasificacion"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setLugar_edicion(rs.getString("lugar_edicion"));
                _libros.add(libro);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _libros;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean InsertarLibro(String Clave_registro, String Estante, String Descripcion_libro, int Existencias,
                                 String Titulo_libro, String Anio_edicion, String Nombre_autor, String Clasificacion,
                                 String Registro_clasificacion, String Editorial, String Lugar_edicion) throws SQLException {

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                IndexApp.usuario, IndexApp.contrasenia);
             CallableStatement stmt = conn.prepareCall("CALL spInsertarLibro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

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
            stmt.execute();

            conn.close();
            stmt.close();
            return true;
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
             CallableStatement stmt = conn.prepareCall("CALL spEditarLibro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

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
            stmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println("Error al ejecutar stored procedure: " + e.getMessage());
            return false;
        }
    }
}
