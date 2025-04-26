package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import com.conecta.incidencias.entity.Comunero;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T13:31:46-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class ComuneroMapperImpl implements ComuneroMapper {

    @Override
    public Comunero toEntity(ComuneroRequest request) {
        if ( request == null ) {
            return null;
        }

        Comunero.ComuneroBuilder comunero = Comunero.builder();

        comunero.nombres( request.getNombres() );
        comunero.apellidos( request.getApellidos() );
        comunero.tipoDocumento( request.getTipoDocumento() );
        comunero.numeroDocumento( request.getNumeroDocumento() );
        comunero.telefono( request.getTelefono() );
        comunero.correo( request.getCorreo() );

        return comunero.build();
    }

    @Override
    public ComuneroResponse toResponse(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }

        ComuneroResponse.ComuneroResponseBuilder comuneroResponse = ComuneroResponse.builder();

        comuneroResponse.id( comunero.getId() );
        comuneroResponse.nombres( comunero.getNombres() );
        comuneroResponse.apellidos( comunero.getApellidos() );
        comuneroResponse.tipoDocumento( comunero.getTipoDocumento() );
        comuneroResponse.numeroDocumento( comunero.getNumeroDocumento() );
        comuneroResponse.telefono( comunero.getTelefono() );
        comuneroResponse.correo( comunero.getCorreo() );

        return comuneroResponse.build();
    }
}
