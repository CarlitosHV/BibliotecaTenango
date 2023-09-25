package com.hardbug.bibliotecatenango.Models;

import com.hardbug.bibliotecatenango.Fechas;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.sql.Date;
import java.util.Objects;

public class GenerarPdf {
    Document document;

    public void GenerarReporte(Date fecha,Reporte reporte) {
        String nombreReporte = "Reporte " + fecha + ".pdf";
        String rutaDocumento = System.getProperty("user.home") + "\\Downloads\\" + nombreReporte;

        try {
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaDocumento));
            document.open();
            document.newPage();

            InputStream template = GenerarPdf.class.getResourceAsStream("/assets/ReporteSS.pdf");
            PdfReader reader = new PdfReader(Objects.requireNonNull(template));
            int totalPages = reader.getNumberOfPages();
            for (int i = 1; i <= totalPages; i++) {
                document.newPage();
                PdfImportedPage page = writer.getImportedPage(reader, i);
                PdfContentByte cb = writer.getDirectContent();
                cb.addTemplate(page, 0, 0);
                ColumnText ct = new ColumnText(cb);
                switch (i){
                    case 1: {
                        int offset = 187;
                        addParagraph(cb, "Estado de México ", 126, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, "Tenango del Valle ", 302, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, "Tenango del Valle ", 482, 806 - offset, 559, 806 - offset + 20);
                        offset += 20;
                        addParagraph(cb, "Biblioteca Pública Municipal Lic. Abel C. Salazar ", 199, 806 - offset, 559, 806 - offset + 20);
                        offset += 19;
                        addParagraph(cb, Fechas.ObtenerFechaInicio(), 480, 806 - offset, 559, 806 - offset + 20);
                        offset += 70;
                        addParagraph(cb, String.valueOf(reporte.Masculinos60), 275, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.MasculinosDis60), 320, 806 - offset, 559, 806 - offset + 20);

                        addParagraph(cb, String.valueOf(reporte.FEM60) , 365, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.FEMDis60), 410, 806 - offset, 559, 806 - offset + 20);
                        offset += 18;
                        addParagraph(cb, String.valueOf(reporte.Masculinos3059), 275, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.MasculinosDis3059) , 320, 806 - offset, 559, 806 - offset + 20);

                        addParagraph(cb, String.valueOf(reporte.FEM3059), 365, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.FEMDis3059), 410, 806 - offset, 559, 806 - offset + 20);
                        offset += 15;
                        addParagraph(cb, String.valueOf(reporte.Masculinos1829), 275, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.MasculinosDis1829), 320, 806 - offset, 559, 806 - offset + 20);

                        addParagraph(cb, String.valueOf(reporte.FEM1829), 365, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.FEMDis1829), 410, 806 - offset, 559, 806 - offset + 20);
                        offset += 15;
                        addParagraph(cb, String.valueOf(reporte.Masculinos1317), 275, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.MasculinosDis1317), 320, 806 - offset, 559, 806 - offset + 20);

                        offset += 15;
                        addParagraph(cb, String.valueOf(reporte.TotalUsers), 510, 806 - offset, 559, 806 - offset + 20);
                        offset -= 15;

                        addParagraph(cb, String.valueOf(reporte.FEM1317) ,365 ,806-offset ,559 ,806-offset+20);
                        addParagraph(cb, String.valueOf(reporte.FEMDis1317), 410, 806 - offset, 559, 806 - offset + 20);
                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.Masculinos012), 275, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.MasculinosDis012), 320, 806 - offset, 559, 806 - offset + 20);

                        addParagraph(cb, String.valueOf(reporte.FEM012), 365, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.FEMDis012), 410, 806 - offset, 559, 806 - offset + 20);
                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.MasculinosTotales), 275, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.MasculinosDiscTotales), 320, 806 - offset, 559, 806 - offset + 20);

                        addParagraph(cb, String.valueOf(reporte.FEMTotales), 365, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.FEMDiscTotales), 410, 806 - offset, 559, 806 - offset + 20);

                        offset += 30;
                        addParagraph(cb, String.valueOf(reporte.Preescolar), 190, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.Primaria), 330, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.Secundaria), 518, 806 - offset, 559, 806 - offset + 20);
                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.Preparatoria), 190, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.Universidad), 330, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.Posgrado), 518, 806 - offset, 559, 806 - offset + 20);

                        ct.go();
                        break;
                    }
                    case 2:{
                        break;
                    }
                    case 3:{
                        break;
                    }
                }
            }
            document.close();

            System.out.println("PDF en blanco creado correctamente en: " + nombreReporte);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addParagraph(PdfContentByte cb, String text, float llx, float lly, float urx, float ury) throws DocumentException {
        ColumnText ct = new ColumnText(cb);
        ct.setSimpleColumn(llx, lly, urx, ury);
        ct.addElement(generateParagraph(text));
        ct.go();
    }

    public static Paragraph generateParagraph(String contenido){
        Paragraph parrafo = new Paragraph();
        parrafo.add(contenido);
        parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
        parrafo.setFont(FontFactory.getFont("Times New Roman", 7, Font.BOLD, BaseColor.BLACK));

        return parrafo;
    }
}
