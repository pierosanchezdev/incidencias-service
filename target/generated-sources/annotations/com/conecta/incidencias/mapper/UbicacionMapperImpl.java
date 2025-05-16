package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.UbicacionRequest;
import com.conecta.incidencias.entity.UbicacionGeografica;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-16T10:53:35-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class UbicacionMapperImpl implements UbicacionMapper {

    @Override
    public UbicacionGeografica toEntity(UbicacionRequest request) {
        if ( request == null ) {
            return null;
        }

        UbicacionGeografica.UbicacionGeograficaBuilder ubicacionGeografica = UbicacionGeografica.builder();

        ubicacionGeografica.pais( request.getPais() );
        ubicacionGeografica.departamento( request.getDepartamento() );
        ubicacionGeografica.provincia( request.getProvincia() );
        ubicacionGeografica.distrito( request.getDistrito() );
        ubicacionGeografica.ubigeo( request.getUbigeo() );
        ubicacionGeografica.codigoPostal( request.getCodigoPostal() );
        ubicacionGeografica.nombreLocalidad( request.getNombreLocalidad() );
        if ( request.getLatitud() != null ) {
            ubicacionGeografica.latitud( request.getLatitud().doubleValue() );
        }
        if ( request.getLongitud() != null ) {
            ubicacionGeografica.longitud( request.getLongitud().doubleValue() );
        }

        return ubicacionGeografica.build();
    }
}
