package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import com.conecta.incidencias.entity.Comunero;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.mapper.ComuneroMapper;
import com.conecta.incidencias.repository.ComuneroRepository;
import com.conecta.incidencias.repository.UbicacionGeograficaRepository;
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

class ComuneroServiceImplTest {

    @Mock
    private ComuneroRepository comuneroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UbicacionGeograficaRepository ubicacionRepository;

    @Mock
    private ComuneroMapper comuneroMapper;

    @InjectMocks
    private ComuneroServiceImpl comuneroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearComunero_deberiaCrearComuneroYRetornarResponse() {
        // Arrange
        ComuneroRequest request = new ComuneroRequest();
        request.setUsuarioId(1L);
        request.setUbicacionId(1L);

        Usuario usuario = new Usuario();
        UbicacionGeografica ubicacion = new UbicacionGeografica();
        Comunero comunero = new Comunero();
        Comunero comuneroGuardado = new Comunero();
        comuneroGuardado.setId(1L);

        ComuneroResponse responseEsperado = new ComuneroResponse();
        responseEsperado.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(ubicacionRepository.findById(1L)).thenReturn(Optional.of(ubicacion));
        when(comuneroMapper.toEntity(request)).thenReturn(comunero);
        when(comuneroRepository.save(comunero)).thenReturn(comuneroGuardado);
        when(comuneroMapper.toResponse(comuneroGuardado)).thenReturn(responseEsperado);

        // Act
        ComuneroResponse resultado = comuneroService.crearComunero(request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void obtenerComuneroPorId_deberiaRetornarComuneroCuandoExiste() {
        // Arrange
        Long comuneroId = 1L;
        Comunero comunero = new Comunero();
        ComuneroResponse response = new ComuneroResponse();
        response.setId(comuneroId);

        when(comuneroRepository.findById(comuneroId)).thenReturn(Optional.of(comunero));
        when(comuneroMapper.toResponse(comunero)).thenReturn(response);

        // Act
        Optional<ComuneroResponse> resultado = comuneroService.obtenerComuneroPorId(comuneroId);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(comuneroId);
    }

    @Test
    void actualizarComunero_deberiaActualizarComuneroCuandoExiste() {
        // Arrange
        Long comuneroId = 1L;
        ComuneroRequest request = new ComuneroRequest();
        request.setNombres("Nuevo Nombre");
        request.setApellidos("Nuevo Apellido");
        request.setTelefono("999999999");
        request.setCorreo("nuevo@example.com");
        request.setTipoDocumento("DNI");
        request.setNumeroDocumento("12345678");
        request.setUbicacionId(2L);

        Comunero comuneroExistente = new Comunero();
        comuneroExistente.setId(comuneroId);

        UbicacionGeografica nuevaUbicacion = new UbicacionGeografica();

        Comunero comuneroActualizado = new Comunero();
        comuneroActualizado.setId(comuneroId);

        ComuneroResponse responseEsperado = new ComuneroResponse();
        responseEsperado.setId(comuneroId);

        when(comuneroRepository.findById(comuneroId)).thenReturn(Optional.of(comuneroExistente));
        when(ubicacionRepository.findById(2L)).thenReturn(Optional.of(nuevaUbicacion));
        when(comuneroRepository.save(comuneroExistente)).thenReturn(comuneroActualizado);
        when(comuneroMapper.toResponse(comuneroActualizado)).thenReturn(responseEsperado);

        // Act
        ComuneroResponse resultado = comuneroService.actualizarComunero(comuneroId, request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(comuneroId);
    }

    @Test
    void actualizarComunero_deberiaLanzarExcepcionCuandoComuneroNoExiste() {
        // Arrange
        Long comuneroId = 1L;
        ComuneroRequest request = new ComuneroRequest();

        when(comuneroRepository.findById(comuneroId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> comuneroService.actualizarComunero(comuneroId, request));
    }
}
