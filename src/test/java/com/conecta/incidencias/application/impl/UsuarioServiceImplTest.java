package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.dto.request.UsuarioRequest;
import com.conecta.incidencias.dto.response.UsuarioResponse;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.enums.RolUsuario;
import com.conecta.incidencias.mapper.UsuarioMapper;
import com.conecta.incidencias.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_deberiaCrearUsuarioYRetornarResponse() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setRol(RolUsuario.COMUNERO);

        Usuario usuario = new Usuario();
        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);

        UsuarioResponse responseEsperado = new UsuarioResponse();
        responseEsperado.setId(1L);

        when(usuarioMapper.toEntity(request)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuarioGuardado);
        when(usuarioMapper.toResponse(usuarioGuardado)).thenReturn(responseEsperado);

        // Act
        UsuarioResponse resultado = usuarioService.crearUsuario(request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void obtenerUsuarioPorId_deberiaRetornarUsuarioCuandoExiste() {
        // Arrange
        Long userId = 1L;
        Usuario usuario = new Usuario();
        UsuarioResponse response = new UsuarioResponse();
        response.setId(userId);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        // Act
        Optional<UsuarioResponse> resultado = usuarioService.obtenerUsuarioPorId(userId);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(userId);
    }

    @Test
    void actualizarUsuario_deberiaActualizarUsuarioCuandoExiste() {
        // Arrange
        Long userId = 1L;
        UsuarioRequest request = new UsuarioRequest();
        request.setEmail("nuevo@example.com");
        request.setPassword("nuevaPassword");
        request.setRol(RolUsuario.OPERADOR);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(userId);

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(userId);

        UsuarioResponse responseEsperado = new UsuarioResponse();
        responseEsperado.setId(userId);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioActualizado);
        when(usuarioMapper.toResponse(usuarioActualizado)).thenReturn(responseEsperado);

        // Act
        UsuarioResponse resultado = usuarioService.actualizarUsuario(userId, request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(userId);
    }

    @Test
    void actualizarUsuario_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        Long userId = 1L;
        UsuarioRequest request = new UsuarioRequest();

        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usuarioService.actualizarUsuario(userId, request));
    }
}
