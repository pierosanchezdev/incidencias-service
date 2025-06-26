package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;
import com.conecta.incidencias.entity.Operador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OperadorMapper {
    Operador toEntity(OperadorRequest request);

    @Mapping(source = "usuario.email", target = "emailUsuario")
    @Mapping(source = "usuario.username", target = "username")
    @Mapping(source = "usuario.rol", target = "rol")
    @Mapping(source = "usuario.id", target = "usuarioId")
    OperadorResponse toResponse(Operador operador);
}
