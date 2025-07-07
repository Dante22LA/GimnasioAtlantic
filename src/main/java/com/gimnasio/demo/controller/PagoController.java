package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Boleta;
import com.gimnasio.demo.model.Plan;
import com.gimnasio.demo.model.Usuario;
import com.gimnasio.demo.payload.PagoRequest;
import com.gimnasio.demo.repository.BoletaRepository;
import com.gimnasio.demo.repository.PlanRepository;
import com.gimnasio.demo.repository.UsuarioRepository;
import com.gimnasio.demo.util.BoletaUtils;
import com.gimnasio.demo.util.PdfGeneratorUtil;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = LoggerFactory.getLogger(PagoController.class);

    @PostMapping("/pago-pdf")
    public ResponseEntity<?> registrarPagoConPDF(@RequestBody PagoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getDocumento()).orElse(null);
        Plan plan = planRepository.findById(request.getIdPlan()).orElse(null);

        if (usuario == null || plan == null) {
            return ResponseEntity.status(404).body("Usuario o plan no encontrado");
        }

        // 🚫 Verificar si ya tiene un plan activo
        List<Boleta> boletasUsuario = boletaRepository.findByDocumentoUsuario(usuario.getDocumento());
        Date ahora = new Date();

        for (Boleta b : boletasUsuario) {
            Date vencimiento = BoletaUtils.calcularFechaVencimiento(b.getFechaEmision(),
                    b.getPlan().getDuracionMeses());
            if (vencimiento.after(ahora)) {
                return ResponseEntity.badRequest()
                        .body("Ya tienes un plan activo hasta el " + vencimiento);
            }
        }

        // ✅ Crear nueva boleta
        Boleta boleta = new Boleta();
        boleta.setDocumentoUsuario(usuario.getDocumento());
        boleta.setPlan(plan);
        boleta.setMontoTotal(BigDecimal.valueOf(plan.getPrecio()));
        boleta.setFechaEmision(ahora);
        boletaRepository.save(boleta);

        // 🧾 Generar PDF
        byte[] pdfBytes = PdfGeneratorUtil.generarPDFBoleta(boleta);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=boleta.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdfBytes);
    }

}
