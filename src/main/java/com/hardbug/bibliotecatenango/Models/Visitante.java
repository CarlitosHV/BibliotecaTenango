package com.hardbug.bibliotecatenango.Models;

import java.util.Date;

public class Visitante {
    int id_visitante;
    String edad_visitante;
    int id_grado_escolar;
    String ocupacion;
    boolean discapacidad;
    int id_nombre;
    String fecha_visita;

    public Visitante(){

    }

    public Visitante(int id_visitante,String edad_visitante,int id_grado_escolar,String ocupacion,
                     boolean discapaciad,int id_nombre,String fecha_visita){
        this.id_visitante = id_visitante;
        this.edad_visitante = edad_visitante;
        this.id_grado_escolar = id_grado_escolar;
        this.ocupacion = ocupacion;
        this.discapacidad = discapaciad;
        this.id_nombre = id_nombre;
        this.fecha_visita = fecha_visita;

    }
    public int getId_visitante() {
        return id_visitante;
    }

    public void setId_visitante(int id_visitante) {
        this.id_visitante = id_visitante;
    }

    public String getEdad_visitante() {
        return edad_visitante;
    }

    public void setEdad_visitante(String edad_visitante) {
        this.edad_visitante = edad_visitante;
    }

    public int getId_grado_escolar() {
        return id_grado_escolar;
    }

    public void setId_grado_escolar(int id_grado_escolar) {
        this.id_grado_escolar = id_grado_escolar;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public boolean isDiscapaciad() {
        return discapacidad;
    }
    public void setDiscapaciad(boolean discapaciad) {
        this.discapacidad = discapaciad;
    }

    public int getId_nombre() {
        return id_nombre;
    }

    public void setId_nombre(int id_nombre) {
        this.id_nombre = id_nombre;
    }

    public String getFecha_visita() {
        return fecha_visita;
    }

    public void setFecha_visita(String fecha_visita) {
        this.fecha_visita = fecha_visita;
    }
}
