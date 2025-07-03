package com.gimnasio.demo.repository;

import com.gimnasio.demo.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    // Puedes agregar métodos personalizados si lo necesitas, por ahora no es necesario
}

