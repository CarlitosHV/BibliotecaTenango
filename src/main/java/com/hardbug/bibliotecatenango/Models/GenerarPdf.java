package com.hardbug.bibliotecatenango.Models;

import com.hardbug.bibliotecatenango.Fechas;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.sql.Date;
import java.util.Objects;

public class GenerarPdf {
    Document document;

    public void GenerarReporte(Date fecha, Reporte reporte) {
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

                        offset += 29;
                        addParagraph(cb, String.valueOf(reporte.Hogar), 215, 806 - offset, 559, 806 - offset + 20);
                        offset += 2;
                        addParagraph(cb, String.valueOf(reporte.Empleado), 515, 806 - offset, 559, 806 - offset + 20);
                        offset += 16;
                        addParagraph(cb, String.valueOf(reporte.Estudiante), 215, 806 - offset, 559, 806 - offset + 20);
                        offset += 3;
                        addParagraph(cb, String.valueOf(reporte.Desempleado), 515, 806 - offset, 559, 806 - offset + 20);

                        offset += 35;
                        addParagraph(cb, String.valueOf(reporte.ColeccionGeneral), 190, 806 - offset, 559, 806 - offset + 20);
                        offset += 2;
                        addParagraph(cb, String.valueOf(reporte.ColeccionConsulta), 330, 806 - offset, 559, 806 - offset + 20);
                        offset += 1;
                        addParagraph(cb, String.valueOf(reporte.ColeccionInfantil), 518, 806 - offset, 559, 806 - offset + 20);

                        offset += 37;
                        addParagraph(cb, String.valueOf(reporte.PublicacionesPeriodicas), 175, 806 - offset, 559, 806 - offset + 20);
                        offset += 2;
                        addParagraph(cb, String.valueOf(reporte.AudioVisual), 280, 806 - offset, 559, 806 - offset + 20);
                        offset += 1;
                        addParagraph(cb, String.valueOf(reporte.Ludoteca), 370, 806 - offset, 559, 806 - offset + 20);
                        offset += 1;
                        addParagraph(cb, String.valueOf(reporte.Braille), 510, 806 - offset, 559, 806 - offset + 20);

                        offset += 20;
                        addParagraph(cb, String.valueOf(reporte.ColeccionEstatal), 230, 806 - offset, 559, 806 - offset + 20);
                        offset += 1;
                        addParagraph(cb, String.valueOf(reporte.ColeccionEspecial), 410, 806 - offset, 559, 806 - offset + 20);
                        offset += 1;
                        addParagraph(cb, String.valueOf(reporte.ColeccionOtra), 530, 806 - offset, 559, 806 - offset + 20);

                        offset += 35;
                        addParagraph(cb, String.valueOf(reporte.CredencialesExpedidas), 230, 806 - offset, 559, 806 - offset + 20);
                        offset += 4;
                        addParagraph(cb, String.valueOf(reporte.LibrosDomicilio), 480, 806 - offset, 559, 806 - offset + 20);

                        ct.go();
                        break;
                    }
                    case 2:{
                        int offset = 215;
                        addParagraph(cb, String.valueOf(reporte.ActividadLectura60), 312, 806 - offset, 559, 806 - offset + 20);
                        offset -= 2;
                        addParagraph(cb, String.valueOf(reporte.ActMasculinos60), 405, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ActFem60), 500, 806 - offset, 559, 806 - offset + 20);

