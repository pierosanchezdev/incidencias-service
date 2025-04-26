package com.conecta.incidencias.repository;

import com.conecta.incidencias.entity.UbicacionGeografica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbicacionGeograficaRepository extends JpaRepository<UbicacionGeografica, Long> {
    Optional<UbicacionGeografica> findByUbigeo(String ubigeo);
}
