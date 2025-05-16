package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.UsuarioRequest;
import com.conecta.incidencias.dto.response.UsuarioResponse;
import com.conecta.incidencias.entity.Usuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-16T10:53:35-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioRequest request) {
        if ( request == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.username( request.getUsername() );
        usuario.email( request.getEmail() );
        usuario.password( request.getPassword() );
        usuario.rol( request.getRol() );

        return usuario.build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioResponse.UsuarioResponseBuilder usuarioResponse = UsuarioResponse.builder();

        usuarioResponse.id( usuario.getId() );
        usuarioResponse.username( usuario.getUsername() );
        usuarioResponse.email( usuario.getEmail() );
        usuarioResponse.rol( usuario.getRol() );
        usuarioResponse.activo( usuario.isActivo() );

        return usuarioResponse.build();
    }
}
