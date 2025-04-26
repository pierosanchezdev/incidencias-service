package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ArchivoRequest;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import com.conecta.incidencias.entity.Archivo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArchivoMapper {
    Archivo toEntity(ArchivoRequest request);
    ArchivoResponse toResponse(Archivo archivo);
}
