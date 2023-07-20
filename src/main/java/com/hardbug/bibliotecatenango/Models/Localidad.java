package com.hardbug.bibliotecatenango.Models;

public class Localidad {
    int Id;
    String Localidad;
    String Municipio;
    String Estado;
    int CP;

    public int getCP() {
        return CP;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public Localidad(int id, String localidad, String municipio, String estado, int cp) {
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
