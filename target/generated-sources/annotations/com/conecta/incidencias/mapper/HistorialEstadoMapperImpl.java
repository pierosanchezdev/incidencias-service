package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.response.HistorialEstadoResponse;
import com.conecta.incidencias.entity.HistorialEstado;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T13:31:46-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class HistorialEstadoMapperImpl implements HistorialEstadoMapper {

    @Override
    public HistorialEstadoResponse toResponse(HistorialEstado historialEstado) {
        if ( historialEstado == null ) {
            return null;
        }

        HistorialEstadoResponse.HistorialEstadoResponseBuilder historialEstadoResponse = HistorialEstadoResponse.builder();

        historialEstadoResponse.id( historialEstado.getId() );
        historialEstadoResponse.estadoNuevo( historialEstado.getEstadoNuevo() );
        historialEstadoResponse.estadoAnterior( historialEstado.getEstadoAnterior() );
        historialEstadoResponse.comentario( historialEstado.getComentario() );
        historialEstadoResponse.fechaCambio( historialEstado.getFechaCambio() );

        return historialEstadoResponse.build();
    }
}
