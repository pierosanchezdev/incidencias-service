package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.dto.response.ArchivoResponse;
import com.conecta.incidencias.entity.Archivo;
import com.conecta.incidencias.mapper.ArchivoMapper;
import com.conecta.incidencias.repository.ArchivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ArchivoServiceImplTest {

    @Mock
    private ArchivoRepository archivoRepository;

    @Mock
    private ArchivoMapper archivoMapper;

    @InjectMocks
    private ArchivoServiceImpl archivoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerArchivoPorId_deberiaRetornarArchivoCuandoExiste() {
        // Arrange
        Long archivoId = 1L;
        Archivo archivo = new Archivo();
        ArchivoResponse response = new ArchivoResponse();
        response.setId(archivoId);

        when(archivoRepository.findById(archivoId)).thenReturn(Optional.of(archivo));
        when(archivoMapper.toResponse(archivo)).thenReturn(response);

        // Act
        Optional<ArchivoResponse> resultado = archivoService.obtenerArchivoPorId(archivoId);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(archivoId);
    }

    @Test
    void listarArchivosPorIncidencia_deberiaRetornarListaDeArchivos() {
        // Arrange
        Long incidenciaId = 1L;
        Archivo archivo = new Archivo();
        ArchivoResponse response = new ArchivoResponse();
        response.setId(1L);

        when(archivoRepository.findByIncidenciaId(incidenciaId)).thenReturn(Collections.singletonList(archivo));
        when(archivoMapper.toResponse(archivo)).thenReturn(response);

        // Act
        List<ArchivoResponse> resultado = archivoService.listarArchivosPorIncidencia(incidenciaId);

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void eliminarArchivo_deberiaEliminarArchivo() {
        // Arrange
        Long archivoId = 1L;

        doNothing().when(archivoRepository).deleteById(archivoId);

        // Act
        archivoService.eliminarArchivo(archivoId);

        // Assert
        verify(archivoRepository, times(1)).deleteById(archivoId);
    }
}
