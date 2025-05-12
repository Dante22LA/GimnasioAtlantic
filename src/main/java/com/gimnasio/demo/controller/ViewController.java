// src/main/java/com/gimnasio/demo/controller/ViewController.java
package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Usuario;
import com.gimnasio.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final UsuarioRepository repo;

    public ViewController(UsuarioRepository repo) {
        this.repo = repo;
    }

    // Página de login
    @GetMapping({"/", "/login"})
    public String loginPage() {
        return "index"; // src/main/resources/templates/index.html
    }

    // Página de registro
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // src/main/resources/templates/register.html
    }

    // Página de tabla de usuarios
    @GetMapping("/tablaUsuarios")
    public String tablaUsuarios(Model model) {
        List<Usuario> usuarios = repo.findAll();
        model.addAttribute("usuarios", usuarios);
        return "tablaUsuarios"; // src/main/resources/templates/tablaUsuarios.html
    }

    
}
