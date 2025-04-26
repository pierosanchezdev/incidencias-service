package com.conecta.incidencias.application;

import com.conecta.incidencias.dto.request.CambioEstadoRequest;
import com.conecta.incidencias.dto.response.HistorialEstadoResponse;

import java.util.List;

public interface HistorialEstadoService {

    HistorialEstadoResponse registrarCambioEstado(Long incidenciaId, Long operadorId, CambioEstadoRequest request);
    List<HistorialEstadoResponse> listarHistorialPorIncidencia(Long incidenciaId);
}
