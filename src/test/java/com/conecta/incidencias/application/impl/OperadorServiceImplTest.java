package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;
import com.conecta.incidencias.entity.Operador;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.mapper.OperadorMapper;
import com.conecta.incidencias.repository.OperadorRepository;
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

class OperadorServiceImplTest {

    @Mock
    private OperadorRepository operadorRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private OperadorMapper operadorMapper;

    @InjectMocks
    private OperadorServiceImpl operadorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearOperador_deberiaCrearOperadorYRetornarResponse() {
        // Arrange
        OperadorRequest request = new OperadorRequest();
        request.setUsuarioId(1L);

        Usuario usuario = new Usuario();
        Operador operador = new Operador();
        Operador operadorGuardado = new Operador();
        operadorGuardado.setId(1L);

        OperadorResponse responseEsperado = new OperadorResponse();
        responseEsperado.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(operadorMapper.toEntity(request)).thenReturn(operador);
        when(operadorRepository.save(operador)).thenReturn(operadorGuardado);
        when(operadorMapper.toResponse(operadorGuardado)).thenReturn(responseEsperado);

        // Act
        OperadorResponse resultado = operadorService.crearOperador(request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void obtenerOperadorPorId_deberiaRetornarOperadorCuandoExiste() {
        // Arrange
        Long operadorId = 1L;
        Operador operador = new Operador();
        OperadorResponse response = new OperadorResponse();
        response.setId(operadorId);

        when(operadorRepository.findById(operadorId)).thenReturn(Optional.of(operador));
        when(operadorMapper.toResponse(operador)).thenReturn(response);

        // Act
        Optional<OperadorResponse> resultado = operadorService.obtenerOperadorPorId(operadorId);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(operadorId);
    }

    @Test
    void actualizarOperador_deberiaActualizarOperadorCuandoExiste() {
        // Arrange
        Long operadorId = 1L;
        OperadorRequest request = new OperadorRequest();
        request.setNombres("Nuevo Nombre");
        request.setApellidos("Nuevo Apellido");
        request.setCargo("Nuevo Cargo");
        request.setTelefono("999999999");

        Operador operadorExistente = new Operador();
        operadorExistente.setId(operadorId);

        Operador operadorActualizado = new Operador();
        operadorActualizado.setId(operadorId);

        OperadorResponse responseEsperado = new OperadorResponse();
        responseEsperado.setId(operadorId);

        when(operadorRepository.findById(operadorId)).thenReturn(Optional.of(operadorExistente));
        when(operadorRepository.save(operadorExistente)).thenReturn(operadorActualizado);
        when(operadorMapper.toResponse(operadorActualizado)).thenReturn(responseEsperado);

        // Act
        OperadorResponse resultado = operadorService.actualizarOperador(operadorId, request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(operadorId);
    }

    @Test
    void actualizarOperador_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        Long operadorId = 1L;
        OperadorRequest request = new OperadorRequest();

        when(operadorRepository.findById(operadorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> operadorService.actualizarOperador(operadorId, request));
    }
}
