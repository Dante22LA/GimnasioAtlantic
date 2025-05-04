// src/main/java/com/gimnasio/demo/controller/ViewController.java
package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Usuario;
import com.gimnasio.demo.repository.UsuarioRepository;        // Repo para acceder a datos de Usuario (CRUD)
import org.springframework.stereotype.Controller;             // Marca la clase como controlador MVC
import org.springframework.ui.Model;                          // Interfaz para pasar datos a la vista
import org.springframework.web.bind.annotation.GetMapping;    // Anotación para mapear peticiones GET

import java.util.List;

@Controller  // Define que esta clase maneja peticiones web y devuelve vistas (HTML)
public class ViewController {

    private final UsuarioRepository repo;  // Repositorio inyectado para manejar la entidad Usuario

    // Inyección de repositorio vía constructor (recomendado para tests e inmutabilidad)
    public ViewController(UsuarioRepository repo) {
        this.repo = repo;
    }

    /**
     * Muestra la página de login.
     * URL: GET / o GET /login
     * @return nombre de la plantilla Thymeleaf (index.html)
     */
    @GetMapping({"/", "/login"})
    public String loginPage() {
        // Thymeleaf buscará: src/main/resources/templates/index.html
        return "index";
    }

    /**
     * Muestra la página de registro de usuarios.
     * URL: GET /register
     * @return nombre de la plantilla Thymeleaf (register.html)
     */
    @GetMapping("/register")
    public String registerPage() {
        // Thymeleaf buscará: src/main/resources/templates/register.html
        return "register";
    }

    /**
     * Muestra la tabla con todos los usuarios registrados.
     * URL: GET /tablaUsuarios
     * @param model objeto para pasar datos a la vista
     * @return nombre de la plantilla Thymeleaf (tablaUsuarios.html)
     */
    @GetMapping("/tablaUsuarios")
    public String tablaUsuarios(Model model) {
        // Recupera todos los usuarios de la base de datos
        List<Usuario> usuarios = repo.findAll();
        // Añade la lista al modelo con la clave "usuarios"
        model.addAttribute("usuarios", usuarios);
        // Thymeleaf buscará: src/main/resources/templates/tablaUsuarios.html
        return "tablaUsuarios";
    }
}
