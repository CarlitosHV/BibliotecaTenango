package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.*;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringJoiner;

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

    public ArrayList<Libro> TraerLibros (){
        ArrayList<Libro> _libros = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnseleccionartodoslibros()");

            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Libro libro = new Libro();
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

    public Libro TraerLibro(String clave_registro) {
        Libro book = null;

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnBuscarLibroClave(?)");
            stmt.setString(1, clave_registro);

            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            if (rs.next()) {
                book = new Libro();
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


    public boolean InsertarLibro(String Clave_registro, String Estante, String Descripcion_libro, int Existencias,
                                 String Titulo_libro, String Anio_edicion, String Nombre_autor, String Clasificacion,
                                 String Registro_clasificacion, String Editorial, String Lugar_edicion) {

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
                               String Registro_clasificacion, String Editorial, String Lugar_edicion) {
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

    public ArrayList<Estados> BuscarEstados () {
        ArrayList<Estados> _estados = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnSeleccionarEstados()");
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Estados estado = new Estados();
                estado.setId(rs.getInt("id_estado"));
                estado.setEstado(rs.getString("nombre"));
                _estados.add(estado);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _estados;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Municipios> BuscarMunicipios (String Estado) {
        ArrayList<Municipios> _municipios = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnseleccionarmunicipiosporestado(?)");
            stmt.setString(1, Estado);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Municipios municipios = new Municipios();
                municipios.setId(rs.getInt("id_municipio"));
                municipios.setMunicipio(rs.getString("nombre"));
                municipios.setEstado(rs.getString("estado"));
                _municipios.add(municipios);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _municipios;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Localidad> BuscarLocalidades (String Municipio, String Estado) {
        ArrayList<Localidad> _localidades = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnSeleccionarLocalidadesPorMunicipio(?, ?)");
            stmt.setString(1, Municipio);
            stmt.setString(2, Estado);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Localidad localidad = new Localidad();
                localidad.setId(rs.getInt("id_localidad"));
                localidad.setLocalidad(rs.getString("localidad"));
                localidad.setCP(rs.getInt("cp"));
                _localidades.add(localidad);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _localidades;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Localidad> BuscarCodigoPostal (int cp) {
        ArrayList<Localidad> _localidades = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnseleccionarlocalidadporcp(?)");
            stmt.setInt(1, cp);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Localidad localidad = new Localidad();
                localidad.setId(rs.getInt("id_localidad"));
                localidad.setLocalidad(rs.getString("localidad"));
                localidad.IdMunicipio = (rs.getInt("id_municipio"));
                localidad.setMunicipio(rs.getString("municipio"));
                localidad.IdEstado = rs.getInt("id_estado");
                localidad.setEstado(rs.getString("estado"));
                localidad.setCP(cp);
                _localidades.add(localidad);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _localidades;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Catalogo> ConsultarGradosEscolares(Boolean InsertBlank){
        ArrayList<Catalogo> _grados = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnseleccionartodosgrados()");
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            if (InsertBlank){
                _grados.add(new Catalogo("Selecciona un grado"));
            }
            while (rs.next()) {
                Catalogo catalogo = new Catalogo();
                catalogo.setId(rs.getInt("id"));
                catalogo.setNombre(rs.getString("nombre"));
                _grados.add(catalogo);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _grados;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Catalogo> ConsultarOcupaciones(Boolean InsertBlank){
        ArrayList<Catalogo> _ocupaciones = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnseleccionartodasocupaciones()");
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            if (InsertBlank){
                _ocupaciones.add(new Catalogo("Selecciona ocupación"));
            }
            while (rs.next()) {
                Catalogo catalogo = new Catalogo();
                catalogo.setId(rs.getInt("id_ocupacion"));
                catalogo.setNombre(rs.getString("nombre"));
                _ocupaciones.add(catalogo);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _ocupaciones;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean InsertarEditarOcupacion(Catalogo Ocupacion){
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("call spInsertarActualizarOcupacion(?,?)");
            stmt.setInt(1, Ocupacion.getId());
            stmt.setString(2, Ocupacion.getNombre());
            stmt.execute();

            stmt.close();
            conn.close();
            return true;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean EliminarOcupacion(Catalogo Ocupacion){
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("call spEliminarOcupacion(?)");
            stmt.setInt(1, Ocupacion.getId());
            stmt.execute();

            stmt.close();
            conn.close();
            return true;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean InsertarActualizarUsuario(Usuario mUsuario) throws Exception {
        int IdNombre = 0;
        int IdDir = 0;
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fninsertaractualizarnombre(?,?,?,?)");
            stmt.setInt(1, mUsuario.nombre.IdNombre);
            stmt.setString(2, mUsuario.nombre.Nombre);
            stmt.setString(3, mUsuario.nombre.ApellidoPaterno);
            stmt.setString(4, mUsuario.nombre.ApellidoMaterno);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                IdNombre = rs.getInt("fninsertaractualizarnombre");
            }

            if (IdNombre > 0){
                stmt.close();
                mUsuario.nombre.IdNombre = IdNombre;
                PreparedStatement stmtDir = conn.prepareStatement("select * from fninsertaractualizardireccion(?,?,?,?,?,?)");
                stmtDir.setInt(1, mUsuario.direccion.IdDireccion);
                stmtDir.setString(2, mUsuario.direccion.Calle);
                stmtDir.setString(3, mUsuario.direccion.CP);
                stmtDir.setInt(4, mUsuario.direccion.IdMunicipio);
                stmtDir.setInt(5, mUsuario.direccion.IdEstado);
                stmtDir.setInt(6, mUsuario.direccion.IdLocalidad);
                stmtDir.execute();
                ResultSet rsDir = stmtDir.getResultSet();
                while (rsDir.next()){
                    IdDir = rsDir.getInt("fninsertaractualizardireccion");
                }

                if (IdDir > 0){
                    stmtDir.close();
                    mUsuario.direccion.IdDireccion = IdDir;
                    PreparedStatement stmtUser = conn.prepareStatement("call spInsertarActualizarUsuario(?,?,?,?,?,?,?,?,?,?,?,?)");
                    stmtUser.setInt(1, mUsuario.IdUsuario);
                    stmtUser.setLong(2, mUsuario.Telefono.longValueExact());
                    stmtUser.setInt(3, mUsuario.getEdad());
                    stmtUser.setString(4, mUsuario.sexo);
                    stmtUser.setString(5, mUsuario.Curp);
                    stmtUser.setString(6, mUsuario.Contrasenia);
                    stmtUser.setInt(7, mUsuario.GradoEscolar.getId());
                    stmtUser.setString(8, mUsuario.getCorreo());
                    stmtUser.setInt(9, mUsuario.nombre.IdNombre);
                    stmtUser.setInt(10, mUsuario.IdTipoUsuario);
                    stmtUser.setInt(11, mUsuario.Ocupacion.getId());
                    stmtUser.setInt(12, mUsuario.direccion.IdDireccion);
                    stmtUser.execute();
                    stmtUser.close();
                    conn.close();
                    if (mUsuario.IdUsuario == 0){
                        String mensaje = String.format("""
                                <div style="font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; max-width: 600px;">
                                    <h2 style="color: #2F4F4F;">¡Bienvenido, %s!</h2>
                                    <p style="font-size: 1.2em;">
                                        Tu correo ha sido registrado en el sistema de <strong>Biblioteca Pública Municipal Lic. Abel C. Salazar</strong>.
                                        ¡Ahora puedes solicitar libros con tu cuenta al proporcionar tu CURP!
                                    </p>
                                    <p style="font-size: 1.2em;">
                                        Dudas o sugerencias al correo: <a href="mailto:direccion.educacion@tenangodelvalle.gob.mx">direccion.educacion@tenangodelvalle.gob.mx</a>
                                    </p>
                                    <p style="color: #696969;">
                                        *Este mensaje ha sido generado automáticamente*
                                    </p>
                                    <p style="color: #696969;">
                                        Biblioteca Pública Municipal Lic. Abel C. Salazar<br>
                                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                    </p>
                                </div>
                            """, mUsuario.getNombre());
                        new EmailSender().emailSender("Registro de cuenta", mUsuario.getCorreo(), mensaje);
                    }else{
                        String mensaje = String.format("""
                                <div style="font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; max-width: 600px;">
                                    <h2 style="color: #2F4F4F;">Tu cuenta ha sido actualizada, %s</h2>
                                    <p style="font-size: 1.2em;">
                                        Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                        mandando un correo a la dirección: <a href="mailto:direccion.educacion@tenangodelvalle.gob.mx">direccion.educacion@tenangodelvalle.gob.mx</a>
                                        De lo contrario, haz caso omiso a este mensaje
                                    </p>
                                    <p style="color: #696969;">
                                        *Este mensaje ha sido generado automáticamente*
                                    </p>
                                    <p style="color: #696969;">
                                        Biblioteca Pública Municipal Lic. Abel C. Salazar<br>
                                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                    </p>
                                </div>
                            """, mUsuario.getNombre());
                        new EmailSender().emailSender("Actualización de cuenta", mUsuario.getCorreo(), mensaje);
                    }
                    return true;
                }else{
                    conn.close();
                    return false;
                }
            }else{
                conn.close();
                return false;
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<Usuario> MostrarTodosUsuarios(){
        ArrayList<Usuario> _usuarios = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fnseleccionartodosusuarios()");
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.IdUsuario = rs.getInt("id_usuario");
                usuario.setTelefono(new BigInteger(String.valueOf(rs.getLong("telefono"))));
                usuario.setEdad(rs.getInt("edad"));
                usuario.sexo = rs.getString("sexo");
                usuario.setCurp(rs.getString("curp"));
                usuario.Contrasenia = ClaseCifrarContrasenia.decrypt(rs.getString("contrasenia"));
                usuario.setGradoEscolar(new Catalogo(rs.getInt("id_grado_escolar"), rs.getString("grado_escolar")));
                usuario.setCorreo(rs.getString("correo"));
                Nombres nombre = new Nombres();
                nombre.IdNombre = rs.getInt("id_nombre");
                nombre.Nombre = rs.getString("nombre");
                nombre.ApellidoPaterno = rs.getString("apellido_paterno");
                nombre.ApellidoMaterno = rs.getString("apellido_materno");
                usuario.nombre = nombre;
                usuario.TipoUsuario = new Catalogo(rs.getInt("id_tipo_usuario"), rs.getString("tipo_usuario"));
                usuario.Ocupacion = new Catalogo(rs.getInt("id_ocupacion"), rs.getString("ocupacion"));
                Direccion direccion = new Direccion();
                direccion.IdDireccion = rs.getInt("id_direccion");
                direccion.setCalle(rs.getString("calle"));
                direccion.setCP(rs.getString("codigo_postal"));
                direccion.IdMunicipio = rs.getInt("id_municipio");
                direccion.Municipio = rs.getString("municipio");
                direccion.IdEstado = rs.getInt("id_estado");
                direccion.Estado = rs.getString("estado");
                direccion.IdLocalidad = rs.getInt("id_localidad");
                direccion.Localidad = rs.getString("localidad");
                usuario.direccion = direccion;
                _usuarios.add(usuario);
            }

            stmt.close();
            conn.close();
            return _usuarios;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean EliminarUsuario(Usuario usuario, String Correo){
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("call spEliminarUsuario(?)");
            stmt.setInt(1, usuario.IdUsuario);
            stmt.execute();
            stmt.close();

            PreparedStatement statement = conn.prepareStatement("select * from fnseleccionartodosusuariosporid(?)");
            statement.setInt(1, usuario.IdUsuario);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()){
                conn.close();
                return false;
            }else{
                conn.close();
                String mensaje = String.format("""
                                <div style="font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; max-width: 600px;">
                                    <h2 style="color: #2F4F4F;">Hola, %s</h2>
                                    <p style="font-size: 1.2em;">
                                        Tu cuenta ha sido eliminada del sistema. Esperamos verte pronto :(
                                        Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                        mandando un correo a la dirección: <a href="mailto:direccion.educacion@tenangodelvalle.gob.mx">direccion.educacion@tenangodelvalle.gob.mx</a>
                                        De lo contrario, haz caso omiso a este mensaje
                                    </p>
                                    <p style="color: #696969;">
                                        *Este mensaje ha sido generado automáticamente*
                                    </p>
                                    <p style="color: #696969;">
                                        Biblioteca Pública Municipal Lic. Abel C. Salazar<br>
                                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                    </p>
                                </div>
                            """, usuario.getNombre());
                new EmailSender().emailSender("Cuenta eliminada del sistema", Correo, mensaje);
                return true;
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Catalogo> ConsultarDocumentos(Boolean InsertBlank){
        ArrayList<Catalogo> _documentos = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnSeleccionarTodosDocumentos()");
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            if (InsertBlank){
                _documentos.add(new Catalogo("Selecciona un documento"));
            }
            while (rs.next()) {
                Catalogo catalogo = new Catalogo();
                catalogo.setId(rs.getInt("id_documento"));
                catalogo.setNombre(rs.getString("nombre"));
                _documentos.add(catalogo);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _documentos;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public int InsertarActualizarPrestamo(Prestamo mPrestamo) throws Exception {
        int IdPrestamo = 0;
        StringJoiner joiner = new StringJoiner(",");
        for (Libro libro : mPrestamo.libros) {
            joiner.add(libro.getClave_registro());
        }
        String LibrosConcat = joiner.toString();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fninsertaractualizarprestamo(?,?,?,?,?,?,?)");
            stmt.setInt(1, mPrestamo.IdPrestamo);
            stmt.setInt(2, mPrestamo.Usuario.IdUsuario);
            stmt.setDate(3, mPrestamo.FechaInicio);
            stmt.setDate(4, mPrestamo.FechaFin);
            stmt.setInt(5, 0);
            stmt.setString(6, "");
            stmt.setInt(7, mPrestamo.Documento.getId());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                IdPrestamo = rs.getInt("fninsertaractualizarprestamo");
            }

            if(IdPrestamo == -2){
                return -2;
            }

            if (IdPrestamo > 0){
                stmt.close();
                mPrestamo.IdPrestamo = IdPrestamo;
                PreparedStatement stmtDir = conn.prepareStatement("call spInsertarLibrosPrestamo(?,?)");
                stmtDir.setInt(1, mPrestamo.IdPrestamo);
                stmtDir.setString(2, LibrosConcat);
                stmtDir.execute();
                String mensaje = String.format("""
                                <div style="font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; max-width: 600px;">
                                    <h2 style="color: #2F4F4F;">¡Se ha generado tu préstamo, %s!</h2>
                                    <p style="font-size: 1.2em;">
                                        Tu fecha de préstamo es: %s<br>
                                        Tu fecha de devolución es: %s<br>
                                        Es necesario que cumplas con tu fecha de devolución o si necesitas más tiempo, antes del vencimiento puedes extender el periodo
                                        solicitándolo en la biblioteca.
                                    </p>
                                    <p style="font-size: 1.2em;">
                                        Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                        mandando un correo a la dirección: <a href="mailto:direccion.educacion@tenangodelvalle.gob.mx">direccion.educacion@tenangodelvalle.gob.mx</a>
                                        De lo contrario, haz caso omiso a este mensaje
                                    </p>
                                    <p style="color: #696969;">
                                        *Este mensaje ha sido generado automáticamente*
                                    </p>
                                    <p style="color: #696969;">
                                        Biblioteca Pública Municipal Lic. Abel C. Salazar<br>
                                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                    </p>
                                </div>
                            """, mPrestamo.Usuario.nombre.Nombre, Fechas.obtenerFechaInicio(mPrestamo.FechaInicio),
                        Fechas.obtenerFechaDevolucion(mPrestamo.FechaFin));
                new EmailSender().emailSender("Préstamo generado", mPrestamo.Usuario.Correo, mensaje);
                return 0;
            }else{
                conn.close();
                return -1;
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
    public boolean InsertarVisitante(Visitante miVisitante) {
        int IdNombre = 0;
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fninsertaractualizarnombre(?,?,?,?)");
            stmt.setInt(1, miVisitante.nombre.IdNombre);
            stmt.setString(2, miVisitante.nombre.Nombre);
            stmt.setString(3, miVisitante.nombre.ApellidoPaterno);
            stmt.setString(4, miVisitante.nombre.ApellidoMaterno);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                IdNombre = rs.getInt("fninsertaractualizarnombre");
            }

            if (IdNombre > 0){
                stmt.close();
                miVisitante.nombre.IdNombre = IdNombre;
                PreparedStatement stmtVisita = conn.prepareStatement("call spinsertarregistrarvisitante(?,?,?,?,?,?,?,?)");
                stmtVisita.setInt(1,miVisitante.edad);
                stmtVisita.setInt(2,miVisitante.grado_escolar.getId());
                stmtVisita.setBoolean(3,miVisitante.discapacidad);
                stmtVisita.setInt(4,miVisitante.nombre.IdNombre);
                stmtVisita.setDate(5,miVisitante.fecha);
                stmtVisita.setInt(6,miVisitante.ocupacion.getId());
                stmtVisita.setInt(7,miVisitante.Actividad.getId());
                stmtVisita.setString(8,miVisitante.sexo);
                stmtVisita.execute();
                stmtVisita.close();
                conn.close();
                return true;
            }else{
                conn.close();
                return false;
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean InsertarEditarActividad(Catalogo Actividad){
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("call spInsertarActualizarActividad(?,?)");
            stmt.setInt(1, Actividad.getId());
            stmt.setString(2, Actividad.getNombre());
            stmt.execute();

            stmt.close();
            conn.close();
            return true;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean EliminarActividad(Catalogo Actividad){
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("call spEliminarActividad(?)");
            stmt.setInt(1, Actividad.getId());
            stmt.execute();

            stmt.close();
            conn.close();
            return true;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<Catalogo> ConsultarActividades(Boolean InsertBlank){
        ArrayList<Catalogo> _actividades = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnSeleccionarTodasActividades()");
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            if (InsertBlank){
                _actividades.add(new Catalogo("Selecciona actividad"));
            }
            while (rs.next()) {
                Catalogo catalogo = new Catalogo();
                catalogo.setId(rs.getInt("id_actividad"));
                catalogo.setNombre(rs.getString("nombre"));
                _actividades.add(catalogo);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _actividades;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Prestamo> ConsultarPrestamos(){
        ArrayList<Prestamo> _prestamos = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnSeleccionarTodosPrestamos()");
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.IdPrestamo = rs.getInt("id_prestamo");
                prestamo.Usuario =  new Usuario(rs.getInt("id_usuario"));
                prestamo.FechaInicio = rs.getDate("fecha_prestamo");
                prestamo.FechaFin = rs.getDate("fecha_devolucion");
                prestamo.Renovaciones = rs.getInt("renovaciones");
                prestamo.ComentarioAtraso = rs.getString("comentario_atraso") != null ? rs.getString("comentario_atraso") : "";
                prestamo.Documento = new Catalogo(rs.getInt("id_tipo_documento"));
                PreparedStatement stmtU = conn.prepareStatement("SELECT * FROM fnSeleccionarTodosLibrosPrestamos(?)");
                stmtU.setInt(1, prestamo.IdPrestamo);
                stmtU.execute();
                ResultSet rrs = stmtU.getResultSet();
                ArrayList<Libro> _libros = new ArrayList<>();
                while (rrs.next()){
                    Libro libro = new Libro(rrs.getString("clave_registro"), rrs.getString("titulo_libro"));
                    _libros.add(libro);
                }
                prestamo.libros = _libros;
                rrs.close();
                stmtU.close();
                prestamo.Documento = ConsultarDocumentosPorId(prestamo.Documento.getId());
                prestamo.Usuario = MostrarUsuarioPorId(prestamo.Usuario.IdUsuario);
                _prestamos.add(prestamo);
            }
            rs.close();
            stmt.close();
            conn.close();
            return _prestamos;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Catalogo ConsultarDocumentosPorId(int IdDocumento){
        Catalogo catalogo = new Catalogo();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fnConsultarDocumentosPorId(?)");
            stmt.setInt(1, IdDocumento);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                catalogo.setId(rs.getInt("id_documento"));
                catalogo.setNombre(rs.getString("nombre"));
            }
            rs.close();
            stmt.close();
            conn.close();
            return catalogo;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Usuario MostrarUsuarioPorId(int IdUsuario){
        Usuario usuario = new Usuario();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fnseleccionartodosusuariosporid(?)");
            stmt.setInt(1, IdUsuario);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                usuario.IdUsuario = rs.getInt("id_usuario");
                usuario.setTelefono(new BigInteger(String.valueOf(rs.getLong("telefono"))));
                usuario.setEdad(rs.getInt("edad"));
                usuario.sexo = rs.getString("sexo");
                usuario.setCurp(rs.getString("curp").trim());
                usuario.Contrasenia = ClaseCifrarContrasenia.decrypt(rs.getString("contrasenia"));
                usuario.setGradoEscolar(new Catalogo(rs.getInt("id_grado_escolar"), rs.getString("grado_escolar")));
                usuario.setCorreo(rs.getString("correo"));
                Nombres nombre = new Nombres();
                nombre.IdNombre = rs.getInt("id_nombre");
                nombre.Nombre = rs.getString("nombre");
                nombre.ApellidoPaterno = rs.getString("apellido_paterno");
                nombre.ApellidoMaterno = rs.getString("apellido_materno");
                usuario.nombre = nombre;
                usuario.TipoUsuario = new Catalogo(rs.getInt("id_tipo_usuario"), rs.getString("tipo_usuario"));
                usuario.Ocupacion = new Catalogo(rs.getInt("id_ocupacion"), rs.getString("ocupacion"));
                Direccion direccion = new Direccion();
                direccion.IdDireccion = rs.getInt("id_direccion");
                direccion.setCalle(rs.getString("calle"));
                direccion.setCP(rs.getString("codigo_postal"));
                direccion.IdMunicipio = rs.getInt("id_municipio");
                direccion.Municipio = rs.getString("municipio");
                direccion.IdEstado = rs.getInt("id_estado");
                direccion.Estado = rs.getString("estado");
                direccion.IdLocalidad = rs.getInt("id_localidad");
                direccion.Localidad = rs.getString("localidad");
                usuario.direccion = direccion;
            }

            stmt.close();
            conn.close();
            return usuario;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean ExtenderPrestamo(Prestamo mPrestamo) throws Exception {
        int response = 0;
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fnActualizarPrestamo(?,?,?)");
            stmt.setInt(1, mPrestamo.IdPrestamo);
            stmt.setDate(2, mPrestamo.FechaFin);
            stmt.setString(3, mPrestamo.ComentarioAtraso);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                response = rs.getInt("fnactualizarprestamo");
            }
            if (response == 1){
                stmt.close();
                conn.close();

                String mensaje = String.format("""
                                <div style="font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; max-width: 600px;">
                                    <h2 style="color: #2F4F4F;">¡Hola, %s!</h2>
                                    <p style="font-size: 1.2em;">
                                        ¡Tu préstamo se ha extendido hasta la fecha %s!.
                                        Favor de cumplir con la nueva fecha de devolución
                                    </p>
                                    <p style="font-size: 1.2em;">
                                        Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                        mandando un correo a la dirección: <a href="mailto:direccion.educacion@tenangodelvalle.gob.mx">direccion.educacion@tenangodelvalle.gob.mx</a>
                                        De lo contrario, haz caso omiso a este mensaje
                                    </p>
                                    <p style="color: #696969;">
                                        *Este mensaje ha sido generado automáticamente*
                                    </p>
                                    <p style="color: #696969;">
                                        Biblioteca Pública Municipal Lic. Abel C. Salazar<br>
                                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                    </p>
                                </div>
                            """,mPrestamo.Usuario.nombre.Nombre,
                        Fechas.obtenerFechaDevolucion(mPrestamo.FechaFin));
                new EmailSender().emailSender("Tu préstamo solicitado se ha extendido", mPrestamo.Usuario.Correo, mensaje);
                return true;
            }else{
                conn.close();
                return false;
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean FinalizarPrestamo(Prestamo mPrestamo) throws Exception {
        int response = 0;
        StringJoiner joiner = new StringJoiner(",");
        for (Libro libro : mPrestamo.libros) {
            joiner.add(libro.getClave_registro());
        }
        String LibrosConcat = joiner.toString();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fnFinalizarPrestamo(?,?)");
            stmt.setInt(1, mPrestamo.IdPrestamo);
            stmt.setString(2, LibrosConcat);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                response = rs.getInt("fnfinalizarprestamo");
            }
            if (response == 1){
                stmt.close();
                conn.close();
                String mensaje = String.format("""
                                <div style="font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; max-width: 600px;">
                                    <h2 style="color: #2F4F4F;">Hola, %s</h2>
                                    <p style="font-size: 1.2em;">
                                        Nos complace anunciarte que el préstamo que habías solicitado ha terminado de manera satisfactoria
                                        ¡Ahora puedes volver a solicitar un nuevo préstamo de libros!
                                    </p>
                                    <p style="font-size: 1.2em;">
                                        Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                        mandando un correo a la dirección: <a href="mailto:direccion.educacion@tenangodelvalle.gob.mx">direccion.educacion@tenangodelvalle.gob.mx</a>
                                        De lo contrario, haz caso omiso a este mensaje
                                    </p>
                                    <p style="color: #696969;">
                                        *Este mensaje ha sido generado automáticamente*
                                    </p>
                                    <p style="color: #696969;">
                                        Biblioteca Pública Municipal Lic. Abel C. Salazar<br>
                                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                    </p>
                                </div>
                            """,mPrestamo.Usuario.nombre.Nombre);
                new EmailSender().emailSender("Finalización de préstamo", mPrestamo.Usuario.Correo, mensaje);
                return true;
            }else{
                conn.close();
                return false;
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean ValidarPrestamo(int IdUsuario) throws Exception {
        boolean response = false;
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fnValidarPrestamo(?)");
            stmt.setInt(1, IdUsuario);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                response = rs.getBoolean("fnValidarPrestamo");
            }
            return response;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public void EnviarRecordatorio(Prestamo mPrestamo) throws Exception {
        java.sql.Date fechaFin = mPrestamo.FechaFin;
        java.sql.Date fechaActual = Fechas.obtenerFechaActual();
        if (fechaFin.compareTo(fechaActual) < 0){
            String mensaje = String.format("""
                                <div style="font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; max-width: 600px;">
                                    <h2 style="color: #2F4F4F;">¡Hola, %s!</h2>
                                    <p style="font-size: 1.2em;">
                                        ¡Este es un mensaje para recordarte que tu fecha de devolución es el día %s!
                                        Si existe algún problema en la fecha de devolución, puedes extender el periodo
                                        acudiendo a la Biblioteca.
                                    </p>
                                    <p style="color: #696969;">
                                        *Este mensaje ha sido generado automáticamente*
                                    </p>
                                    <p style="color: #696969;">
                                        Biblioteca Pública Municipal Lic. Abel C. Salazar<br>
                                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                    </p>
                                </div>
                            """,mPrestamo.Usuario.nombre.Nombre,
                    Fechas.obtenerFechaDevolucion(mPrestamo.FechaFin));
            new EmailSender().emailSender("Recordatorio de préstamo, " + mPrestamo.Usuario.nombre.Nombre, mPrestamo.Usuario.Correo, mensaje);
        }else{
            String mensaje = String.format("""
                                <div style="font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; max-width: 600px;">
                                    <h2 style="color: #2F4F4F;">¡Hola, %s!</h2>
                                    <p style="font-size: 1.2em;">
                                        Tu préstamo excede la fecha límite de devolución
                                        por lo que te recordamos que tu fecha de devolución es el día %s.
                                        Se solicita devolver los libros en el menor tiempo posible.
                                        Si existe algún problema, acude a la Biblioteca para resolver la situación.
                                    </p>
                                    <p style="color: #696969;">
                                        *Este mensaje ha sido generado automáticamente*
                                    </p>
                                    <p style="color: #696969;">
                                        Biblioteca Pública Municipal Lic. Abel C. Salazar<br>
                                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                    </p>
                                </div>
                            """,mPrestamo.Usuario.nombre.Nombre,
                    Fechas.obtenerFechaDevolucion(mPrestamo.FechaFin));

            new EmailSender().emailSender("Préstamo vencido, " + mPrestamo.Usuario.nombre.Nombre, mPrestamo.Usuario.Correo, mensaje);
        }
    }

    public Reporte GenerarReporte(Date inicio, Date fin){
        Reporte reporte = new Reporte();
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fnConteoVisitas(?,?)");
            stmt.setDate(1, inicio);
            stmt.setDate(2, fin);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                reporte.Masculinos60 = rs.getInt("Masculinos60");
                reporte.Masculinos3059 = rs.getInt("Masculinos3059");
                reporte.Masculinos1829 = rs.getInt("Masculinos1829");
                reporte.Masculinos1317 = rs.getInt("Masculinos1317");
                reporte.Masculinos012 = rs.getInt("Masculinos012");
                reporte.MasculinosDis60 = rs.getInt("MasculinosDis60");
                reporte.MasculinosDis3059 = rs.getInt("MasculinosDis3059");
                reporte.MasculinosDis1829 = rs.getInt("MasculinosDis1829");
                reporte.MasculinosDis1317 = rs.getInt("MasculinosDis1317");
                reporte.MasculinosDis012 = rs.getInt("MasculinosDis012");
                reporte.MasculinosTotales = rs.getInt("MasculinosTotales");
                reporte.MasculinosDiscTotales = rs.getInt("MasculinosDiscTotales");
                reporte.FEM60 = rs.getInt("FEM60");
                reporte.FEM3059 = rs.getInt("FEM3059");
                reporte.FEM1829 = rs.getInt("FEM1829");
                reporte.FEM1317 = rs.getInt("FEM1317");
                reporte.FEM012 = rs.getInt("FEM012");
                reporte.FEMDis60 = rs.getInt("FEMDis60");
                reporte.FEMDis3059 = rs.getInt("FEMDis3059");
                reporte.FEMDis1829 = rs.getInt("FEMDis1829");
                reporte.FEMDis1317 = rs.getInt("FEMDis1317");
                reporte.FEMDis012 = rs.getInt("FEMDis012");
                reporte.FEMTotales = rs.getInt("FEMTotales");
                reporte.FEMDiscTotales = rs.getInt("FEMDisTotales");
                reporte.TotalUsers = rs.getInt("TotalUsers");

                PreparedStatement stmt1 = conn.prepareStatement("select * from fnSeleccionarConteoGrados(?,?)");
                stmt1.setDate(1, inicio);
                stmt1.setDate(2, fin);
                stmt1.execute();
                ResultSet rs1 = stmt1.getResultSet();

                while(rs1.next()){
                    reporte.Preescolar = rs1.getInt("Preescolar");
                    reporte.Primaria = rs1.getInt("Primaria");
                    reporte.Secundaria = rs1.getInt("Secundaria");
                    reporte.Preparatoria = rs1.getInt("Preparatoria");
                    reporte.Universidad = rs1.getInt("Universidad");
                    reporte.Posgrado = rs1.getInt("Posgrado");

                    PreparedStatement stmt2 = conn.prepareStatement("select * from fnContarOcupaciones(?,?)");
                    stmt2.setDate(1, inicio);
                    stmt2.setDate(2, fin);
                    stmt2.execute();
                    ResultSet rs2 = stmt2.getResultSet();
                    while(rs2.next()){
                        reporte.Hogar = rs2.getInt("hogar");
                        reporte.Estudiante = rs2.getInt("estudiante");
                        reporte.Empleado = rs2.getInt("empleado");
                        reporte.Desempleado = rs2.getInt("desocupado");

                        PreparedStatement stmt3 = conn.prepareStatement("select * from fnContarClasificaciones(?,?)");
                        stmt3.setDate(1, inicio);
                        stmt3.setDate(2, fin);
                        stmt3.execute();
                        ResultSet rs3 = stmt3.getResultSet();
                        while(rs3.next()){
                            reporte.ColeccionGeneral = rs3.getInt("ColGen");
                            reporte.ColeccionConsulta = rs3.getInt("ColCon");
                            reporte.ColeccionInfantil = rs3.getInt("Colinf");

                            PreparedStatement stmt4 = conn.prepareStatement("select * from fnContarUsuariosLibros(?,?)");
                            stmt4.setDate(1, inicio);
                            stmt4.setDate(2, fin);
                            stmt4.execute();
                            ResultSet rs4 = stmt4.getResultSet();
                            while(rs4.next()){
                                reporte.CredencialesExpedidas = rs4.getInt("TotalCredenciales");
                                reporte.LibrosDomicilio = rs4.getInt("TotalLibros");

                                PreparedStatement stmt5 = conn.prepareStatement("select * from fnContarActividadesPorEdadYSexo(?,?)");
                                stmt5.setDate(1, inicio);
                                stmt5.setDate(2, fin);
                                stmt5.execute();
                                ResultSet rs5 = stmt5.getResultSet();
                                while(rs5.next()){
                                    reporte.ActividadLectura60 = rs5.getInt("Actividades60");
                                    reporte.ActividadLectura3059 = rs5.getInt("Actividades30a59");
                                    reporte.ActividadLectura1829 = rs5.getInt("Actividades18a29");
                                    reporte.ActividadLectura1317 = rs5.getInt("Actividades13a17");
                                    reporte.ActividadLectura012 = rs5.getInt("Actividades0a12");
                                    reporte.ActividadLecturaTotal = rs5.getInt("TotalActividades");
                                    reporte.ActMasculinos60 = rs5.getInt("ActividadHombres60");
                                    reporte.ActMasculinos3059 = rs5.getInt("ActividadHombres30a59");
                                    reporte.ActMasculinos1829 = rs5.getInt("ActividadHombres18a29");
                                    reporte.ActMasculinos1317 = rs5.getInt("ActividadHombres13a17");
                                    reporte.ActMasculinos012 = rs5.getInt("ActividadHombres0a12");
                                    reporte.ActFem60 = rs5.getInt("ActividadMujeres60");
                                    reporte.ActFem3059 = rs5.getInt("ActividadMujeres30a59");
                                    reporte.ActFem1829 = rs5.getInt("ActividadMujeres18a29");
                                    reporte.ActFem1317 = rs5.getInt("ActividadMujeres13a17");
                                    reporte.ActFem012 = rs5.getInt("ActividadMujeres0a12");
                                    reporte.ActMasculinoTotal = rs5.getInt("TotalHombres");
                                    reporte.ActFemTotal = rs5.getInt("TotalMujeres");

                                    PreparedStatement stmt6 = conn.prepareStatement("select * from fnContarArtisticasPorEdadYSexo(?,?)");
                                    stmt6.setDate(1, inicio);
                                    stmt6.setDate(2, fin);
                                    stmt6.execute();
                                    ResultSet rs6 = stmt6.getResultSet();
                                    while(rs6.next()){
                                        reporte.ArtisticaLectura60 = rs6.getInt("Artistica60");
                                        reporte.ArtisticaLectura3059 = rs6.getInt("Artistica30a59");
                                        reporte.ArtisticaLectura1829 = rs6.getInt("Artistica18a29");
                                        reporte.ArtisticaLectura1317 = rs6.getInt("Artistica13a17");
                                        reporte.ArtisticaLectura012 = rs6.getInt("Artistica0a12");
                                        reporte.ArtisticaLecturaTotal = rs6.getInt("TotalArtistica");
                                        reporte.ArtMasculinos60 = rs6.getInt("ArtisticaHombres60");
                                        reporte.ArtMasculinos3059 = rs6.getInt("ArtisticaHombres30a59");
                                        reporte.ArtMasculinos1829 = rs6.getInt("ArtisticaHombres18a29");
                                        reporte.ArtMasculinos1317 = rs6.getInt("ArtisticaHombres13a17");
                                        reporte.ArtMasculinos012 = rs6.getInt("ArtisticaHombres0a12");
                                        reporte.ArtFem60 = rs6.getInt("ArtisticaMujeres60");
                                        reporte.ArtFem3059 = rs6.getInt("ArtisticaMujeres30a59");
                                        reporte.ArtFem1829 = rs6.getInt("ArtisticaMujeres18a29");
                                        reporte.ArtFem1317 = rs6.getInt("ArtisticaMujeres13a17");
                                        reporte.ArtFem012 = rs6.getInt("ArtisticaMujeres0a12");
                                        reporte.ArtMasculinoTotal = rs6.getInt("TotalHombres");
                                        reporte.ArtFemTotal = rs6.getInt("TotalMujeres");

                                        PreparedStatement stmt7 = conn.prepareStatement("select * from fnContarVisitasGuiadas(?,?)");
                                        stmt7.setDate(1, inicio);
                                        stmt7.setDate(2, fin);
                                        stmt7.execute();
                                        ResultSet rs7 = stmt7.getResultSet();
                                        while(rs7.next()){
                                            reporte.VisMasculinos60 = rs7.getInt("GHombres60");
                                            reporte.VisMasculinos3059 = rs7.getInt("GHombres30a59");
                                            reporte.VisMasculinos1829 = rs7.getInt("GHombres18a29");
                                            reporte.VisMasculinos1317 = rs7.getInt("GHombres13a17");
                                            reporte.VisMasculinos012 = rs7.getInt("GHombres0a12");
                                            reporte.VisMasculinoTotal = rs7.getInt("TotalHombres");
                                            reporte.VisFem60 = rs7.getInt("GMujeres60");
                                            reporte.VisFem3059 = rs7.getInt("GMujeres30a59");
                                            reporte.VisFem1829 = rs7.getInt("GMujeres18a29");
                                            reporte.VisFem1317 = rs7.getInt("GMujeres13a17");
                                            reporte.VisFem012 = rs7.getInt("GMujeres0a12");
                                            reporte.VisFemTotal = rs7.getInt("TotalMujeres");
                                            reporte.VisTotales = rs7.getInt("TotalG");

                                            PreparedStatement stmt8 = conn.prepareStatement("select * from fnContarServiciosDig(?,?)");
                                            stmt8.setDate(1, inicio);
                                            stmt8.setDate(2, fin);
                                            stmt8.execute();
                                            ResultSet rs8 = stmt8.getResultSet();
                                            while(rs8.next()){
                                                reporte.ServMasculinos60 = rs8.getInt("SDHombres60");
                                                reporte.ServMasculinos3059 = rs8.getInt("SDHombres30a59");
                                                reporte.ServMasculinos1829 = rs8.getInt("SDHombres18a29");
                                                reporte.ServMasculinos1317 = rs8.getInt("SDHombres13a17");
                                                reporte.ServMasculinos012 = rs8.getInt("SDHombres0a12");
                                                reporte.ServMasculinoTotal = rs8.getInt("TotalHombres");
                                                reporte.ServFem60 = rs8.getInt("SDMujeres60");
                                                reporte.ServFem3059 = rs8.getInt("SDMujeres30a59");
                                                reporte.ServFem1829 = rs8.getInt("SDMujeres18a29");
                                                reporte.ServFem1317 = rs8.getInt("SDMujeres13a17");
                                                reporte.ServFem012 = rs8.getInt("SDMujeres0a12");
                                                reporte.ServFemTotal = rs8.getInt("TotalMujeres");
                                                reporte.ServCursos60 = rs8.getInt("SD60");
                                                reporte.ServCursos3059 = rs8.getInt("SD30a59");
                                                reporte.ServCursos1829 = rs8.getInt("SD18a29");
                                                reporte.ServCursos1317 = rs8.getInt("SD13a17");
                                                reporte.ServCursos012 = rs8.getInt("SD0a12");
                                                reporte.ServCursosTotales = rs8.getInt("TotalSD");
                                                reporte.ServAsisCursos60 = rs8.getInt("SD60");
                                                reporte.ServAsisCursos3059 = rs8.getInt("SD30a59");
                                                reporte.ServAsisCursos1829 = rs8.getInt("SD18a29");
                                                reporte.ServAsisCursos1317 = rs8.getInt("SD13a17");
                                                reporte.ServAsisCursos012 = rs8.getInt("SD0a12");
                                                reporte.ServAsisCursosTotales = rs8.getInt("TotalSD");
                                            }
                                            stmt8.close();
                                        }
                                        stmt7.close();
                                    }
                                    stmt6.close();
                                }
                                stmt5.close();
                            }
                            stmt4.close();
                        }
                        stmt3.close();
                    }
                    stmt2.close();
                }
                stmt1.close();
            }
            stmt.close();
            conn.close();
            return reporte;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean InsertarRegistroLibro(String clave_registro, String  registro_clasificacion){
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("call spInsertarRegistroLibro(?,?)");
            stmt.setString(1, clave_registro);
            stmt.setString(2, registro_clasificacion);
            stmt.execute();

            stmt.close();
            conn.close();
            return true;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
