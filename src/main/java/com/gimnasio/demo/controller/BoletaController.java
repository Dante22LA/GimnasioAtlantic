package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Boleta;
import com.gimnasio.demo.model.Plan;
import com.gimnasio.demo.model.Usuario;
import com.gimnasio.demo.payload.PagoRequest;
import com.gimnasio.demo.repository.BoletaRepository;
import com.gimnasio.demo.repository.PlanRepository;
import com.gimnasio.demo.repository.UsuarioRepository;
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

    @GetMapping("/boletas")
    public List<Boleta> listarBoletas() {
        return boletaRepository.findAll();
    }

    @DeleteMapping("/boletas/{id}")
    public ResponseEntity<?> eliminarBoleta(@PathVariable int id) {
        if (!boletaRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Boleta no encontrada");
        }
        boletaRepository.deleteById(id);
        return ResponseEntity.ok("Boleta eliminada");
    }

}
