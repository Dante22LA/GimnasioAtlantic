package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Usuario;
import com.gimnasio.demo.payload.LoginRequest;
import com.gimnasio.demo.payload.RegisterRequest;
import com.gimnasio.demo.repository.UsuarioRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController  // Indica que esta clase atiende peticiones REST y que sus métodos devuelven directamente el cuerpo de la respuesta.
public class AuthController {

    private final UsuarioRepository repo;  // Repositorio para acceder a los datos de usuarios (CRUD).

    // Constructor que inyecta el repositorio (Spring lo gestiona automáticamente).
    public AuthController(UsuarioRepository repo) {
        this.repo = repo;
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     * URL: POST /register
     * @param req Datos de registro (documento, password, nombres, apellidos).
     * @return 200 OK si se registra; 400 Bad Request si ya existe.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        // Verifica si ya existe un usuario con el mismo documento (clave primaria).
        if (repo.existsById(req.getDocumento())) {
            // Si existe, retorna 400 Bad Request con mensaje de error.
            return ResponseEntity
                     .status(HttpStatus.BAD_REQUEST)
                     .body("Ya existe un usuario con ese documento");
        }
        // Crea la entidad Usuario y asigna los datos del request.
        Usuario u = new Usuario();
        u.setDocumento(req.getDocumento());
        u.setPassword(req.getPassword());
        u.setNombres(req.getNombres());
        u.setApellidos(req.getApellidos());
        u.setRango("usuario");  // Asigna un rol predeterminado.

        // Persiste el nuevo usuario en la base de datos.
        repo.save(u);

        // Retorna 200 OK sin cuerpo.
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para autenticar (login) a un usuario.
     * URL: POST /login
     * @param req Datos de login (documento y password).
     * @return 200 OK si las credenciales coinciden; 401 Unauthorized si fallan.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        // Busca el usuario por documento (ID).
        Optional<Usuario> usr = repo.findById(req.getDocumento());

        // Comprueba que el usuario exista y que el password coincida.
        if (usr.isPresent() && usr.get().getPassword().equals(req.getPassword())) {
            // Credenciales válidas: retorna 200 OK.
            return ResponseEntity.ok().build();
        }

        // Credenciales inválidas: retorna 401 Unauthorized.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
