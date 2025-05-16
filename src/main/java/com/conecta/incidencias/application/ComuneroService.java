package com.conecta.incidencias.application;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;

import java.util.Optional;

public interface ComuneroService {
    ComuneroResponse crearComunero(ComuneroRequest request);
    Optional<ComuneroResponse> obtenerComuneroPorId(Long id);
    ComuneroResponse actualizarComunero(Long id, ComuneroRequest request);
    ComuneroResponse findByUsuarioEmail(String email);

}
