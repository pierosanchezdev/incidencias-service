package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import com.conecta.incidencias.entity.Comunero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComuneroMapper {
    Comunero toEntity(ComuneroRequest request);
    ComuneroResponse toResponse(Comunero comunero);
}
