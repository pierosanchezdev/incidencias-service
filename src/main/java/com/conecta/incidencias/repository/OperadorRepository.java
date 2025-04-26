package com.conecta.incidencias.repository;

import com.conecta.incidencias.entity.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperadorRepository extends JpaRepository<Operador, Long> {
    Optional<Operador> findByUsuarioId(Long usuarioId);
}
