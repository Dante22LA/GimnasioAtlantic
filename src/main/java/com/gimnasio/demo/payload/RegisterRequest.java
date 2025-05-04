package com.gimnasio.demo.payload;

// DTO para mapear los datos enviados al registrar un usuario (endpoint /register)
public class RegisterRequest {
    private String documento; // Documento de identidad (clave primaria del usuario)
    private String password;  // Contraseña para la nueva cuenta
    private String nombres;   // Nombres del usuario
    private String apellidos; // Apellidos del usuario

    // getters y setters...
    public String getDocumento() { return documento; }            // Devuelve el documento proporcionado
    public void setDocumento(String documento) { this.documento = documento; } // Asigna el documento recibido
    
    public String getPassword() { return password; }               // Devuelve la contraseña proporcionada
    public void setPassword(String password) { this.password = password; }     // Asigna la contraseña recibida

    public String getNombres() { return nombres; }                 // Devuelve los nombres proporcionados
    public void setNombres(String nombres) { this.nombres = nombres; }        // Asigna los nombres recibidos

    public String getApellidos() { return apellidos; }             // Devuelve los apellidos proporcionados
    public void setApellidos(String apellidos) { this.apellidos = apellidos; } // Asigna los apellidos recibidos
}
