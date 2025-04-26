package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.dto.request.CategoriaRequest;
import com.conecta.incidencias.dto.response.CategoriaResponse;
import com.conecta.incidencias.entity.Categoria;
import com.conecta.incidencias.mapper.CategoriaMapper;
import com.conecta.incidencias.repository.CategoriaRepository;
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
import static org.mockito.Mockito.*;

class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCategoria_deberiaCrearYRetornarCategoria() {
        // Arrange
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Salud");
        request.setDescripcion("Problemas relacionados a salud");

        Categoria categoria = new Categoria();
        Categoria categoriaGuardada = new Categoria();
        categoriaGuardada.setId(1L);

        CategoriaResponse responseEsperado = new CategoriaResponse();
        responseEsperado.setId(1L);

        when(categoriaMapper.toEntity(request)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoriaGuardada);
        when(categoriaMapper.toResponse(categoriaGuardada)).thenReturn(responseEsperado);

        // Act
        CategoriaResponse resultado = categoriaService.crearCategoria(request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void obtenerCategoriaPorId_deberiaRetornarCategoriaCuandoExiste() {
        // Arrange
        Long categoriaId = 1L;
        Categoria categoria = new Categoria();
        CategoriaResponse response = new CategoriaResponse();
        response.setId(categoriaId);

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toResponse(categoria)).thenReturn(response);

        // Act
        Optional<CategoriaResponse> resultado = categoriaService.obtenerCategoriaPorId(categoriaId);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(categoriaId);
    }

    @Test
    void actualizarCategoria_deberiaActualizarCategoriaCuandoExiste() {
        // Arrange
        Long categoriaId = 1L;
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Educación");
        request.setDescripcion("Temas educativos");

        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(categoriaId);

        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setId(categoriaId);

        CategoriaResponse responseEsperado = new CategoriaResponse();
        responseEsperado.setId(categoriaId);

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.save(categoriaExistente)).thenReturn(categoriaActualizada);
        when(categoriaMapper.toResponse(categoriaActualizada)).thenReturn(responseEsperado);

        // Act
        CategoriaResponse resultado = categoriaService.actualizarCategoria(categoriaId, request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(categoriaId);
    }

    @Test
    void actualizarCategoria_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        Long categoriaId = 1L;
        CategoriaRequest request = new CategoriaRequest();

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> categoriaService.actualizarCategoria(categoriaId, request));
    }

    @Test
    void eliminarCategoria_deberiaEliminarCategoria() {
        // Arrange
        Long categoriaId = 1L;

        doNothing().when(categoriaRepository).deleteById(categoriaId);

        // Act
        categoriaService.eliminarCategoria(categoriaId);

        // Assert
        verify(categoriaRepository, times(1)).deleteById(categoriaId);
    }

    @Test
    void listarCategorias_deberiaRetornarListaDeCategorias() {
        // Arrange
        Categoria categoria = new Categoria();
        CategoriaResponse response = new CategoriaResponse();
        response.setId(1L);

        when(categoriaRepository.findAll()).thenReturn(Collections.singletonList(categoria));
        when(categoriaMapper.toResponse(categoria)).thenReturn(response);

        // Act
        List<CategoriaResponse> resultado = categoriaService.listarCategorias();

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }
}
