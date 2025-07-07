package com.gimnasio.demo.repository;

import com.gimnasio.demo.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta, Integer> {

    // Buscar boletas por DNI del usuario
    List<Boleta> findByDocumentoUsuario(String documentoUsuario);

    // Validar si un usuario ya tiene una boleta para cierto plan (opcional)
    boolean existsByDocumentoUsuarioAndPlan_IdPlan(String documentoUsuario, int idPlan);
}






