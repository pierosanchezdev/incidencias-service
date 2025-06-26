package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.dto.request.UbicacionRequest;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.mapper.UbicacionMapper;
import com.conecta.incidencias.repository.UbicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UbicacionServiceImplTest {

    @Mock
    private UbicacionRepository ubicacionRepository;

    @Mock
    private UbicacionMapper ubicacionMapper;

    @InjectMocks
    private UbicacionServiceImpl ubicacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crear_deberiaGuardarUbicacionYRetornarSuId() {
        // Arrange
        UbicacionRequest request = UbicacionRequest.builder()
                .pais("Perú")
                .departamento("Lima")
                .provincia("Lima")
                .distrito("Miraflores")
                .ubigeo("150122")
                .codigoPostal("15074")
                .nombreLocalidad("Av. Pardo")
                .latitud(new BigDecimal("-12.1234"))
                .longitud(new BigDecimal("-77.0123"))
                .build();

        UbicacionGeografica entity = UbicacionGeografica.builder()
                .id(5L)
                .pais("Perú")
                .departamento("Lima")
                .provincia("Lima")
                .distrito("Miraflores")
                .ubigeo("150122")
                .codigoPostal("15074")
                .nombreLocalidad("Av. Pardo")
                .latitud(-12.1234)    // en la entidad sigue siendo Double
                .longitud(-77.0123)
                .build();

        when(ubicacionMapper.toEntity(request)).thenReturn(entity);
        when(ubicacionRepository.save(entity)).thenReturn(entity);

        // Act
        Integer result = ubicacionService.crear(request);

        // Assert
        assertThat(result).isEqualTo(5);
        verify(ubicacionMapper).toEntity(request);
        verify(ubicacionRepository).save(entity);
    }
}
