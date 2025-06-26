package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;
import com.conecta.incidencias.entity.Operador;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.enums.RolUsuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-26T13:57:57-0500",
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

        operadorResponse.emailUsuario( operadorUsuarioEmail( operador ) );
        operadorResponse.username( operadorUsuarioUsername( operador ) );
        RolUsuario rol = operadorUsuarioRol( operador );
        if ( rol != null ) {
            operadorResponse.rol( rol.name() );
        }
        operadorResponse.usuarioId( operadorUsuarioId( operador ) );
        operadorResponse.id( operador.getId() );
        operadorResponse.nombres( operador.getNombres() );
        operadorResponse.apellidos( operador.getApellidos() );
        operadorResponse.cargo( operador.getCargo() );
        operadorResponse.telefono( operador.getTelefono() );

        return operadorResponse.build();
    }

    private String operadorUsuarioEmail(Operador operador) {
        if ( operador == null ) {
            return null;
        }
        Usuario usuario = operador.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        String email = usuario.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String operadorUsuarioUsername(Operador operador) {
        if ( operador == null ) {
            return null;
        }
        Usuario usuario = operador.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        String username = usuario.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private RolUsuario operadorUsuarioRol(Operador operador) {
        if ( operador == null ) {
            return null;
        }
        Usuario usuario = operador.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        RolUsuario rol = usuario.getRol();
        if ( rol == null ) {
            return null;
        }
        return rol;
    }

    private Long operadorUsuarioId(Operador operador) {
        if ( operador == null ) {
            return null;
        }
        Usuario usuario = operador.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        Long id = usuario.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
