package com.gimnasio.demo.payload;  // Paquete donde se ubican las clases que representan payloads de peticiones

public class LoginRequest {  // DTO para mapear los datos de login enviados en el cuerpo de la petición
    private String documento; // Campo para el identificador único del usuario (documento de identidad)
    private String password;  // Campo para la contraseña del usuario

    // getters y setters...
    
    public String getDocumento() { 
        return documento;    // Retorna el valor del documento
    }
    public void setDocumento(String documento) { 
        this.documento = documento;  // Asigna el valor del documento recibido
    }
    public String getPassword() { 
        return password;    // Retorna el valor de la contraseña
    }
    public void setPassword(String password) { 
        this.password = password;    // Asigna el valor de la contraseña recibida
    }
}
