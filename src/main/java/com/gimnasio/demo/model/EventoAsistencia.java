package com.gimnasio.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "evento_asistencia")
@IdClass(EventoAsistenciaId.class)
public class EventoAsistencia {

    @Id
    @Column(name = "id_evento")
    private int idEvento;

    @Id
    @Column(name = "documento_usuario")
    private String documentoUsuario;

    @Column(name = "fecha_inscripcion", updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant fechaInscripcion;

    // Getters y setters

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public Instant getFechaInscripcion() {
        return fechaInscripcion;
    }
}

