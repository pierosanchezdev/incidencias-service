package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;
import com.conecta.incidencias.entity.Operador;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-16T10:53:35-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class OperadorMapperImpl implements OperadorMapper {

    @Override
    public Operador toEntity(OperadorRequest request) {
        if ( request == null ) {
            return null;
        }

        Operador.OperadorBuilder operador = Operador.builder();

        operador.nombres( request.getNombres() );
        operador.apellidos( request.getApellidos() );
        operador.cargo( request.getCargo() );
        operador.telefono( request.getTelefono() );

        return operador.build();
    }

    @Override
    public OperadorResponse toResponse(Operador operador) {
        if ( operador == null ) {
            return null;
        }

        OperadorResponse.OperadorResponseBuilder operadorResponse = OperadorResponse.builder();

        operadorResponse.id( operador.getId() );
        operadorResponse.nombres( operador.getNombres() );
        operadorResponse.apellidos( operador.getApellidos() );
        operadorResponse.cargo( operador.getCargo() );
        operadorResponse.telefono( operador.getTelefono() );

        return operadorResponse.build();
    }
}
