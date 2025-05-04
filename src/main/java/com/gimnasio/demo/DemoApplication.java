package com.gimnasio.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Anotación que habilita la configuración automática de Spring Boot,
// escaneo de componentes y configuración basada en convenciones.
public class DemoApplication {

    public static void main(String[] args) {
        // Método de arranque de la aplicación:
        // Inicializa el contexto de Spring y arranca el servidor embebido (por ejemplo, Tomcat).
        SpringApplication.run(DemoApplication.class, args);
    }
}
