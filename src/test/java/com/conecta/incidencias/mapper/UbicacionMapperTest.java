package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.UbicacionRequest;
import com.conecta.incidencias.entity.UbicacionGeografica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class UbicacionMapperTest {

    private UbicacionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UbicacionMapperImpl();
    }

    @Test
    void toEntity_deberiaMapearTodosLosCampos() {
        // Arrange
        UbicacionRequest request = UbicacionRequest.builder()
                .pais("Perú")
                .departamento("Lima")
                .provincia("Lima")
                .distrito("Miraflores")
                .ubigeo("150122")
                .codigoPostal("15074")
                .nombreLocalidad("Malecon")
                .latitud(new BigDecimal("-12.1234"))
                .longitud(new BigDecimal("-77.0123"))
                .build();

        // Act
        UbicacionGeografica entity = mapper.toEntity(request);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getPais()).isEqualTo("Perú");
        assertThat(entity.getDepartamento()).isEqualTo("Lima");
        assertThat(entity.getProvincia()).isEqualTo("Lima");
        assertThat(entity.getDistrito()).isEqualTo("Miraflores");
        assertThat(entity.getUbigeo()).isEqualTo("150122");
        assertThat(entity.getCodigoPostal()).isEqualTo("15074");
        assertThat(entity.getNombreLocalidad()).isEqualTo("Malecon");
        assertThat(entity.getLatitud()).isEqualTo(-12.1234);
        assertThat(entity.getLongitud()).isEqualTo(-77.0123);
    }

    @Test
    void toEntity_deberiaRetornarNullSiRequestEsNull() {
        // Act
        UbicacionGeografica entity = mapper.toEntity(null);

        // Assert
        assertThat(entity).isNull();
    }
}
