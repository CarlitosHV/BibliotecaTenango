package com.hardbug.bibliotecatenango;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fechas {

    public String ObtenerFechaInicio(){
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("es", "ES"));
        return fechaActual.format(formatoFecha);
    }

    public String ObtenerFechaDevolucion() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaConSuma = fechaActual.plusDays(5);

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("es", "ES"));
        return fechaConSuma.format(formatoFecha);
    }


    public Date ObtenerFechaInicioDate(){
        Calendar calendario = Calendar.getInstance();
        return calendario.getTime();
    }

    public Date ObtenerFechaDevolucionDate() {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, 5);
        return calendario.getTime();
    }
}
