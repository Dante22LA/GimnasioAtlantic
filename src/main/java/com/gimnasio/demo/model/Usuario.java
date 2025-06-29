package com.gimnasio.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(length = 15)
    private String documento;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 50)
    private String nombres;

    @Column(nullable = false, length = 50)
    private String apellidos;

    // NUEVO: Relación con tipos_usuario
    @ManyToOne
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    private TipoUsuario tipoUsuario;

    @Column(
        name = "fecha",
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Instant fecha;

    // Constructor vacío requerido por JPA
    public Usuario() {
    }

    // --- Getters y Setters ---

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Instant getFecha() {
        return fecha;
    }
}

