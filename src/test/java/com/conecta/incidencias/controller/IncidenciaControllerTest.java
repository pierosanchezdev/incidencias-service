package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.IncidenciaService;
import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.IncidenciaResponse;
import com.conecta.incidencias.enums.Impacto;
import com.conecta.incidencias.enums.Urgencia;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = IncidenciaController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.conecta.incidencias.security.JwtAuthenticationFilter.class})
})
@AutoConfigureMockMvc(addFilters = false)
class IncidenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidenciaService incidenciaService;

    @Autowired
    private ObjectMapper objectMapper;

    private IncidenciaRequest incidenciaRequest;
    private IncidenciaResponse incidenciaResponse;

    @BeforeEach
    void setUp() {
        incidenciaRequest = new IncidenciaRequest();
        incidenciaRequest.setTitulo("Fuga de agua");
        incidenciaRequest.setDescripcion("Se detectó una fuga en la tubería principal.");
        incidenciaRequest.setUsuarioId(1L);
        incidenciaRequest.setCategoriaId(2L);
        incidenciaRequest.setUbicacionId(3L);
        incidenciaRequest.setImpacto(Impacto.ALTO);
        incidenciaRequest.setUrgencia(Urgencia.ALTA);

        incidenciaResponse = new IncidenciaResponse();
        incidenciaResponse.setId(1L);
        incidenciaResponse.setTitulo("Fuga de agua");
        incidenciaResponse.setDescripcion("Se detectó una fuga en la tubería principal.");
    }

    @Test
    void crearIncidencia_deberiaRetornarIncidenciaCreada() throws Exception {
        // Arrange
        when(incidenciaService.crearIncidencia(any(IncidenciaRequest.class))).thenReturn(incidenciaResponse);

        // Act & Assert
        mockMvc.perform(post("/incidencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incidenciaRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Fuga de agua"))
                .andExpect(jsonPath("$.descripcion").value("Se detectó una fuga en la tubería principal."));
    }

    @Test
    void obtenerIncidencia_deberiaRetornarIncidenciaCuandoExiste() throws Exception {
        // Arrange
        when(incidenciaService.obtenerIncidenciaPorId(1L)).thenReturn(Optional.of(incidenciaResponse));

        // Act & Assert
        mockMvc.perform(get("/incidencias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Fuga de agua"))
                .andExpect(jsonPath("$.descripcion").value("Se detectó una fuga en la tubería principal."));
    }

    @Test
    void listarIncidencias_deberiaRetornarListaDeIncidencias() throws Exception {
        // Arrange
        when(incidenciaService.listarIncidencias()).thenReturn(Collections.singletonList(incidenciaResponse));

        // Act & Assert
        mockMvc.perform(get("/incidencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Fuga de agua"))
                .andExpect(jsonPath("$[0].descripcion").value("Se detectó una fuga en la tubería principal."));
    }

    @Test
    void listarMisIncidencias_deberiaRetornarListaDeIncidenciasDelUsuario() throws Exception {
        // Arrange
        when(incidenciaService.listarIncidenciasPorUsuario(1L)).thenReturn(Collections.singletonList(incidenciaResponse));

        // Act & Assert
        mockMvc.perform(get("/incidencias/mis-incidencias")
                        .param("usuarioId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Fuga de agua"))
                .andExpect(jsonPath("$[0].descripcion").value("Se detectó una fuga en la tubería principal."));
    }

    @Test
    void actualizarIncidencia_deberiaRetornarIncidenciaActualizada() throws Exception {
        // Arrange
        when(incidenciaService.actualizarIncidencia(any(Long.class), any(IncidenciaRequest.class))).thenReturn(incidenciaResponse);

        // Act & Assert
        mockMvc.perform(put("/incidencias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incidenciaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Fuga de agua"))
                .andExpect(jsonPath("$.descripcion").value("Se detectó una fuga en la tubería principal."));
    }
}
