package com.conecta.incidencias.application;

import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.IncidenciaResponse;

import java.util.List;
import java.util.Optional;

public interface IncidenciaService {

    IncidenciaResponse crearIncidencia(IncidenciaRequest request);
    Optional<IncidenciaResponse> obtenerIncidenciaPorId(Long id);
    List<IncidenciaResponse> listarIncidencias();
    List<IncidenciaResponse> listarIncidenciasPorUsuario(Long usuarioId);
    IncidenciaResponse actualizarIncidencia(Long id, IncidenciaRequest request);
}
