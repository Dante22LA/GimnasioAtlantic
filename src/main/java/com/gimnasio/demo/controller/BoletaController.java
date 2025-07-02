package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Boleta;
import com.gimnasio.demo.model.Plan;
import com.gimnasio.demo.model.Usuario;
import com.gimnasio.demo.payload.PagoRequest;
import com.gimnasio.demo.repository.BoletaRepository;
import com.gimnasio.demo.repository.PlanRepository;
import com.gimnasio.demo.repository.UsuarioRepository;
import com.gimnasio.demo.util.BoletaUtils;
import com.gimnasio.demo.payload.ResumenBoletaDTO; // Asegúrate de importar tu DTO
import java.util.ArrayList;

import com.google.common.collect.ListMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BoletaController {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanRepository planRepository;

    // REGISTRAR PAGO
    @PostMapping("/pago")
    public ResponseEntity<?> registrarPago(@RequestBody PagoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getDocumento()).orElse(null);
        Plan plan = planRepository.findById(request.getIdPlan()).orElse(null);

        if (usuario == null || plan == null) {
            return ResponseEntity.status(404).body("Usuario o Plan no encontrado");
        }

        Boleta boleta = new Boleta();
        boleta.setDocumentoUsuario(usuario.getDocumento());
        boleta.setPlan(plan);
        boleta.setMontoTotal(BigDecimal.valueOf(plan.getPrecio()));
        boleta.setFechaEmision(new Date());

        boletaRepository.save(boleta);
        return ResponseEntity.ok("Boleta registrada correctamente");
    }

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
}
