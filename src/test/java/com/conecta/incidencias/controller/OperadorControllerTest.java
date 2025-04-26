package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.OperadorService;
import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;
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

@WebMvcTest(controllers = OperadorController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.conecta.incidencias.security.JwtAuthenticationFilter.class})
})
@AutoConfigureMockMvc(addFilters = false)
class OperadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperadorService operadorService;

    @Autowired
    private ObjectMapper objectMapper;

    private OperadorRequest operadorRequest;
    private OperadorResponse operadorResponse;

    @BeforeEach
    void setUp() {
        operadorRequest = new OperadorRequest();
        operadorRequest.setUsuarioId(1L);
        operadorRequest.setNombres("Juan");
        operadorRequest.setApellidos("Pérez");
        operadorRequest.setCargo("Supervisor");
        operadorRequest.setTelefono("987654321");

        operadorResponse = new OperadorResponse();
        operadorResponse.setId(1L);
        operadorResponse.setNombres("Juan");
        operadorResponse.setApellidos("Pérez");
        operadorResponse.setCargo("Supervisor");
        operadorResponse.setTelefono("987654321");
    }

    @Test
    void crearOperador_deberiaRetornarOperadorCreado() throws Exception {
        // Arrange
        when(operadorService.crearOperador(any(OperadorRequest.class))).thenReturn(operadorResponse);

        // Act & Assert
        mockMvc.perform(post("/operadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operadorRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombres").value("Juan"))
                .andExpect(jsonPath("$.apellidos").value("Pérez"))
                .andExpect(jsonPath("$.cargo").value("Supervisor"))
                .andExpect(jsonPath("$.telefono").value("987654321"));
    }

    @Test
    void obtenerOperador_deberiaRetornarOperadorCuandoExiste() throws Exception {
        // Arrange
        when(operadorService.obtenerOperadorPorId(1L)).thenReturn(Optional.of(operadorResponse));

        // Act & Assert
        mockMvc.perform(get("/operadores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombres").value("Juan"))
                .andExpect(jsonPath("$.apellidos").value("Pérez"))
                .andExpect(jsonPath("$.cargo").value("Supervisor"))
                .andExpect(jsonPath("$.telefono").value("987654321"));
    }

    @Test
    void actualizarOperador_deberiaRetornarOperadorActualizado() throws Exception {
        // Arrange
        when(operadorService.actualizarOperador(any(Long.class), any(OperadorRequest.class))).thenReturn(operadorResponse);

        // Act & Assert
        mockMvc.perform(put("/operadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operadorRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombres").value("Juan"))
                .andExpect(jsonPath("$.apellidos").value("Pérez"))
                .andExpect(jsonPath("$.cargo").value("Supervisor"))
                .andExpect(jsonPath("$.telefono").value("987654321"));
    }
}
