package com.hardbug.bibliotecatenango.Models;

import java.math.BigInteger;

public class Usuario {
    public int IdUsuario = 0;
    public String Nombre = "";
    public String ApellidoPaterno = "";
    public String ApellidoMaterno = "";
    public String Correo = "";
    public String Contrasenia = "";
    public BigInteger Telefono = BigInteger.valueOf(0);
    public int Edad = 0;
    public String sexo = "";
    public String Curp = "";
    public Integer IdGradoEscolar = 0;
    public Catalogo GradoEscolar = null;
    public Integer IdTipoUsuario = 1;
    public Catalogo TipoUsuario = null;
    public Integer IdOcupacion = 0;
    public Catalogo Ocupacion = null;
    public String Calle = "";
    public Estados Estado = null;
    public Municipios Municipio = null;
    public Localidad Localidad = null;
    int CodigoPostal = 0;
    public Nombres nombre = null;
    public Direccion direccion = null;
    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String correo,
                   String contrasenia, BigInteger telefono, int edad, String sexo, String curp, Integer idGradoEscolar,
                   Catalogo gradoEscolar, Integer idTipoUsuario, Integer idOcupacion, Catalogo ocupacion, String calle,
                   Estados estado, Municipios municipio, com.hardbug.bibliotecatenango.Models.Localidad localidad, int codigoPostal) {
        IdUsuario = idUsuario;
        Nombre = nombre;
        ApellidoPaterno = apellidoPaterno;
        ApellidoMaterno = apellidoMaterno;
        Correo = correo;
        Contrasenia = contrasenia;
        Telefono = telefono;
        Edad = edad;
        this.sexo = sexo;
        Curp = curp;
        IdGradoEscolar = idGradoEscolar;
        GradoEscolar = gradoEscolar;
        IdTipoUsuario = idTipoUsuario;
        IdOcupacion = idOcupacion;
        Ocupacion = ocupacion;
        Calle = calle;
        Estado = estado;
        Municipio = municipio;
        Localidad = localidad;
        CodigoPostal = codigoPostal;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        ApellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        ApellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContrasenia() {
        return Contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        Contrasenia = contrasenia;
    }

    public BigInteger getTelefono() {
        return Telefono;
    }

    public void setTelefono(BigInteger telefono) {
        Telefono = telefono;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String curp) {
        Curp = curp;
    }

    public Integer getIdGradoEscolar() {
        return IdGradoEscolar;
    }

    public void setIdGradoEscolar(Integer idGradoEscolar) {
        IdGradoEscolar = idGradoEscolar;
    }

    public Catalogo getGradoEscolar() {
        return GradoEscolar;
    }

    public void setGradoEscolar(Catalogo gradoEscolar) {
        GradoEscolar = gradoEscolar;
    }

    public Integer getIdTipoUsuario() {
        return IdTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        IdTipoUsuario = idTipoUsuario;
    }

    public Integer getIdOcupacion() {
        return IdOcupacion;
    }

    public void setIdOcupacion(Integer idOcupacion) {
        IdOcupacion = idOcupacion;
    }

    public Catalogo getOcupacion() {
        return Ocupacion;
    }

    public void setOcupacion(Catalogo ocupacion) {
        Ocupacion = ocupacion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public Estados getEstado() {
        return Estado;
    }

    public void setEstado(Estados estado) {
        Estado = estado;
    }

    public Municipios getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipios municipio) {
        Municipio = municipio;
    }

    public com.hardbug.bibliotecatenango.Models.Localidad getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(com.hardbug.bibliotecatenango.Models.Localidad localidad) {
        Localidad = localidad;
    }

    public int getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        CodigoPostal = codigoPostal;
    }
}
