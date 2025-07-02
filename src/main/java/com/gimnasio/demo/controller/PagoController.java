package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Boleta;
import com.gimnasio.demo.model.Plan;
import com.gimnasio.demo.payload.PagoRequest;
import com.gimnasio.demo.repository.BoletaRepository;
import com.gimnasio.demo.repository.PlanRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private PlanRepository planRepository;

    private static final Logger logger = LoggerFactory.getLogger(PagoController.class);

    // ========== REGISTRAR PAGO ==========
    @PostMapping
    public ResponseEntity<?> registrarPago(@Valid @RequestBody PagoRequest request) {
        logger.info("📥 Iniciando registro de pago para documento {}", request.getDocumento());

        try {
            Optional<Plan> planOptional = planRepository.findById(request.getIdPlan());

            if (planOptional.isEmpty()) {
                logger.warn("❌ El plan con ID {} no fue encontrado", request.getIdPlan());
                return ResponseEntity.badRequest().body("El plan con ID " + request.getIdPlan() + " no existe.");
            }

            Plan plan = planOptional.get();

            Boleta boleta = new Boleta();
            boleta.setDocumentoUsuario(request.getDocumento());
            boleta.setPlan(plan);
            boleta.setFechaEmision(new Date());
            boleta.setMontoTotal(BigDecimal.valueOf(plan.getPrecio()));

            boletaRepository.save(boleta);

            logger.info("✅ Pago registrado con éxito para el documento {}", request.getDocumento());
            return ResponseEntity.ok("Pago registrado exitosamente.");

        } catch (Exception e) {
            logger.error("🚨 Error al registrar el pago", e);
            return ResponseEntity.internalServerError().body("Error al registrar el pago: " + e.getMessage());
        }
    }

}
