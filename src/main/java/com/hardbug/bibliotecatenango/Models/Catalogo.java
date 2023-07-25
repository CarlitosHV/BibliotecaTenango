package com.hardbug.bibliotecatenango.Models;

public class Catalogo {
    int id = 0;
    String nombre = "";

    public Catalogo() {
    }

    public Catalogo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Catalogo(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
