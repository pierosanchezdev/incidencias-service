package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.HistorialEstadoService;
import com.conecta.incidencias.dto.request.CambioEstadoRequest;
import com.conecta.incidencias.dto.response.HistorialEstadoResponse;
import com.conecta.incidencias.enums.Estado;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HistorialEstadoController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.conecta.incidencias.security.JwtAuthenticationFilter.class})
})
@AutoConfigureMockMvc(addFilters = false)
class HistorialEstadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistorialEstadoService historialEstadoService;

    @Autowired
    private ObjectMapper objectMapper;

    private CambioEstadoRequest cambioEstadoRequest;
    private HistorialEstadoResponse historialEstadoResponse;

    @BeforeEach
    void setUp() {
        cambioEstadoRequest = new CambioEstadoRequest();
        cambioEstadoRequest.setEstadoNuevo(Estado.RESUELTA);
        cambioEstadoRequest.setComentario("Problema solucionado satisfactoriamente.");

        historialEstadoResponse = new HistorialEstadoResponse();
        historialEstadoResponse.setId(1L);
        historialEstadoResponse.setEstadoAnterior(Estado.EN_PROCESO);
        historialEstadoResponse.setEstadoNuevo(Estado.RESUELTA);
        historialEstadoResponse.setComentario("Problema solucionado satisfactoriamente.");
    }

    @Test
    void registrarCambioEstado_deberiaRetornarHistorialCreado() throws Exception {
        // Arrange
        when(historialEstadoService.registrarCambioEstado(any(Long.class), any(Long.class), any(CambioEstadoRequest.class)))
                .thenReturn(historialEstadoResponse);

        // Act & Assert
        mockMvc.perform(post("/incidencias/1/historial")
                        .param("operadorId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cambioEstadoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.estadoAnterior").value("EN_PROCESO"))
                .andExpect(jsonPath("$.estadoNuevo").value("RESUELTA"))
                .andExpect(jsonPath("$.comentario").value("Problema solucionado satisfactoriamente."));
    }

    @Test
    void listarHistorial_deberiaRetornarListaDeHistorial() throws Exception {
        // Arrange
        when(historialEstadoService.listarHistorialPorIncidencia(1L)).thenReturn(Collections.singletonList(historialEstadoResponse));

        // Act & Assert
        mockMvc.perform(get("/incidencias/1/historial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].estadoAnterior").value("EN_PROCESO"))
                .andExpect(jsonPath("$[0].estadoNuevo").value("RESUELTA"))
                .andExpect(jsonPath("$[0].comentario").value("Problema solucionado satisfactoriamente."));
    }
}
