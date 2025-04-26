package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.ComuneroService;
import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ComuneroController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.conecta.incidencias.security.JwtAuthenticationFilter.class})
})
@AutoConfigureMockMvc(addFilters = false)
class ComuneroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComuneroService comuneroService;

    @Autowired
    private ObjectMapper objectMapper;

    private ComuneroRequest comuneroRequest;
    private ComuneroResponse comuneroResponse;

    @BeforeEach
    void setUp() {
        comuneroRequest = new ComuneroRequest();
        comuneroRequest.setUsuarioId(1L);
        comuneroRequest.setUbicacionId(1L);
        comuneroRequest.setNombres("Ana");
        comuneroRequest.setApellidos("Gonzales");
        comuneroRequest.setTelefono("999888777");
        comuneroRequest.setCorreo("ana@example.com");
        comuneroRequest.setTipoDocumento("DNI");
        comuneroRequest.setNumeroDocumento("12345678");

        comuneroResponse = new ComuneroResponse();
        comuneroResponse.setId(1L);
        comuneroResponse.setNombres("Ana");
        comuneroResponse.setApellidos("Gonzales");
        comuneroResponse.setTelefono("999888777");
        comuneroResponse.setCorreo("ana@example.com");
    }

    @Test
    void crearComunero_deberiaRetornarComuneroCreado() throws Exception {
        // Arrange
        when(comuneroService.crearComunero(any(ComuneroRequest.class))).thenReturn(comuneroResponse);

        // Act & Assert
        mockMvc.perform(post("/comuneros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comuneroRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombres").value("Ana"))
                .andExpect(jsonPath("$.apellidos").value("Gonzales"))
                .andExpect(jsonPath("$.telefono").value("999888777"))
                .andExpect(jsonPath("$.correo").value("ana@example.com"));
    }

    @Test
    void obtenerComunero_deberiaRetornarComuneroCuandoExiste() throws Exception {
        // Arrange
        when(comuneroService.obtenerComuneroPorId(1L)).thenReturn(Optional.of(comuneroResponse));

        // Act & Assert
        mockMvc.perform(get("/comuneros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombres").value("Ana"))
                .andExpect(jsonPath("$.apellidos").value("Gonzales"))
                .andExpect(jsonPath("$.telefono").value("999888777"))
                .andExpect(jsonPath("$.correo").value("ana@example.com"));
    }

    @Test
    void actualizarComunero_deberiaRetornarComuneroActualizado() throws Exception {
        // Arrange
        when(comuneroService.actualizarComunero(any(Long.class), any(ComuneroRequest.class))).thenReturn(comuneroResponse);

        // Act & Assert
        mockMvc.perform(put("/comuneros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comuneroRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombres").value("Ana"))
                .andExpect(jsonPath("$.apellidos").value("Gonzales"))
                .andExpect(jsonPath("$.telefono").value("999888777"))
                .andExpect(jsonPath("$.correo").value("ana@example.com"));
    }
}
