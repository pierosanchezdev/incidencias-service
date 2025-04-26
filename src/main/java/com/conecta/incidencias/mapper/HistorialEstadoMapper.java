package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.response.HistorialEstadoResponse;
import com.conecta.incidencias.entity.HistorialEstado;
import com.conecta.incidencias.enums.Estado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistorialEstadoMapper {
    HistorialEstadoResponse toResponse(HistorialEstado historialEstado);

    default String mapEstadoToString(Estado estado) {
        return estado != null ? estado.name() : null;
    }
}
