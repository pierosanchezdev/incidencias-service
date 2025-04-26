package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;
import com.conecta.incidencias.entity.Operador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OperadorMapperTest {

    private OperadorMapper operadorMapper;

    @BeforeEach
    void setUp() {
        operadorMapper = new OperadorMapperImpl();
    }

    @Test
    void toEntity_deberiaMapearRequestAEntidad() {
        // Arrange
        OperadorRequest request = new OperadorRequest();
        request.setNombres("Juan");
        request.setApellidos("Pérez");
        request.setCargo("Supervisor");
        request.setTelefono("987654321");

        // Act
        Operador operador = operadorMapper.toEntity(request);

        // Assert
        assertThat(operador).isNotNull();
        assertThat(operador.getNombres()).isEqualTo("Juan");
        assertThat(operador.getApellidos()).isEqualTo("Pérez");
        assertThat(operador.getCargo()).isEqualTo("Supervisor");
        assertThat(operador.getTelefono()).isEqualTo("987654321");
    }

    @Test
    void toResponse_deberiaMapearEntidadAResponse() {
        // Arrange
        Operador operador = new Operador();
        operador.setId(1L);
        operador.setNombres("Juan");
        operador.setApellidos("Pérez");
        operador.setCargo("Supervisor");
        operador.setTelefono("987654321");

        // Act
        OperadorResponse response = operadorMapper.toResponse(operador);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNombres()).isEqualTo("Juan");
        assertThat(response.getApellidos()).isEqualTo("Pérez");
        assertThat(response.getCargo()).isEqualTo("Supervisor");
        assertThat(response.getTelefono()).isEqualTo("987654321");
    }
}
