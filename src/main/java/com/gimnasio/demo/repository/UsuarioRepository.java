package com.gimnasio.demo.repository;  // Paquete para los repositorios de acceso a datos

import com.gimnasio.demo.model.Usuario;          // Entidad Usuario que mapea la tabla "usuarios"
import org.springframework.data.jpa.repository.JpaRepository;  // Interfaz de Spring Data JPA para operaciones CRUD

// Interfaz que hereda de JpaRepository para manejar la entidad Usuario.
// JpaRepository<Entidad, TipoDeId> proporciona métodos como:
//   - save(): guardar o actualizar una entidad
//   - findById(): buscar por clave primaria
//   - findAll(): obtener todos los registros
//   - existsById(): verificar existencia por clave primaria
//   - delete(): eliminar una entidad
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // No es necesario definir métodos adicionales para operaciones básicas,
    // Spring los implementa automáticamente en tiempo de ejecución.
}
