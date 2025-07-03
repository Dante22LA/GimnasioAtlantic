package com.gimnasio.demo.payload;

public class EventoDTO {
    private int idEvento;
    private String titulo;
    private String descripcion;
    private String fechaEvento;
    private int cupos;
    private boolean inscrito;

    public EventoDTO(int idEvento, String titulo, String descripcion, String fechaEvento, int cupos, boolean inscrito) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEvento = fechaEvento;
        this.cupos = cupos;
        this.inscrito = inscrito;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public int getCupos() {
        return cupos;
    }

    public boolean isInscrito() {
        return inscrito;
    }
}


