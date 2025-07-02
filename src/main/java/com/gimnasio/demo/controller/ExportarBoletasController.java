package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Boleta;
import com.gimnasio.demo.repository.BoletaRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
public class ExportarBoletasController {

    @Autowired
    private BoletaRepository boletaRepository;

    @GetMapping("/api/boletas/exportar")
    public ResponseEntity<byte[]> exportarBoletasExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet hoja = workbook.createSheet("Boletas");
            Row header = hoja.createRow(0);
            String[] columnas = { "ID", "Documento", "ID Plan", "Nombre Plan", "Fecha", "Monto" };

            for (int i = 0; i < columnas.length; i++) {
                header.createCell(i).setCellValue(columnas[i]);
            }

            List<Boleta> boletas = boletaRepository.findAll();

            int fila = 1;
            for (Boleta b : boletas) {
                Row row = hoja.createRow(fila++);
                row.createCell(0).setCellValue(b.getIdBoleta());
                row.createCell(1).setCellValue(b.getDocumentoUsuario());
                row.createCell(2).setCellValue(b.getPlan().getIdPlan());
                row.createCell(3).setCellValue(b.getPlan().getNombre());
                row.createCell(4).setCellValue(b.getFechaEmision().toString());
                row.createCell(5).setCellValue(b.getMontoTotal().doubleValue());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=boletas.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(out.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
