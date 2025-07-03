package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Evento;
import com.gimnasio.demo.payload.EventoDTO;
import com.gimnasio.demo.repository.EventoAsistenciaRepository;
import com.gimnasio.demo.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoAsistenciaRepository asistenciaRepository;

    // ✅ 1. Listar todos los eventos
    @GetMapping
    public List<EventoDTO> listarEventos(@RequestParam(required = false) String documento) {
        List<Evento> eventos = eventoRepository.findAll();

        return eventos.stream().map(evento -> {
            boolean inscrito = false;

            if (documento != null && !documento.isBlank()) {
                inscrito = asistenciaRepository.existsByIdEventoAndDocumentoUsuario(
                        evento.getIdEvento(), documento);
            }

            return new EventoDTO(
                    evento.getIdEvento(),
                    evento.getTitulo(),
                    evento.getDescripcion(),
                    evento.getFechaEvento().toString(),
                    evento.getCupos(),
                    inscrito);
        }).toList();
    }

    // ✅ 2. Registrar un nuevo evento
    @PostMapping
    public ResponseEntity<?> crearEvento(@RequestBody Evento evento) {
        try {
            evento.setEstado("activo"); // por defecto
            Evento nuevo = eventoRepository.save(evento);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar evento: " + e.getMessage());
        }
    }

    // ✅ 3. Actualizar evento
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEvento(@PathVariable int id, @RequestBody Evento eventoActualizado) {
        Optional<Evento> optionalEvento = eventoRepository.findById(id);

        if (optionalEvento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Evento evento = optionalEvento.get();
        evento.setTitulo(eventoActualizado.getTitulo());
        evento.setDescripcion(eventoActualizado.getDescripcion());
        evento.setFechaEvento(eventoActualizado.getFechaEvento());
        evento.setCupos(eventoActualizado.getCupos());
        evento.setEstado(eventoActualizado.getEstado());

        eventoRepository.save(evento);
        return ResponseEntity.ok(evento);
    }

    // ✅ 4. Eliminar evento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEvento(@PathVariable int id) {
        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        eventoRepository.deleteById(id);
        return ResponseEntity.ok("Evento eliminado correctamente.");
    }

}
