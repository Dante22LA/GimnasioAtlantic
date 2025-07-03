package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Evento;
import com.gimnasio.demo.model.EventoAsistencia;
import com.gimnasio.demo.model.Usuario;
import com.gimnasio.demo.repository.EventoAsistenciaRepository;
import com.gimnasio.demo.repository.EventoRepository;
import com.gimnasio.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/asistencias")
public class EventoAsistenciaController {

    @Autowired
    private EventoAsistenciaRepository asistenciaRepo;

    @Autowired
    private EventoRepository eventoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    // ===== POST: REGISTRAR ASISTENCIA =====
    @PostMapping
    public ResponseEntity<?> registrarAsistencia(@RequestParam int idEvento, @RequestParam String documento) {
        try {
            Optional<Evento> optionalEvento = eventoRepo.findById(idEvento);
            if (optionalEvento.isEmpty()) {
                return ResponseEntity.badRequest().body("Evento no encontrado");
            }

            Evento evento = optionalEvento.get();

            if (evento.getCupos() <= 0) {
                return ResponseEntity.badRequest().body("Ya no hay cupos disponibles.");
            }

            if (asistenciaRepo.existsByIdEventoAndDocumentoUsuario(idEvento, documento)) {
                return ResponseEntity.badRequest().body("Ya estás inscrito en este evento.");
            }

            // Guardar inscripción
            EventoAsistencia asistencia = new EventoAsistencia();
            asistencia.setIdEvento(idEvento);
            asistencia.setDocumentoUsuario(documento);
            asistenciaRepo.save(asistencia);

            // Reducir cupo
            evento.setCupos(evento.getCupos() - 1);
            eventoRepo.save(evento);

            return ResponseEntity.ok("Inscripción exitosa");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al inscribirse: " + e.getMessage());
        }
    }

    // ===== GET: LISTAR TODAS LAS ASISTENCIAS CON DETALLE =====
    @GetMapping
    public List<Map<String, Object>> listarAsistencias() {
        List<EventoAsistencia> asistencias = asistenciaRepo.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (EventoAsistencia a : asistencias) {
            Map<String, Object> fila = new HashMap<>();

            // Buscar detalles del evento
            Optional<Evento> ev = eventoRepo.findById(a.getIdEvento());
            Optional<Usuario> us = usuarioRepo.findById(a.getDocumentoUsuario());

            fila.put("idEvento", a.getIdEvento());
            fila.put("documentoUsuario", a.getDocumentoUsuario());
            fila.put("fechaInscripcion", a.getFechaInscripcion());

            fila.put("tituloEvento", ev.map(Evento::getTitulo).orElse("Desconocido"));
            fila.put("nombreUsuario", us.map(u -> u.getNombres() + " " + u.getApellidos()).orElse("Desconocido"));

            resultado.add(fila);
        }

        return resultado;
    }

    // ===== DELETE: ELIMINAR UNA INSCRIPCIÓN =====
    @DeleteMapping("/{idEvento}/{documento}")
    public ResponseEntity<?> eliminarInscripcion(@PathVariable int idEvento, @PathVariable String documento) {
        try {
            Optional<EventoAsistencia> asistenciaOpt = asistenciaRepo.findByIdEventoAndDocumentoUsuario(idEvento, documento);

            if (asistenciaOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Inscripción no encontrada.");
            }

            asistenciaRepo.delete(asistenciaOpt.get());

            // Aumentar cupo del evento
            eventoRepo.findById(idEvento).ifPresent(ev -> {
                ev.setCupos(ev.getCupos() + 1);
                eventoRepo.save(ev);
            });

            return ResponseEntity.ok("Inscripción eliminada correctamente.");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar inscripción: " + e.getMessage());
        }
    }
}


