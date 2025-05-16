package com.conecta.incidencias.mapper;


import com.conecta.incidencias.dto.request.UbicacionRequest;
import com.conecta.incidencias.entity.UbicacionGeografica;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UbicacionMapper {
    UbicacionGeografica toEntity(UbicacionRequest request);
}
