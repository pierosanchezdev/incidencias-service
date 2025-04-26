package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.UsuarioRequest;
import com.conecta.incidencias.dto.response.UsuarioResponse;
import com.conecta.incidencias.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioRequest request);
    UsuarioResponse toResponse(Usuario usuario);
}
