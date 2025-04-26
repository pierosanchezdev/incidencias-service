package com.conecta.incidencias.repository;

import com.conecta.incidencias.entity.HistorialEstado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialEstadoRepository extends JpaRepository<HistorialEstado, Long> {
    List<HistorialEstado> findByIncidenciaIdOrderByFechaCambioAsc(Long incidenciaId);
}
