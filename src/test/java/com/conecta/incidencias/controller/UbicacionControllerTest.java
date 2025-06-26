package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.UbicacionService;
import com.conecta.incidencias.dto.request.UbicacionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UbicacionController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.conecta.incidencias.security.JwtAuthenticationFilter.class})
})
@AutoConfigureMockMvc(addFilters = false)
class UbicacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UbicacionService ubicacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearUbicacion_deberiaRetornarStatusCreatedYId() throws Exception {
        // Arrange
        UbicacionRequest request = UbicacionRequest.builder()
                .pais("Perú")
                .departamento("Lima")
                .provincia("Lima")
                .distrito("Miraflores")
                .ubigeo("150122")
                .codigoPostal("15074")
                .nombreLocalidad("Zona A")
                .latitud(new BigDecimal("-12.121212"))
                .longitud(new BigDecimal("-77.030303"))
                .build();

        when(ubicacionService.crear(any(UbicacionRequest.class))).thenReturn(123);

        // Act & Assert
        mockMvc.perform(post("/ubicacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("123"));
    }
}
