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

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private PlanRepository planRepository;

    // ========== REGISTRAR PAGO ==========
    @PostMapping
    public ResponseEntity<?> registrarPago(@Valid @RequestBody PagoRequest request) {
        try {
            // 1. Buscar el plan por ID
            Optional<Plan> planOptional = planRepository.findById(request.getIdPlan());

            if (planOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("El plan con ID " + request.getIdPlan() + " no existe.");
            }

            Plan plan = planOptional.get();

            // 2. Crear y llenar la boleta
            Boleta boleta = new Boleta();
            boleta.setDocumentoUsuario(request.getDocumento());
            boleta.setPlan(plan);
            boleta.setFechaEmision(new Date());
            boleta.setMontoTotal(BigDecimal.valueOf(plan.getPrecio())); // 🔥 AQUI SE CORRIGE

            // 3. Guardar en la base de datos
            boletaRepository.save(boleta);

            return ResponseEntity.ok("Pago registrado exitosamente.");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar el pago: " + e.getMessage());
        }
    }
}


