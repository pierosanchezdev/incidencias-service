package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import com.conecta.incidencias.entity.Comunero;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.enums.RolUsuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-16T11:43:57-0500",
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

        comuneroResponse.emailUsuario( comuneroUsuarioEmail( comunero ) );
        comuneroResponse.username( comuneroUsuarioUsername( comunero ) );
        RolUsuario rol = comuneroUsuarioRol( comunero );
        if ( rol != null ) {
            comuneroResponse.rol( rol.name() );
        }
        comuneroResponse.usuarioId( comuneroUsuarioId( comunero ) );
        comuneroResponse.pais( comuneroUbicacionPais( comunero ) );
        comuneroResponse.departamento( comuneroUbicacionDepartamento( comunero ) );
        comuneroResponse.provincia( comuneroUbicacionProvincia( comunero ) );
        comuneroResponse.distrito( comuneroUbicacionDistrito( comunero ) );
        comuneroResponse.ubigeo( comuneroUbicacionUbigeo( comunero ) );
        comuneroResponse.nombreLocalidad( comuneroUbicacionNombreLocalidad( comunero ) );
        comuneroResponse.latitud( comuneroUbicacionLatitud( comunero ) );
        comuneroResponse.longitud( comuneroUbicacionLongitud( comunero ) );
        comuneroResponse.id( comunero.getId() );
        comuneroResponse.nombres( comunero.getNombres() );
        comuneroResponse.apellidos( comunero.getApellidos() );
        comuneroResponse.tipoDocumento( comunero.getTipoDocumento() );
        comuneroResponse.numeroDocumento( comunero.getNumeroDocumento() );
        comuneroResponse.telefono( comunero.getTelefono() );
        comuneroResponse.correo( comunero.getCorreo() );

        return comuneroResponse.build();
    }

    private String comuneroUsuarioEmail(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        Usuario usuario = comunero.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        String email = usuario.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String comuneroUsuarioUsername(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        Usuario usuario = comunero.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        String username = usuario.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private RolUsuario comuneroUsuarioRol(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        Usuario usuario = comunero.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        RolUsuario rol = usuario.getRol();
        if ( rol == null ) {
            return null;
        }
        return rol;
    }

    private Long comuneroUsuarioId(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        Usuario usuario = comunero.getUsuario();
        if ( usuario == null ) {
            return null;
        }
        Long id = usuario.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String comuneroUbicacionPais(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = comunero.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        String pais = ubicacion.getPais();
        if ( pais == null ) {
            return null;
        }
        return pais;
    }

    private String comuneroUbicacionDepartamento(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = comunero.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        String departamento = ubicacion.getDepartamento();
        if ( departamento == null ) {
            return null;
        }
        return departamento;
    }

    private String comuneroUbicacionProvincia(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = comunero.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        String provincia = ubicacion.getProvincia();
        if ( provincia == null ) {
            return null;
        }
        return provincia;
    }

    private String comuneroUbicacionDistrito(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = comunero.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        String distrito = ubicacion.getDistrito();
        if ( distrito == null ) {
            return null;
        }
        return distrito;
    }

    private String comuneroUbicacionUbigeo(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = comunero.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        String ubigeo = ubicacion.getUbigeo();
        if ( ubigeo == null ) {
            return null;
        }
        return ubigeo;
    }

    private String comuneroUbicacionNombreLocalidad(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = comunero.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        String nombreLocalidad = ubicacion.getNombreLocalidad();
        if ( nombreLocalidad == null ) {
            return null;
        }
        return nombreLocalidad;
    }

    private Double comuneroUbicacionLatitud(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = comunero.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        Double latitud = ubicacion.getLatitud();
        if ( latitud == null ) {
            return null;
        }
        return latitud;
    }

    private Double comuneroUbicacionLongitud(Comunero comunero) {
        if ( comunero == null ) {
            return null;
        }
        UbicacionGeografica ubicacion = comunero.getUbicacion();
        if ( ubicacion == null ) {
            return null;
        }
        Double longitud = ubicacion.getLongitud();
        if ( longitud == null ) {
            return null;
        }
        return longitud;
    }
}
