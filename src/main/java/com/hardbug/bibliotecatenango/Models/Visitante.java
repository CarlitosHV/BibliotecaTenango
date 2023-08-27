package com.hardbug.bibliotecatenango.Models;

import java.util.Date;

public class Visitante {
    public int id=0;
    public int edad=0;
    public Catalogo grado_escolar=null;
    public Catalogo ocupacion=null;
    public boolean discapacidad=false;
    public Nombres nombre=null;
    public Date fecha=new Date();
    public Direccion direccion=null;

    public Visitante() {

    }

    public Visitante(int id, int edad, Catalogo grado_escolar, Catalogo ocupacion, boolean discapacidad,
                     Nombres nombre, Date fecha, Direccion direccion) {
        this.id = id;
        this.edad = edad;
        this.grado_escolar = grado_escolar;
        this.ocupacion = ocupacion;
        this.discapacidad = discapacidad;
        this.nombre = nombre;
        this.fecha = fecha;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }


    public boolean isDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public Nombres getNombre() {
        return nombre;
    }

    public void setNombre(Nombres nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}
