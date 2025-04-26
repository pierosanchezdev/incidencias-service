package com.conecta.incidencias.application;

import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;

import java.util.Optional;

public interface OperadorService {
    OperadorResponse crearOperador(OperadorRequest request);
    Optional<OperadorResponse> obtenerOperadorPorId(Long id);
    OperadorResponse actualizarOperador(Long id, OperadorRequest request);
}
