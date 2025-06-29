package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Plan;
import com.gimnasio.demo.repository.PlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/planes")
public class PlanController {

    @Autowired
    private PlanRepository planRepository;

    // Tu método para actualizar el plan
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPlan(@PathVariable int id, @RequestBody Plan planActualizado) {
        Optional<Plan> planOpt = planRepository.findById(id);
        if (planOpt.isPresent()) {
            Plan plan = planOpt.get();
            plan.setNombre(planActualizado.getNombre());
            plan.setDuracionMeses(planActualizado.getDuracionMeses());
            plan.setPrecio(planActualizado.getPrecio());
            plan.setDescripcion(planActualizado.getDescripcion());

            planRepository.save(plan);
            return ResponseEntity.ok("Plan actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan no encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPlan(@PathVariable int id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
            return ResponseEntity.ok("Plan eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan no encontrado.");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarPlanes() {
        return ResponseEntity.ok(planRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPlanPorId(@PathVariable int id) {
        return planRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
