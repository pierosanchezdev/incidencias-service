package com.conecta.incidencias.repository;

import com.conecta.incidencias.entity.UbicacionGeografica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbicacionRepository extends JpaRepository<UbicacionGeografica, Integer> {
}
