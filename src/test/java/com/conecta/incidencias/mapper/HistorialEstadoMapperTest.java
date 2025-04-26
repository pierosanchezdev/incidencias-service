package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.response.HistorialEstadoResponse;
import com.conecta.incidencias.entity.HistorialEstado;
import com.conecta.incidencias.enums.Estado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HistorialEstadoMapperTest {

    private HistorialEstadoMapper historialEstadoMapper;

    @BeforeEach
    void setUp() {
        historialEstadoMapper = new HistorialEstadoMapperImpl();
    }

    @Test
    void toResponse_deberiaMapearEntidadAResponse() {
        // Arrange
        HistorialEstado historialEstado = new HistorialEstado();
        historialEstado.setId(1L);
        historialEstado.setEstadoAnterior(Estado.EN_PROCESO);
        historialEstado.setEstadoNuevo(Estado.RESUELTA);
        historialEstado.setComentario("Problema solucionado satisfactoriamente.");

        // Act
        HistorialEstadoResponse response = historialEstadoMapper.toResponse(historialEstado);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getEstadoAnterior().toString()).isEqualTo("EN_PROCESO");
        assertThat(response.getEstadoNuevo().toString()).isEqualTo("RESUELTA");
        assertThat(response.getComentario()).isEqualTo("Problema solucionado satisfactoriamente.");
    }
}
