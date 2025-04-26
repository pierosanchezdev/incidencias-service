package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.dto.request.CambioEstadoRequest;
import com.conecta.incidencias.dto.response.HistorialEstadoResponse;
import com.conecta.incidencias.entity.HistorialEstado;
import com.conecta.incidencias.entity.Incidencia;
import com.conecta.incidencias.entity.Operador;
import com.conecta.incidencias.enums.Estado;
import com.conecta.incidencias.mapper.HistorialEstadoMapper;
import com.conecta.incidencias.repository.HistorialEstadoRepository;
import com.conecta.incidencias.repository.IncidenciaRepository;
import com.conecta.incidencias.repository.OperadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class HistorialEstadoServiceImplTest {

    @Mock
    private HistorialEstadoRepository historialEstadoRepository;

    @Mock
    private IncidenciaRepository incidenciaRepository;

    @Mock
    private OperadorRepository operadorRepository;

    @Mock
    private HistorialEstadoMapper historialEstadoMapper;

    @InjectMocks
    private HistorialEstadoServiceImpl historialEstadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarCambioEstado_deberiaRegistrarCambioYRetornarResponse() {
        // Arrange
        Long incidenciaId = 1L;
        Long operadorId = 2L;
        CambioEstadoRequest request = new CambioEstadoRequest();
        request.setEstadoNuevo(Estado.RESUELTA);
        request.setComentario("Se resolvió la incidencia.");

        Incidencia incidencia = new Incidencia();
        incidencia.setEstado(Estado.EN_PROCESO); // ✅ Estado anterior real

        Operador operador = new Operador();
        HistorialEstado historialGuardado = new HistorialEstado();
        historialGuardado.setEstadoAnterior(Estado.EN_PROCESO);
        historialGuardado.setEstadoNuevo(Estado.RESUELTA);

        HistorialEstadoResponse responseEsperado = new HistorialEstadoResponse();
        responseEsperado.setId(1L);
        responseEsperado.setEstadoAnterior(Estado.EN_PROCESO);
        responseEsperado.setEstadoNuevo(Estado.RESUELTA);

        when(incidenciaRepository.findById(incidenciaId)).thenReturn(Optional.of(incidencia));
        when(operadorRepository.findById(operadorId)).thenReturn(Optional.of(operador));
        when(historialEstadoRepository.save(any(HistorialEstado.class))).thenReturn(historialGuardado);
        when(historialEstadoMapper.toResponse(historialGuardado)).thenReturn(responseEsperado);

        // Act
        HistorialEstadoResponse resultado = historialEstadoService.registrarCambioEstado(incidenciaId, operadorId, request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEstadoAnterior()).isEqualTo(Estado.EN_PROCESO);
        assertThat(resultado.getEstadoNuevo()).isEqualTo(Estado.RESUELTA);
    }


    @Test
    void registrarCambioEstado_deberiaLanzarExcepcionCuandoIncidenciaNoExiste() {
        // Arrange
        Long incidenciaId = 1L;
        Long operadorId = 2L;
        CambioEstadoRequest request = new CambioEstadoRequest();

        when(incidenciaRepository.findById(incidenciaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> historialEstadoService.registrarCambioEstado(incidenciaId, operadorId, request));
    }

    @Test
    void listarHistorialPorIncidencia_deberiaRetornarListaDeHistorial() {
        // Arrange
        Long incidenciaId = 1L;
        HistorialEstado historial = new HistorialEstado();
        HistorialEstadoResponse response = new HistorialEstadoResponse();
        response.setId(1L);

        when(historialEstadoRepository.findByIncidenciaIdOrderByFechaCambioAsc(incidenciaId))
                .thenReturn(Collections.singletonList(historial));
        when(historialEstadoMapper.toResponse(historial)).thenReturn(response);

        // Act
        List<HistorialEstadoResponse> resultado = historialEstadoService.listarHistorialPorIncidencia(incidenciaId);

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }
}
