package com.hardbug.bibliotecatenango.Models;

public class Estados {
    int id;
    String Estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public Estados() {
    }

    public Estados(int id, String estado) {
        this.id = id;
        Estado = estado;
    }

    @Override
    public String toString() {
        return Estado;
    }
}
