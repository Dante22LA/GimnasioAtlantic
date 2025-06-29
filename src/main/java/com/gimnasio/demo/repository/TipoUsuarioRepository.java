package com.gimnasio.demo.repository;

import com.gimnasio.demo.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {

    // Buscar por descripción: "admin" o "usuario"
    Optional<TipoUsuario> findByDescripcion(String descripcion);
}


