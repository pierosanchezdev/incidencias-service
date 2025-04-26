package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ArchivoRequest;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import com.conecta.incidencias.entity.Archivo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T13:31:46-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class ArchivoMapperImpl implements ArchivoMapper {

    @Override
    public Archivo toEntity(ArchivoRequest request) {
        if ( request == null ) {
            return null;
        }

        Archivo.ArchivoBuilder archivo = Archivo.builder();

        archivo.url( request.getUrl() );
        archivo.tipo( request.getTipo() );
        archivo.tamanoBytes( request.getTamanoBytes() );

        return archivo.build();
    }

    @Override
    public ArchivoResponse toResponse(Archivo archivo) {
        if ( archivo == null ) {
            return null;
        }

        ArchivoResponse.ArchivoResponseBuilder archivoResponse = ArchivoResponse.builder();

        archivoResponse.id( archivo.getId() );
        archivoResponse.url( archivo.getUrl() );
        archivoResponse.tipo( archivo.getTipo() );
        archivoResponse.tamanoBytes( archivo.getTamanoBytes() );
        archivoResponse.fechaSubida( archivo.getFechaSubida() );

        return archivoResponse.build();
    }
}
