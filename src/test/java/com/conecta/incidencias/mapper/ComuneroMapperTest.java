package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import com.conecta.incidencias.entity.Comunero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComuneroMapperTest {

    private ComuneroMapper comuneroMapper;

    @BeforeEach
    void setUp() {
        comuneroMapper = new ComuneroMapperImpl();
    }

    @Test
    void toEntity_deberiaMapearRequestAEntidad() {
        // Arrange
        ComuneroRequest request = new ComuneroRequest();
        request.setNombres("Ana");
        request.setApellidos("Gonzales");
        request.setTelefono("999888777");
        request.setCorreo("ana@example.com");
        request.setTipoDocumento("DNI");
        request.setNumeroDocumento("12345678");

        // Act
        Comunero comunero = comuneroMapper.toEntity(request);

        // Assert
        assertThat(comunero).isNotNull();
        assertThat(comunero.getNombres()).isEqualTo("Ana");
        assertThat(comunero.getApellidos()).isEqualTo("Gonzales");
        assertThat(comunero.getTelefono()).isEqualTo("999888777");
        assertThat(comunero.getCorreo()).isEqualTo("ana@example.com");
        assertThat(comunero.getTipoDocumento()).isEqualTo("DNI");
        assertThat(comunero.getNumeroDocumento()).isEqualTo("12345678");
    }

    @Test
    void toResponse_deberiaMapearEntidadAResponse() {
        // Arrange
        Comunero comunero = new Comunero();
        comunero.setId(1L);
        comunero.setNombres("Ana");
        comunero.setApellidos("Gonzales");
        comunero.setTelefono("999888777");
        comunero.setCorreo("ana@example.com");

        // Act
        ComuneroResponse response = comuneroMapper.toResponse(comunero);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNombres()).isEqualTo("Ana");
        assertThat(response.getApellidos()).isEqualTo("Gonzales");
        assertThat(response.getTelefono()).isEqualTo("999888777");
        assertThat(response.getCorreo()).isEqualTo("ana@example.com");
    }
}
