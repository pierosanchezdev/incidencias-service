package com.conecta.incidencias.repository;

import com.conecta.incidencias.entity.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    List<Archivo> findByIncidenciaId(Long incidenciaId);
}
