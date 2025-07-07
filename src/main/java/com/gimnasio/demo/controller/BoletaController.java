package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Boleta;
import com.gimnasio.demo.repository.BoletaRepository;
//import com.gimnasio.demo.repository.PlanRepository;
//import com.gimnasio.demo.repository.UsuarioRepository;
import com.gimnasio.demo.util.BoletaUtils;
import com.gimnasio.demo.payload.ResumenBoletaDTO; // Asegúrate de importar tu DTO
import java.util.ArrayList;

import com.google.common.collect.ListMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BoletaController {

    @Autowired
    private BoletaRepository boletaRepository;

    // LISTAR TODAS LAS BOLETAS
    @GetMapping("/boletas")
    public List<Boleta> listarBoletas() {
        return boletaRepository.findAll();
    }

    // ELIMINAR UNA BOLETA
    @DeleteMapping("/boletas/{id}")
    public ResponseEntity<?> eliminarBoleta(@PathVariable int id) {
        if (!boletaRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Boleta no encontrada");
        }
        boletaRepository.deleteById(id);
        return ResponseEntity.ok("Boleta eliminada");
    }

    // AGRUPAR BOLETAS POR DNI (USANDO GUAVA)
    @GetMapping("/boletas/agrupadas")
    public ListMultimap<String, Boleta> boletasAgrupadasPorDni() {
        List<Boleta> boletas = boletaRepository.findAll();
        return BoletaUtils.agruparPorUsuario(boletas); // Usa la clase utilitaria con Guava
    }

    @GetMapping("/boletas/agrupadas/resumen")
    public List<ResumenBoletaDTO> resumenPorUsuario() {
        List<Boleta> boletas = boletaRepository.findAll();
        ListMultimap<String, Boleta> agrupadas = BoletaUtils.agruparPorUsuario(boletas);

        List<ResumenBoletaDTO> resumen = new ArrayList<>();

        for (String documento : agrupadas.keySet()) {
            List<Boleta> boletasUsuario = agrupadas.get(documento);

            BigDecimal total = boletasUsuario.stream()
                    .map(Boleta::getMontoTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            resumen.add(new ResumenBoletaDTO(
                    documento,
                    total,
                    boletasUsuario.size()));
        }

        return resumen;
    }

    @GetMapping("/boletas/{documento}")
    public List<Boleta> obtenerBoletasPorUsuario(@PathVariable String documento) {
        return boletaRepository.findByDocumentoUsuario(documento);
    }

    @GetMapping("/usuarios/{dni}/plan-actual")
    public ResponseEntity<?> obtenerPlanActual(@PathVariable String dni) {
        List<Boleta> boletas = boletaRepository.findByDocumentoUsuario(dni);
        LocalDate hoy = LocalDate.now();

        for (Boleta boleta : boletas) {
            LocalDate inicio = boleta.getFechaEmision().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fin = inicio.plusMonths(boleta.getPlan().getDuracionMeses());

            if (!hoy.isBefore(inicio) && !hoy.isAfter(fin)) {
                Map<String, Object> resp = new HashMap<>();
                resp.put("idBoleta", boleta.getIdBoleta());
                resp.put("nombrePlan", boleta.getPlan().getNombre());
                resp.put("fechaInicio", inicio.toString());
                resp.put("fechaFin", fin.toString());
                return ResponseEntity.ok(resp);
            }
        }
        return ResponseEntity.status(404).body("No hay plan activo");
    }

}
