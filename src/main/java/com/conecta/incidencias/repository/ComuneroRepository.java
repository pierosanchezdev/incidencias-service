package com.conecta.incidencias.repository;

import com.conecta.incidencias.entity.Comunero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComuneroRepository extends JpaRepository<Comunero, Long> {
    Optional<Comunero> findByUsuarioId(Long usuarioId);
    Optional<Comunero> findByUsuarioEmail(String email);

}
