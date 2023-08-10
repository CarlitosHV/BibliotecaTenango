package com.hardbug.bibliotecatenango.Models;

public class Nombres {

    public Integer IdNombre = 0;
    public String Nombre = "";
    public String ApellidoPaterno = "";
    public String ApellidoMaterno = "";

    public Nombres() {
    }

    public Nombres(Integer idNombre, String nombre, String apellidoPaterno, String apellidoMaterno) {
        IdNombre = idNombre;
        Nombre = nombre;
        ApellidoPaterno = apellidoPaterno;
        ApellidoMaterno = apellidoMaterno;
    }

    public Nombres(String nombre, String apellidoPaterno, String apellidoMaterno) {
        Nombre = nombre;
        ApellidoPaterno = apellidoPaterno;
        ApellidoMaterno = apellidoMaterno;
    }

    public String GetNombreCompleto(){
        return this.Nombre + " " + this.ApellidoPaterno + " " + this.ApellidoMaterno;
    }

    public Integer getIdNombre() {
        return IdNombre;
    }

    public void setIdNombre(Integer idNombre) {
        IdNombre = idNombre;
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
}
