// AuthController.java (o UserController.java)
package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Usuario;
import com.gimnasio.demo.payload.LoginRequest;
import com.gimnasio.demo.payload.RegisterRequest;
import com.gimnasio.demo.repository.UsuarioRepository;
import com.gimnasio.demo.validator.DocumentoValidator;

import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map; // Importar esto si usas Map para la respuesta del login
import java.util.Collections; // Importar esto si usas Collections.singletonMap

@CrossOrigin(origins = "*")
@RestController
public class AuthController { // Podrías renombrar a UserController si manejas más que solo auth

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository repo) {
        this.repo = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // ... (Tu método @PostMapping("/register") existente aquí) ...

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

        // ✅ Validar que el documento sea un DNI válido (exactamente 8 dígitos
        // numéricos)
        if (!DocumentoValidator.esDNIValido(req.getDocumento())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("El documento debe contener exactamente 8 dígitos numéricos.");
        }

        // Validar si el documento ya existe
        if (repo.existsById(req.getDocumento())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ya existe un usuario con ese documento");
        }

        // Crear un nuevo usuario con los datos del request
        Usuario nuevo = new Usuario();
        nuevo.setDocumento(req.getDocumento());
        nuevo.setNombres(req.getNombres());
        nuevo.setApellidos(req.getApellidos());
        nuevo.setTelefono(req.getTelefono());
        nuevo.setRango(req.getRango());
        // Encriptar la contraseña antes de guardarla
        String hashedPassword = passwordEncoder.encode(req.getPassword());
        nuevo.setPassword(hashedPassword);

        // Guardar en la base de datos
        repo.save(nuevo);

        return ResponseEntity.ok("Usuario registrado exitosamente.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<Usuario> usrOpt = repo.findById(req.getDocumento());

        if (usrOpt.isPresent()) {
            Usuario usr = usrOpt.get();
            if (passwordEncoder.matches(req.getPassword(), usr.getPassword())) {
                return ResponseEntity.ok(Collections.singletonMap("dni", usr.getDocumento()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // *** NUEVO ENDPOINT: Obtener datos de un usuario por DNI ***
    @GetMapping("/api/usuarios/{dni}")
    public ResponseEntity<?> getUsuarioByDni(@PathVariable String dni) {
        Optional<Usuario> usrOpt = repo.findById(dni);

        if (usrOpt.isPresent()) {
            Usuario usr = usrOpt.get();
            // Creamos un objeto para devolver solo los datos que queremos exponer
            // ¡NO devuelvas la contraseña hasheada!
            Map<String, String> userData = new java.util.HashMap<>();
            userData.put("documento", usr.getDocumento());
            userData.put("nombres", usr.getNombres());
            userData.put("apellidos", usr.getApellidos());
            userData.put("telefono", usr.getTelefono());
            // No enviar la contraseña aquí por seguridad.

            return ResponseEntity.ok(userData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    @DeleteMapping("/api/usuarios/{dni}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String dni) {
        if (repo.existsById(dni)) {
            repo.deleteById(dni);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }



    @GetMapping("/api/usuarios")
    public List<Usuario> listarUsuarios() {
        return repo.findAll(); // Envía lista completa como JSON
    }

    // *** NUEVO ENDPOINT: Actualizar datos de un usuario ***
    // Tendrás que crear una clase "UpdateRequest" o "UsuarioDto" si no la tienes
    // con los campos que se pueden actualizar (nombres, apellidos, telefono,
    // contraseña).
    @PutMapping("/api/usuarios") // O @PatchMapping si es actualización parcial
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario updateRequest) {
        // En un escenario real, deberías validar la identidad del usuario que actualiza
        // (ej. con un token JWT en el encabezado de la petición).

        Optional<Usuario> usrOpt = repo.findById(updateRequest.getDocumento());

        if (usrOpt.isPresent()) {
            Usuario existingUser = usrOpt.get();

            // Actualizar solo los campos que se envían y no son nulos
            if (updateRequest.getNombres() != null) {
                existingUser.setNombres(updateRequest.getNombres());
            }
            if (updateRequest.getApellidos() != null) {
                existingUser.setApellidos(updateRequest.getApellidos());
            }
            if (updateRequest.getTelefono() != null) {
                existingUser.setTelefono(updateRequest.getTelefono());
            }
            // Si la nueva contraseña no está vacía, hashearla y actualizarla
            if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(updateRequest.getPassword());
                existingUser.setPassword(hashedPassword);
            }

            repo.save(existingUser); // Guarda los cambios en la base de datos
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado para actualizar.");
        }
    }
}