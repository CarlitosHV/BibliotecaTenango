package com.hardbug.bibliotecatenango;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.Date;
import java.util.Locale;

public class Fechas {

    public static String ObtenerFechaInicio(){
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("es", "ES"));
        return fechaActual.format(formatoFecha);
    }

    public static String ObtenerFechaDevolucion() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaConSuma = fechaActual.plusDays(5);

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("es", "ES"));
        return fechaConSuma.format(formatoFecha);
    }


    public static Date obtenerFechaInicioSqlDate() {
        long milisegundos = Calendar.getInstance().getTimeInMillis();
        return new Date(milisegundos);
    }

    public static Date obtenerFechaDevolucionSqlDate() {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, 5);
        long milisegundos = calendario.getTimeInMillis();
        return new Date(milisegundos);
    }
}
