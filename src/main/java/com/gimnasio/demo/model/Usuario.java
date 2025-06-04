package com.gimnasio.demo.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity  // Marca esta clase como una entidad JPA que se mapea a una tabla de la BD
@Table(name = "usuarios")  // Especifica el nombre de la tabla en la base de datos
public class Usuario {

    @Id  // Define la clave primaria de la entidad
    @Column(length = 15)  // Configura la columna: longitud máxima 15 caracteres
    private String documento;

    @Column(nullable = false, length = 15)  // No acepta null; longitud máxima 15
    private String telefono;

    @Column(nullable = false, length = 20)  // No acepta null; longitud máxima 20
    private String password;

    @Column(nullable = false, length = 50)  // No acepta null; longitud máxima 50
    private String nombres;

    @Column(nullable = false, length = 50)  // No acepta null; longitud máxima 50
    private String apellidos;

    @Column(nullable = false, length = 10)  // No acepta null; longitud máxima 10
    private String rango = "usuario";  // Valor por defecto en el objeto Java

    /**
     * Fecha de creación del registro.
     * insertable = false & updatable = false:
     *   → JPA no incluirá este campo en INSERT ni en UPDATE.
     * columnDefinition indica al DDL que use CURRENT_TIMESTAMP por defecto.
     * MySQL rellenará automáticamente este campo con la hora actual.
     */
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

    //--- Getters y setters (POJO estándar) ---

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

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public Instant getFecha() {
        return fecha;
    }

    // Opcional: no exponemos setter para fecha,
    // así evitamos modificarla manualmente desde Java.
    // public void setFecha(Instant fecha) {
    //     this.fecha = fecha;
    // }
}
