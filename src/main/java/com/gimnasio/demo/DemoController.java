package com.gimnasio.demo;

import org.springframework.web.bind.annotation.GetMapping;    // Importa la anotación para mapear peticiones HTTP GET
import org.springframework.web.bind.annotation.RestController; // Importa la anotación que define un controlador REST

@RestController  // Marca esta clase como controlador REST, de modo que sus métodos devuelven directamente el cuerpo de la respuesta
public class DemoController {
    
    @GetMapping("/demo")  // Mapea las solicitudes GET a la ruta "/demo" a este método
    public String demo() {
        return "¡Hola desde /demo!";  // Retorna una cadena que será el cuerpo de la respuesta HTTP
    }
}
