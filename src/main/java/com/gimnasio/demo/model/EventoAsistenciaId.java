package com.gimnasio.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class EventoAsistenciaId implements Serializable {
    private int idEvento;
    private String documentoUsuario;

    public EventoAsistenciaId() {}

    public EventoAsistenciaId(int idEvento, String documentoUsuario) {
        this.idEvento = idEvento;
        this.documentoUsuario = documentoUsuario;
    }

    // equals y hashCode obligatorios

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventoAsistenciaId)) return false;
        EventoAsistenciaId that = (EventoAsistenciaId) o;
        return idEvento == that.idEvento && documentoUsuario.equals(that.documentoUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEvento, documentoUsuario);
    }
}

