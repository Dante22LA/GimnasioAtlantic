package com.gimnasio.demo.util;

import com.gimnasio.demo.model.Boleta;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;

public class PdfGeneratorUtil {

    public static byte[] generarPDFBoleta(Boleta boleta) {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 12);

            document.add(new Paragraph("Boleta de Pago", fontTitulo));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Documento: " + boleta.getDocumentoUsuario(), fontNormal));
            document.add(new Paragraph("Plan: " + boleta.getPlan().getNombre(), fontNormal));
            document.add(new Paragraph("Monto: S/ " + boleta.getMontoTotal(), fontNormal));
            document.add(new Paragraph("Fecha: " + boleta.getFechaEmision(), fontNormal));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }
}

