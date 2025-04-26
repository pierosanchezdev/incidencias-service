package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.UsuarioService;
import com.conecta.incidencias.dto.request.UsuarioRequest;
import com.conecta.incidencias.dto.response.UsuarioResponse;
import com.conecta.incidencias.enums.RolUsuario;
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

@WebMvcTest(controllers = UsuarioController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.conecta.incidencias.security.JwtAuthenticationFilter.class})
})
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioRequest usuarioRequest;
    private UsuarioResponse usuarioResponse;

    @BeforeEach
    void setUp() {
        usuarioRequest = new UsuarioRequest();
        usuarioRequest.setUsername("nuevoUsuario");
        usuarioRequest.setEmail("nuevo@correo.com");
        usuarioRequest.setPassword("password123");
        usuarioRequest.setRol(RolUsuario.OPERADOR);

        usuarioResponse = new UsuarioResponse();
        usuarioResponse.setUsername("nuevoUsuario");
        usuarioResponse.setId(1L);
        usuarioResponse.setEmail("nuevo@correo.com");
        usuarioResponse.setRol(RolUsuario.COMUNERO);
    }

    @Test
    void crearUsuario_deberiaRetornarUsuarioCreado() throws Exception {
        // Arrange
        when(usuarioService.crearUsuario(any(UsuarioRequest.class))).thenReturn(usuarioResponse);

        // Act & Assert
        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("nuevo@correo.com"))
                .andExpect(jsonPath("$.rol").value("COMUNERO"));
    }

    @Test
    void obtenerUsuario_deberiaRetornarUsuarioCuandoExiste() throws Exception {
        // Arrange
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuarioResponse));

        // Act & Assert
        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("nuevo@correo.com"))
                .andExpect(jsonPath("$.rol").value("COMUNERO"));
    }

    @Test
    void actualizarUsuario_deberiaRetornarUsuarioActualizado() throws Exception {
        // Arrange
        when(usuarioService.actualizarUsuario(any(Long.class), any(UsuarioRequest.class))).thenReturn(usuarioResponse);

        // Act & Assert
        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("nuevo@correo.com"))
                .andExpect(jsonPath("$.rol").value("COMUNERO"));
    }
}
