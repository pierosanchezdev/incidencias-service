package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import com.conecta.incidencias.entity.Comunero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComuneroMapper {
    Comunero toEntity(ComuneroRequest request);
    @Mapping(source = "usuario.email", target = "emailUsuario")
    @Mapping(source = "usuario.username", target = "username")
    @Mapping(source = "usuario.rol", target = "rol")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "ubicacion.pais", target = "pais")
    @Mapping(source = "ubicacion.departamento", target = "departamento")
    @Mapping(source = "ubicacion.provincia", target = "provincia")
    @Mapping(source = "ubicacion.distrito", target = "distrito")
    @Mapping(source = "ubicacion.ubigeo", target = "ubigeo")
    @Mapping(source = "ubicacion.nombreLocalidad", target = "nombreLocalidad")
    @Mapping(source = "ubicacion.latitud", target = "latitud")
    @Mapping(source = "ubicacion.longitud", target = "longitud")
    ComuneroResponse toResponse(Comunero comunero);

}
