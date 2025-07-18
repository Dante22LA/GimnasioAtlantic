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

    /**
     * 1) Al entrar a la raíz "/", muestro la página de inicio:
     */
    @GetMapping("/")
    public String homePage() {
        // Aquí devolvemos la plantilla "/"
        return "US_Inicio";
    }

    /**
     * 2) GET /login: muestro la página de login.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "US_Login";
    }

    /**
     * 3) GET /register: muestro la página de registro.
     */
    @GetMapping("/register")
    public String registerPage() {
        return "US_Register";
    }

    /**
     * 4) GET /tablaUsuarios: muestro la tabla de usuarios (como antes).
     */
    @GetMapping("/tablaUsuarios")
    public String tablaUsuarios(Model model) {
        List<Usuario> usuarios = repo.findAll();
        model.addAttribute("usuarios", usuarios);
        return "tablaUsuarios";
    }

    @GetMapping("/pago2")
    public String pagoBPage() {
        return "US_PagoB";
    }

    @GetMapping("/pago3")
    public String pagoCPage() {
        return "US_PagoC";
    }

    @GetMapping("/datos-usuario")
    public String datosUsuario() {
        return "US_DatosUsuario"; // sin .html
    }

    @GetMapping("/precios")
    public String mostrarPrecios() {
        return "VA_Precios"; // sin .html, Spring buscará en templates/VA_Precios.html
    }

    @GetMapping("/planes")
    public String planes() {
        return "US_PlanesYPrecios";
    }

    @GetMapping("/pago1")
    public String pagoAPage() {
        return "US_PagoA";
    }

    @GetMapping("/suscripciones")
    public String vistaSuscripciones() {
        return "VA_Suscripciones";
    }

    @GetMapping("/admin-panel")
    public String mostrarPanelAdmin() {
        return "VA_Inicio"; // corresponde a templates/miVistaAdmin.html
    }

    @GetMapping("/VistaSuscripciones")
    public String Boletas() {
        return "VA_ResumenBoletas";
    }

    @GetMapping("/eventos")
    public String Eventos() {
        return "US_Eventos";
    }

    @GetMapping("/eventosAdmin")
    public String AdminEventos() {
        return "VA_Eventos";
    }

    @GetMapping("/asistencias")
    public String Asistencias() {
        return "VA_Asistencias";
    }

    @GetMapping("/Anuncios")
    public String Anuncios() {
        return "US_Anuncios";
    }

    @GetMapping("/TablaUsuarios")
    public String TablaUsuarios() {
        return "tablaUsuarios";
    }


    // … si tienes más páginas (por ejemplo, US_Anuncios, US_PlanesYPrecios, etc.),
    // solo agrega más @GetMapping que devuelvan el nombre de la plantilla.
}
