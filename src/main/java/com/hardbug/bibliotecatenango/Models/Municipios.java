package com.hardbug.bibliotecatenango.Models;

public class Municipios {
    int id;
    String Municipio;
    String Estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Municipios() {
    }

    public Municipios(int id, String municipio, String estado) {
        this.id = id;
        Municipio = municipio;
        Estado = estado;
    }

    public Municipios(int id, String municipio) {
        this.id = id;
        Municipio = municipio;
    }

    @Override
    public String toString() {
        return Municipio;
    }
}
