package com.conecta.incidencias.application;

import com.conecta.incidencias.dto.response.ArchivoResponse;

import java.util.List;
import java.util.Optional;

public interface ArchivoService {

    Optional<ArchivoResponse> obtenerArchivoPorId(Long id);
    List<ArchivoResponse> listarArchivosPorIncidencia(Long incidenciaId);
    void eliminarArchivo(Long id);
}
