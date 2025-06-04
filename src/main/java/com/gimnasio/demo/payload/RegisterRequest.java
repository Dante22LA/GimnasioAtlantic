package com.gimnasio.demo.payload;

// DTO para mapear los datos enviados al registrar un usuario (endpoint /register)
public class RegisterRequest {
    private String documento; // Documento de identidad (clave primaria del usuario)
    private String telefono;  // Teléfono del usuario
    private String password;  // Contraseña para la nueva cuenta
    private String nombres;   // Nombres del usuario
    private String apellidos; // Apellidos del usuario
    private String rango;     // Rango del usuario (e.g. "usuario", "admin", etc.)

    // --- Getters y setters ---

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
}
