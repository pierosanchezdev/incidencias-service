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
    OperadorResponse toResponse(Operador operador);
}
