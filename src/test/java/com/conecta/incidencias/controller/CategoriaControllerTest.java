package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.CategoriaService;
import com.conecta.incidencias.dto.request.CategoriaRequest;
import com.conecta.incidencias.dto.response.CategoriaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoriaController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.conecta.incidencias.security.JwtAuthenticationFilter.class})
})
@AutoConfigureMockMvc(addFilters = false)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoriaRequest categoriaRequest;
    private CategoriaResponse categoriaResponse;

    @BeforeEach
    void setUp() {
        categoriaRequest = new CategoriaRequest();
        categoriaRequest.setNombre("Salud");
        categoriaRequest.setDescripcion("Incidencias relacionadas a salud pública.");

        categoriaResponse = new CategoriaResponse();
        categoriaResponse.setId(1L);
        categoriaResponse.setNombre("Salud");
        categoriaResponse.setDescripcion("Incidencias relacionadas a salud pública.");
    }

    @Test
    void crearCategoria_deberiaRetornarCategoriaCreada() throws Exception {
        // Arrange
        when(categoriaService.crearCategoria(any(CategoriaRequest.class))).thenReturn(categoriaResponse);

        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Salud"))
                .andExpect(jsonPath("$.descripcion").value("Incidencias relacionadas a salud pública."));
    }

    @Test
    void obtenerCategoria_deberiaRetornarCategoriaCuandoExiste() throws Exception {
        // Arrange
        when(categoriaService.obtenerCategoriaPorId(1L)).thenReturn(Optional.of(categoriaResponse));

        // Act & Assert
        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Salud"))
                .andExpect(jsonPath("$.descripcion").value("Incidencias relacionadas a salud pública."));
    }

    @Test
    void actualizarCategoria_deberiaRetornarCategoriaActualizada() throws Exception {
        // Arrange
        when(categoriaService.actualizarCategoria(any(Long.class), any(CategoriaRequest.class))).thenReturn(categoriaResponse);

        // Act & Assert
        mockMvc.perform(put("/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Salud"))
                .andExpect(jsonPath("$.descripcion").value("Incidencias relacionadas a salud pública."));
    }

    @Test
    void eliminarCategoria_deberiaEliminarCategoria() throws Exception {
        // Arrange
        doNothing().when(categoriaService).eliminarCategoria(1L);

        // Act & Assert
        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isOk());
    }

    @Test
    void listarCategorias_deberiaRetornarListaDeCategorias() throws Exception {
        // Arrange
        when(categoriaService.listarCategorias()).thenReturn(Collections.singletonList(categoriaResponse));

        // Act & Assert
        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Salud"))
                .andExpect(jsonPath("$[0].descripcion").value("Incidencias relacionadas a salud pública."));
    }
}
