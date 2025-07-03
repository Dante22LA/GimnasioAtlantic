package com.gimnasio.demo.repository;

import com.gimnasio.demo.model.EventoAsistencia;
import com.gimnasio.demo.model.EventoAsistenciaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoAsistenciaRepository extends JpaRepository<EventoAsistencia, EventoAsistenciaId> {

    boolean existsByIdEventoAndDocumentoUsuario(int idEvento, String documentoUsuario);

    Optional<EventoAsistencia> findByIdEventoAndDocumentoUsuario(int idEvento, String documentoUsuario);
}


