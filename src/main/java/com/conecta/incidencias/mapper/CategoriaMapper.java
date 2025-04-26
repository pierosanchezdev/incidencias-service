package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.CategoriaRequest;
import com.conecta.incidencias.dto.response.CategoriaResponse;
import com.conecta.incidencias.entity.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toEntity(CategoriaRequest request);
    CategoriaResponse toResponse(Categoria categoria);
}
