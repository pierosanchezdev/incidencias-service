package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.UsuarioRequest;
import com.conecta.incidencias.dto.response.UsuarioResponse;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.enums.RolUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioMapperTest {

    private UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        usuarioMapper = new UsuarioMapperImpl();
    }

    @Test
    void toEntity_deberiaMapearRequestAEntidad() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest();
        request.setUsername("comunero1");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRol(RolUsuario.COMUNERO);

        // Act
        Usuario usuario = usuarioMapper.toEntity(request);

        // Assert
        assertThat(usuario).isNotNull();
        assertThat(usuario.getUsername()).isEqualTo("comunero1");
        assertThat(usuario.getEmail()).isEqualTo("test@example.com");
        assertThat(usuario.getPassword()).isEqualTo("password123");
    }

    @Test
    void toResponse_deberiaMapearEntidadAResponse() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("comunero1");
        usuario.setEmail("test@example.com");
        usuario.setPassword("password123");

        // Act
        UsuarioResponse response = usuarioMapper.toResponse(usuario);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("comunero1");
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void toResponse_deberiaMapearCampoActivoCorrectamente() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUsername("operador2");
        usuario.setEmail("op@example.com");
        usuario.setPassword("secreto");
        usuario.setRol(RolUsuario.OPERADOR);
        usuario.setActivo(true);

        // Act
        UsuarioResponse response = usuarioMapper.toResponse(usuario);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getUsername()).isEqualTo("operador2");
        assertThat(response.getEmail()).isEqualTo("op@example.com");
        assertThat(response.getRol()).isEqualTo(RolUsuario.OPERADOR);
        assertThat(response.isActivo()).isTrue();
    }

    @Test
    void toResponse_deberiaRetornarNullSiUsuarioEsNull() {
        UsuarioResponse response = usuarioMapper.toResponse(null);
        assertThat(response).isNull();
    }


}
