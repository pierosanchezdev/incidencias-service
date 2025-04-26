package com.conecta.incidencias.application;

import com.conecta.incidencias.dto.request.UsuarioRequest;
import com.conecta.incidencias.dto.response.UsuarioResponse;

import java.util.Optional;
public interface UsuarioService {
    UsuarioResponse crearUsuario(UsuarioRequest request);
    Optional<UsuarioResponse> obtenerUsuarioPorId(Long id);
    UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request);
}
