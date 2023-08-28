package com.hardbug.bibliotecatenango.Models;

import java.util.Date;

public class Visitante {
    public int id=0;
    public int edad=0;
    public Catalogo grado_escolar=null;
    public Catalogo ocupacion=null;
    public boolean discapacidad=false;
    public Nombres nombre=null;
    public String nombres="";
    public String ap_paterno="";
    public String ap_materno="";
    public Date fecha=new Date();
    public Catalogo Actividad = null;

    public Visitante() {

    }

    public Visitante(int id, int edad, Catalogo grado_escolar,
                     Catalogo ocupacion, boolean discapacidad,
                     com.hardbug.bibliotecatenango.Models.Nombres nombre, String nombres, String ap_paterno,
                     String ap_materno, Date fecha) {
        this.id = id;
        this.edad = edad;
        this.grado_escolar = grado_escolar;
        this.ocupacion = ocupacion;
        this.discapacidad = discapacidad;
        this.nombre = nombre;
        this.nombres = nombres;
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.fecha = fecha;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
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


}
