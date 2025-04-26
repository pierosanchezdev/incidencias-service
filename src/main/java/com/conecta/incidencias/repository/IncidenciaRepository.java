package com.conecta.incidencias.repository;

import com.conecta.incidencias.entity.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    List<Incidencia> findByUsuarioId(Long usuarioId);
}
