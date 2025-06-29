package com.gimnasio.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_usuario")
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String descripcion;

    // --- Constructor vacío requerido por JPA
    public TipoUsuario() {
    }

    public TipoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

    // --- Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
