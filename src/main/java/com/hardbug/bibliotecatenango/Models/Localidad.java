package com.hardbug.bibliotecatenango.Models;

public class Localidad {
    public Integer Id=0;
    public String Localidad="";
    public String Municipio="";
    public String Estado="";
    public Integer CP=0;

    public Integer getCP() {
        return CP;
    }

    public void setCP(Integer CP) {
        this.CP = CP;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String localidad) {
        Localidad = localidad;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public Localidad() {
    }

    public Localidad(Integer id, String localidad, String municipio, String estado, Integer cp) {
        Id = id;
        Localidad = localidad;
        Municipio = municipio;
        Estado = estado;
        CP = cp;
    }

    @Override
    public String toString() {
        return Localidad;
    }
}
