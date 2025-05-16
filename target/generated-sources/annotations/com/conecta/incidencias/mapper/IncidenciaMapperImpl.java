package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import com.conecta.incidencias.dto.response.IncidenciaResponse;
import com.conecta.incidencias.entity.Archivo;
import com.conecta.incidencias.entity.Categoria;
import com.conecta.incidencias.entity.Incidencia;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-16T14:18:04-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class IncidenciaMapperImpl implements IncidenciaMapper {

    @Autowired
    private ArchivoMapper archivoMapper;

    @Override
    public Incidencia toEntity(IncidenciaRequest request) {
        if ( request == null ) {
            return null;
        }

        Incidencia.IncidenciaBuilder incidencia = Incidencia.builder();

        incidencia.titulo( request.getTitulo() );
        incidencia.descripcion( request.getDescripcion() );
        incidencia.impacto( request.getImpacto() );
        incidencia.urgencia( request.getUrgencia() );

        return incidencia.build();
    }

    @Override
    public IncidenciaResponse toResponse(Incidencia incidencia) {
        if ( incidencia == null ) {
            return null;
        }

        IncidenciaResponse.IncidenciaResponseBuilder incidenciaResponse = IncidenciaResponse.builder();

        incidenciaResponse.id( incidencia.getId() );
        incidenciaResponse.titulo( incidencia.getTitulo() );
        incidenciaResponse.descripcion( incidencia.getDescripcion() );
        incidenciaResponse.categoriaId( incidenciaCategoriaId( incidencia ) );
        incidenciaResponse.impacto( incidencia.getImpacto() );
        incidenciaResponse.urgencia( incidencia.getUrgencia() );
        incidenciaResponse.estado( incidencia.getEstado() );
        incidenciaResponse.fechaRegistro( incidencia.getFechaRegistro() );
        incidenciaResponse.fechaActualizacion( incidencia.getFechaActualizacion() );
        incidenciaResponse.usuarioId( incidenciaUsuarioId( incidencia ) );
        incidenciaResponse.ubicacionId( incidenciaUbicacionId( incidencia ) );
        incidenciaResponse.latitud( incidenciaUbicacionLatitud( incidencia ) );
        incidenciaResponse.longitud( incidenciaUbicacionLongitud( incidencia ) );
        incidenciaResponse.archivos( archivoListToArchivoResponseList( incidencia.getArchivos() ) );

        return incidenciaResponse.build();
    }

    private Long incidenciaCategoriaId(Incidencia incidencia) {
        if ( incidencia == null ) {
            return null;
        }
        Categoria categoria = incidencia.getCategoria();
        if ( categoria == null ) {
            return null;
        }
        Long id = categoria.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long incidenciaUsuarioId(Incidencia incidencia) {
        if ( incidencia == null ) {
            return null;
        }
        Usuario usuario = incidencia.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        Long id = usuario.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long incidenciaUbicacionId(Incidencia incidencia) {
        if ( incidencia == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = incidencia.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        Long id = ubicacion.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Double incidenciaUbicacionLatitud(Incidencia incidencia) {
        if ( incidencia == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = incidencia.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        Double latitud = ubicacion.getLatitud();
        if ( latitud == null ) {
            return null;
        }
        return latitud;
    }

    private Double incidenciaUbicacionLongitud(Incidencia incidencia) {
        if ( incidencia == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = incidencia.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        Double longitud = ubicacion.getLongitud();
        if ( longitud == null ) {
            return null;
        }
        return longitud;
    }

    protected List<ArchivoResponse> archivoListToArchivoResponseList(List<Archivo> list) {
        if ( list == null ) {
            return null;
        }

        List<ArchivoResponse> list1 = new ArrayList<ArchivoResponse>( list.size() );
        for ( Archivo archivo : list ) {
            list1.add( archivoMapper.toResponse( archivo ) );
        }

        return list1;
    }
}
