package com.hardbug.bibliotecatenango.Models;

import java.sql.Date;
import java.util.ArrayList;

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
