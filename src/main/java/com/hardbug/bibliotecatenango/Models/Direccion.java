package com.hardbug.bibliotecatenango.Models;

public class Direccion {

    public Integer IdDireccion = 0;
    public String Calle = "";
    public String CP = "";
    public Integer IdMunicipio = 0;
    public String Municipio = "";
    public String Estado = "";
    public String Localidad = "";
    public Integer IdEstado = 0;
    public Integer IdLocalidad = 0;

    public Direccion() {
    }

    public Direccion(int idDireccion, String calle, String CP, Integer idMunicipio, Integer idEstado, Integer idLocalidad) {
        this.IdDireccion = idDireccion;
        Calle = calle;
        this.CP = CP;
        IdMunicipio = idMunicipio;
        IdEstado = idEstado;
        IdLocalidad = idLocalidad;
    }

    public Direccion(String calle, String CP, Integer idMunicipio, Integer idEstado, Integer idLocalidad) {
        Calle = calle;
        this.CP = CP;
        IdMunicipio = idMunicipio;
        IdEstado = idEstado;
        IdLocalidad = idLocalidad;
    }

    public String getDireccionCompleta(){
        return this.Calle + ", " + this.Municipio + "," + this.Localidad + ". " + this.Estado;
    }

    public Integer getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        IdDireccion = idDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public Integer getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        IdMunicipio = idMunicipio;
    }

    public Integer getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(Integer idEstado) {
        IdEstado = idEstado;
    }

    public Integer getIdLocalidad() {
        return IdLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad) {
        IdLocalidad = idLocalidad;
    }
}
