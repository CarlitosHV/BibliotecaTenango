package com.hardbug.bibliotecatenango;

public class ClaseLibro {
    int clave_registro;
    String clasificacion;
    String anio_edicion;
    String registro_clasificacion;
    String estante;
    int existencias;
    String editorial;
    String lugar_edicion;
    String titulo_libro;
    String nombre_autor;
    String descripcion_libro;

    public ClaseLibro() {
    }

    public ClaseLibro(int clave_registro, String clasificacion, String anio_edicion, String registro_clasificacion, String estante, int existencias,
                      String editorial, String lugar_edicion, String titulo_libro, String nombre_autor, String descripcion_libro) {
        this.clave_registro = clave_registro;
        this.clasificacion = clasificacion;
        this.anio_edicion = anio_edicion;
        this.registro_clasificacion = registro_clasificacion;
        this.estante = estante;
        this.existencias = existencias;
        this.editorial = editorial;
        this.lugar_edicion = lugar_edicion;
        this.titulo_libro = titulo_libro;
        this.nombre_autor = nombre_autor;
        this.descripcion_libro = descripcion_libro;
    }

    public int getClave_registro() { return clave_registro; }

    public void setClave_registro(int clave_registro) { this.clave_registro = clave_registro; }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getAnio_edicion() {
        return anio_edicion;
    }

    public void setAnio_edicion(String anio_edicion) {
        this.anio_edicion = anio_edicion;
    }

    public String getRegistro_clasificacion() {
        return registro_clasificacion;
    }

    public void setRegistro_clasificacion(String registro_clasificacion) {
        this.registro_clasificacion = registro_clasificacion;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getLugar_edicion() {
        return lugar_edicion;
    }

    public void setLugar_edicion(String lugar_edicion) {
        this.lugar_edicion = lugar_edicion;
    }

    public String getTitulo_libro() {
        return titulo_libro;
    }

    public void setTitulo_libro(String titulo_libro) {
        this.titulo_libro = titulo_libro;
    }

    public String getNombre_autor() {
        return nombre_autor;
    }

    public void setNombre_autor(String nombre_autor) {
        this.nombre_autor = nombre_autor;
    }

    public String getDescripcion_libro() {
        return descripcion_libro;
    }

    public void setDescripcion_libro(String descripcion_libro) {
        this.descripcion_libro = descripcion_libro;
    }


}
