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
                        new EmailSender().emailSender("Registro de cuenta", mUsuario.getCorreo(),
                                "Bienvenido, " + mUsuario.getNombre() + "\n" + """
                                Tu correo ha sido registrado en el sistema de Biblioteca Pública Municipal Lic. Abel C. Salazar.
                                ¡Ahora puedes solicitar libros con tu cuenta al proporcionar tu CURP!
                                Dudas o sugerencias al correo: direccion.educacion@tenangodelvalle.gob.mx
                                
                                *Este mensaje ha sido generado automáticamente*
                                Biblioteca Pública Municipal Lic. Abel C. Salazar
                                Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                            """);
                    }else{
                        new EmailSender().emailSender("Actualización de cuenta", mUsuario.getCorreo(), """
                                Tu cuenta ha sido actualizada\s
                                Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                mandando un correo a la dirección: direccion.educacion@tenangodelvalle.gob.mx
                                De lo contrario, haz caso omiso a este mensaje                             \s
                                
                                *Este mensaje ha sido generado automáticamente*
                                
                                Biblioteca Pública Municipal Lic. Abel C. Salazar
                                Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                """);
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

    public boolean EliminarUsuario(Integer IdUsuario, String Correo){
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("call spEliminarUsuario(?)");
            stmt.setInt(1, IdUsuario);
            stmt.execute();
            stmt.close();

            PreparedStatement statement = conn.prepareStatement("select * from fnseleccionartodosusuariosporid(?)");
            statement.setInt(1, IdUsuario);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()){
                conn.close();
                return false;
            }else{
                conn.close();
                new EmailSender().emailSender("Cuenta eliminada del sistema", Correo, """
                                Tu cuenta ha sido eliminada del sistema. Esperamos verte pronto :(\s
                                Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                mandando un correo a la dirección: direccion.educacion@tenangodelvalle.gob.mx
                                De lo contrario, haz caso omiso a este mensaje\s
                                
                                *Este mensaje ha sido generado automáticamente*
                                
                                Biblioteca Pública Municipal Lic. Abel C. Salazar
                                Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                """);
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
                                ¡Se ha generado tu préstamo, %s!
                                Tu fecha de préstamo es: %s
                                Tu fecha de devolución es: %s
                                Es necesario que cumplas con tu fecha de devolución o si necesitas más tiempo, antes del vencimiento puedes extender el periodo\s
                                solicitándolo en la biblioteca\s
                                Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                mandando un correo a la dirección: direccion.educacion@tenangodelvalle.gob.mx
                                De lo contrario, haz caso omiso a este mensaje

                                *Este mensaje ha sido generado automáticamente*

                                Biblioteca Pública Municipal Lic. Abel C. Salazar
                                Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.""", mPrestamo.Usuario.nombre.Nombre,
                        Fechas.obtenerFechaInicio(mPrestamo.FechaInicio), Fechas.obtenerFechaDevolucion(mPrestamo.FechaFin));
                new EmailSender().emailSender("Tu préstamo solicitado se ha generado", mPrestamo.Usuario.Correo, mensaje);
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
                java.sql.Date sqlDate  = new java.sql.Date(miVisitante.fecha.getTime());
                stmtVisita.setDate(5,  sqlDate);
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
                    Libro libro = new Libro(rrs.getString("clave_registro"));
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
                        ¡Hola, %s!
                        ¡Tu préstamo se ha extendido hasta la fecha %s!
                        Favor de cumplir con la nueva fecha de devolución
                        Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                        mandando un correo a la dirección: direccion.educacion@tenangodelvalle.gob.mx
                        De lo contrario, haz caso omiso a este mensaje

                        *Este mensaje ha sido generado automáticamente*

                        Biblioteca Pública Municipal Lic. Abel C. Salazar
                        Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.""",mPrestamo.Usuario.nombre.Nombre, Fechas.obtenerFechaDevolucion(mPrestamo.FechaFin));
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
        try{
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + IndexApp.servidor + "/" + IndexApp.base_datos,
                    IndexApp.usuario, IndexApp.contrasenia);

            PreparedStatement stmt = conn.prepareStatement("select * from fnFinalizarPrestamo(?)");
            stmt.setInt(1, mPrestamo.IdPrestamo);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                response = rs.getInt("fnfinalizarprestamo");
            }
            if (response == 1){
                stmt.close();
                conn.close();
                new EmailSender().emailSender("Tu préstamo solicitado ha finalizado, " + mPrestamo.Usuario.nombre.Nombre, mPrestamo.Usuario.Correo, """
                                ¡Ahora puedes volver a solicitar un nuevo préstamo de libros!
                                Si no reconoces este movimiento, favor de reportarlo en la Biblioteca o
                                mandando un correo a la dirección: direccion.educacion@tenangodelvalle.gob.mx
                                De lo contrario, haz caso omiso a este mensaje\s
                                
                                *Este mensaje ha sido generado automáticamente*
                                
                                Biblioteca Pública Municipal Lic. Abel C. Salazar
                                Lic. Abel C. Salazar #201, Tenango del Valle. Edoméx.
                                """);
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
}
