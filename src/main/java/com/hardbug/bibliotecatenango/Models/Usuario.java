package com.hardbug.bibliotecatenango.Models;

import java.math.BigInteger;

public class Usuario {
    int IdUsuario;
    String Nombre;
    String ApellidoPaterno;
    String ApellidoMaterno;
    String Correo;
    String Contrasenia;
    BigInteger Telefono;
    int Edad;
    String sexo;
    String Curp;
    String GradoEscolar;
    String TipoUsuario;
    String Ocupacion;
    String Calle;
    String Estado;
    String Municipio;
    String Localidad;
    int CodigoPostal;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String correo,
                   String contrasenia, BigInteger telefono, int edad, String sexo, String curp, String gradoEscolar, String tipoUsuario,
                   String ocupacion, String calle, String estado, String municipio, String localidad, int codigoPostal) {
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
        GradoEscolar = gradoEscolar;
        TipoUsuario = tipoUsuario;
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

    public String getGradoEscolar() {
        return GradoEscolar;
    }

    public void setGradoEscolar(String gradoEscolar) {
        GradoEscolar = gradoEscolar;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        TipoUsuario = tipoUsuario;
    }

    public String getOcupacion() {
        return Ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        Ocupacion = ocupacion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String localidad) {
        Localidad = localidad;
    }

    public int getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        CodigoPostal = codigoPostal;
    }
}
