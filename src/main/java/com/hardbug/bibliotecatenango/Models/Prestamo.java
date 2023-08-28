package com.hardbug.bibliotecatenango.Models;

import java.util.ArrayList;
import java.sql.Date;

public class Prestamo {
    public Integer IdPrestamo = 0;
    public Usuario Usuario = null;
    public Date FechaInicio;
    public Date FechaFin;
    public String ComentarioAtraso = "";
    public Integer Renovaciones = 0;
    public Catalogo Documento = null;
    public ArrayList<Libro> libros = null;

}
