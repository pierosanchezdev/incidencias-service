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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        // Arrange: construir el JSON del request
        String datosJson = """
        {
            "titulo": "Fuga de agua",
            "descripcion": "Se detectó fuga en la vereda",
            "categoriaId": 1,
            "impacto": "ALTO",
            "urgencia": "ALTA",
            "usuarioId": 1,
            "ubicacionId": 1
        }
        """;

        MockMultipartFile datosPart = new MockMultipartFile(
                "datos", // nombre del @RequestPart
                "datos", // nombre del archivo simulado
                "application/json",
                datosJson.getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile archivoPart = new MockMultipartFile(
                "archivos",
                "evidencia.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        // Act & Assert
        mockMvc.perform(multipart("/incidencias")
                        .file(datosPart)
                        .file(archivoPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        verify(incidenciaService, times(1)).crearIncidencia(any(IncidenciaRequest.class), anyList());
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
