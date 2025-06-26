package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.ArchivoService;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import com.conecta.incidencias.enums.TipoArchivo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArchivoController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.conecta.incidencias.security.JwtAuthenticationFilter.class})
})
@AutoConfigureMockMvc(addFilters = false)
class ArchivoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArchivoService archivoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ArchivoResponse archivoResponse;

    @BeforeEach
    void setUp() {
        archivoResponse = new ArchivoResponse();
        archivoResponse.setId(1L);
        archivoResponse.setUrl("https://s3.aws.com/archivo1.jpg");
        archivoResponse.setTipo(TipoArchivo.IMAGEN);
        archivoResponse.setTamanoBytes(2048L);
    }

    @Test
    void obtenerArchivo_deberiaRetornarArchivoCuandoExiste() throws Exception {
        // Arrange
        when(archivoService.obtenerArchivoPorId(1L)).thenReturn(Optional.of(archivoResponse));

        // Act & Assert
        mockMvc.perform(get("/archivos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.url").value("https://s3.aws.com/archivo1.jpg"))
                .andExpect(jsonPath("$.tipo").value("IMAGEN"))
                .andExpect(jsonPath("$.tamanoBytes").value(2048L));
    }

    @Test
    void listarArchivosPorIncidencia_deberiaRetornarListaDeArchivos() throws Exception {
        // Arrange
        when(archivoService.listarArchivosPorIncidencia(1L)).thenReturn(Collections.singletonList(archivoResponse));

        // Act & Assert
        mockMvc.perform(get("/archivos/incidencia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].url").value("https://s3.aws.com/archivo1.jpg"))
                .andExpect(jsonPath("$[0].tipo").value("IMAGEN"))
                .andExpect(jsonPath("$[0].tamanoBytes").value(2048L));
    }

    @Test
    void eliminarArchivo_deberiaEliminarArchivo() throws Exception {
        // Arrange
        doNothing().when(archivoService).eliminarArchivo(1L);

        // Act & Assert
        mockMvc.perform(delete("/archivos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void verArchivo_deberiaRetornarArchivoCuandoExiste() throws Exception {
        // Arrange
        Path ruta = Paths.get("uploads/test.txt");
        Files.createDirectories(ruta.getParent());
        Files.writeString(ruta, "Contenido de prueba");

        // Act & Assert
        mockMvc.perform(get("/archivos/ver/uploads/test.txt"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain"));

        // Clean up
        Files.deleteIfExists(ruta);
    }

    @Test
    void verArchivo_deberiaRetornarNotFoundCuandoArchivoNoExiste() throws Exception {
        mockMvc.perform(get("/archivos/ver/uploads/archivo_inexistente.txt"))
                .andExpect(status().isNotFound());
    }

    @Test
    void verArchivo_deberiaRetornarErrorInternoCuandoFalla() throws Exception {
        mockMvc.perform(get("/archivos/ver/uploads/../invalid-path.txt"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void verArchivo_deberiaRetornarErrorInternoCuandoOcurreExcepcion() throws Exception {
        // Este nombre con caracteres especiales forzará una excepción (nombre inválido para Windows/Linux)
        String nombreArchivoInvalido = "invalid|file.txt";

        mockMvc.perform(get("/archivos/ver/uploads/" + nombreArchivoInvalido))
                .andExpect(status().isInternalServerError());
    }




}