                        offset += 18;
                        addParagraph(cb, String.valueOf(reporte.ActividadLectura3059), 312, 806 - offset, 559, 806 - offset + 20);
                        offset -= 2;
                        addParagraph(cb, String.valueOf(reporte.ActMasculinos3059), 405, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ActFem3059), 500, 806 - offset, 559, 806 - offset + 20);

                        offset += 19;
                        addParagraph(cb, String.valueOf(reporte.ActividadLectura1829), 312, 806 - offset, 559, 806 - offset + 20);
                        offset -= 2;
                        addParagraph(cb, String.valueOf(reporte.ActMasculinos1829), 405, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ActFem1829), 500, 806 - offset, 559, 806 - offset + 20);

                        offset += 18;
                        addParagraph(cb, String.valueOf(reporte.ActividadLectura1317), 312, 806 - offset, 559, 806 - offset + 20);
                        offset -= 2;
                        addParagraph(cb, String.valueOf(reporte.ActMasculinos1317), 405, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ActFem1317), 500, 806 - offset, 559, 806 - offset + 20);

                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.ActividadLectura012), 312, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ActMasculinos012), 405, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ActFem012), 500, 806 - offset, 559, 806 - offset + 20);

                        offset += 16;
                        addParagraph(cb, String.valueOf(reporte.ActividadLecturaTotal), 312, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ActMasculinoTotal), 405, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ActFemTotal), 500, 806 - offset, 559, 806 - offset + 20);

                        offset += 52;
                        addParagraph(cb, String.valueOf(reporte.VisMasculinos60), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.VisFem60), 347, 806 - offset, 559, 806 - offset + 20);
                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.VisMasculinos3059), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.VisFem3059), 347, 806 - offset, 559, 806 - offset + 20);
                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.VisMasculinos1829), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.VisFem1829), 347, 806 - offset, 559, 806 - offset + 20);
                        offset += 16;
                        addParagraph(cb, String.valueOf(reporte.VisMasculinos1317), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.VisFem1317), 347, 806 - offset, 559, 806 - offset + 20);
                        offset -= 4;
                        addParagraph(cb, String.valueOf(reporte.VisTotales), 450, 806 - offset, 559, 806 - offset + 20);
                        offset += 20;
                        addParagraph(cb, String.valueOf(reporte.VisMasculinos012), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.VisFem012), 347, 806 - offset, 559, 806 - offset + 20);
                        offset += 16;
                        addParagraph(cb, String.valueOf(reporte.VisMasculinoTotal), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.VisFemTotal), 347, 806 - offset, 559, 806 - offset + 20);


                        offset += 58;
                        addParagraph(cb, String.valueOf(reporte.ServMasculinos60), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServFem60), 347, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServCursos60), 410, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServAsisCursos60), 500, 806 - offset, 559, 806 - offset + 20);
                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.ServMasculinos3059), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServFem3059), 347, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServCursos3059), 410, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServAsisCursos3059), 500, 806 - offset, 559, 806 - offset + 20);
                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.ServMasculinos1829), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServFem1829), 347, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServCursos1829), 410, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServAsisCursos1829), 500, 806 - offset, 559, 806 - offset + 20);
                        offset += 16;
                        addParagraph(cb, String.valueOf(reporte.ServMasculinos1317), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServFem1317), 347, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServCursos1317), 410, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServAsisCursos1317), 500, 806 - offset, 559, 806 - offset + 20);
                        offset += 16;
                        addParagraph(cb, String.valueOf(reporte.ServMasculinos012), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServFem012), 347, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServCursos012), 410, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServAsisCursos012), 500, 806 - offset, 559, 806 - offset + 20);
                        offset += 16;
                        addParagraph(cb, String.valueOf(reporte.ServMasculinoTotal), 277, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServFemTotal), 347, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServCursosTotales), 410, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ServAsisCursosTotales), 500, 806 - offset, 559, 806 - offset + 20);

                        ct.go();
                        break;
                    }
                    case 3:{
                        int offset = 225;
                        addParagraph(cb, String.valueOf(reporte.ArtisticaLectura60), 305, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtMasculinos60), 390, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtFem60), 480, 806 - offset, 559, 806 - offset + 20);

                        offset += 17;
                        addParagraph(cb, String.valueOf(reporte.ArtisticaLectura3059), 305, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtMasculinos3059), 390, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtFem3059), 480, 806 - offset, 559, 806 - offset + 20);

                        offset += 19;
                        addParagraph(cb, String.valueOf(reporte.ArtisticaLectura1829), 305, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtMasculinos1829), 390, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtFem1829), 480, 806 - offset, 559, 806 - offset + 20);

                        offset += 18;
                        addParagraph(cb, String.valueOf(reporte.ArtisticaLectura1317), 305, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtMasculinos1317), 390, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtFem1317), 480, 806 - offset, 559, 806 - offset + 20);

                        offset += 19;
                        addParagraph(cb, String.valueOf(reporte.ArtisticaLectura012), 305, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtMasculinos012), 390, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtFem012), 480, 806 - offset, 559, 806 - offset + 20);

                        offset += 16;
                        addParagraph(cb, String.valueOf(reporte.ArtisticaLecturaTotal), 305, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtMasculinoTotal), 390, 806 - offset, 559, 806 - offset + 20);
                        addParagraph(cb, String.valueOf(reporte.ArtFemTotal), 480, 806 - offset, 559, 806 - offset + 20);

                        ct.go();
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
