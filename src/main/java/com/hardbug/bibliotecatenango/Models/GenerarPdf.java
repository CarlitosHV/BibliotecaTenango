package com.hardbug.bibliotecatenango.Models;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

public class GenerarPdf {

    public static void GenerarReporte(Date fecha,Reporte reporte) {
        // Ruta y nombre del archivo PDF que deseas crear
        String nombreReporte = "Reporte " + fecha + ".pdf";
        String rutaDocumento = System.getProperty("user.home") + "\\Downloads\\" + nombreReporte;

        // Inicializar el documento
        Document document = new Document();

        try {
            // Crear un objeto PdfWriter para escribir en el archivo PDF
            PdfWriter.getInstance(document, new FileOutputStream(rutaDocumento));

            // Abrir el documento para escribir contenido
            document.open();
            document.newPage();
            document.addHeader("Reporte Biblioteca", "Reporte generado");
            document.add(generateParagraph("Hombres > 60: " + reporte.Masculinos60));
            document.add(generateParagraph("Hombres 30 a 59: " + reporte.Masculinos3059));
            document.add(generateParagraph("Hombres 18 a 29: " + reporte.Masculinos1829));
            document.add(generateParagraph("Hombres 13 a 17: " + reporte.Masculinos1317));
            document.add(generateParagraph("Hombres 0 a 12: " + reporte.Masculinos012));
            document.add(generateParagraph("Hombres > 60 DISCAPACIDAD: " + reporte.MasculinosDis60));
            document.add(generateParagraph("Hombres 30 a 59  DISCAPACIDAD: " + reporte.MasculinosDis3059));
            document.add(generateParagraph("Hombres 18 a 29 DISCAPACIDAD: " + reporte.MasculinosDis1829));
            document.add(generateParagraph("Hombres 13 a 17 DISCAPACIDAD: " + reporte.MasculinosDis1317));
            document.add(generateParagraph("Hombres 0 a 12 DISCAPACIDAD: " + reporte.MasculinosDis012));
            document.add(generateParagraph("Total Hombres: " + reporte.MasculinosTotales));
            document.add(generateParagraph("Total Hombres DISCAPACIDAD: " + reporte.MasculinosDiscTotales));
            document.add(generateParagraph("Mujeres > 60: " + reporte.FEM60));
            document.add(generateParagraph("Mujeres 30 a 59: " + reporte.FEM3059));
            document.add(generateParagraph("Mujeres 18 a 29: " + reporte.FEM1829));
            document.add(generateParagraph("Mujeres 13 a 17: " + reporte.FEM1317));
            document.add(generateParagraph("Mujeres 0 a 12: " + reporte.FEM012));
            document.add(generateParagraph("Total Mujeres: " + reporte.FEMTotales));
            document.add(generateParagraph("Mujeres > 60 DISCAPACIDAD: " + reporte.FEMDis60));
            document.add(generateParagraph("Mujeres 30 a 59  DISCAPACIDAD: " + reporte.FEMDis3059));
            document.add(generateParagraph("Mujeres 18 a 29 DISCAPACIDAD: " + reporte.FEMDis1829));
            document.add(generateParagraph("Mujeres 13 a 17 DISCAPACIDAD: " + reporte.FEMDis1317));
            document.add(generateParagraph("Mujeres 0 a 12 DISCAPACIDAD: " + reporte.FEMDis012));
            document.add(generateParagraph("Mujeres totales DISCAPACIDAD: "+reporte.FEMDiscTotales));
            document.add(generateParagraph("Total usuarios: " + reporte.TotalUsers));
            // Cerrar el documento
            document.close();

            System.out.println("PDF en blanco creado correctamente en: " + nombreReporte);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Paragraph generateParagraph(String contenido){
        Paragraph parrafo = new Paragraph();
        parrafo.add(contenido);
        parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
        parrafo.setFont(FontFactory.getFont("Times New Roman", 12, Font.BOLD, BaseColor.BLACK));

        return parrafo;
    }

}
