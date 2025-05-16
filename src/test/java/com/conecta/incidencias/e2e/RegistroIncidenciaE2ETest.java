package com.conecta.incidencias.e2e;

import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.entity.Categoria;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.enums.Impacto;
import com.conecta.incidencias.enums.RolUsuario;
import com.conecta.incidencias.enums.Urgencia;
import com.conecta.incidencias.repository.CategoriaRepository;
import com.conecta.incidencias.repository.UbicacionGeograficaRepository;
import com.conecta.incidencias.repository.UsuarioRepository;
import com.conecta.incidencias.security.JwtUtils;
import com.conecta.incidencias.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class RegistroIncidenciaE2ETest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UbicacionGeograficaRepository ubicacionRepository;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private Long usuarioId;
    private Long categoriaId;
    private Long ubicacionId;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setEmail("testuser@example.com");
        usuario.setPassword("password");
        usuario.setRol(RolUsuario.COMUNERO);
        usuario.setUsername("testuser");
        usuarioId = usuarioRepository.save(usuario).getId();

        Categoria categoria = new Categoria();
        categoria.setNombre("Infraestructura");
        categoriaId = categoriaRepository.save(categoria).getId();

        UbicacionGeografica ubicacion = new UbicacionGeografica();
        ubicacion.setLatitud(-12.0464);
        ubicacion.setLongitud(-77.0428);
        ubicacion.setDistrito("Miraflores");
        ubicacion.setProvincia("Lima");
        ubicacion.setDepartamento("Lima");
        ubicacion.setPais("Perú");
        ubicacion.setUbigeo("150122");
        ubicacion.setNombreLocalidad("Miraflores");
        ubicacionId = ubicacionRepository.save(ubicacion).getId();
    }

    @Test
    void registrarIncidencia_deberiaCrearIncidencia() throws Exception {
        // Arrange
        String token = "fake-jwt-token";

        when(jwtUtils.extraerUsername(token)).thenReturn("testuser");
        when(jwtUtils.validarToken(eq(token), any())).thenReturn(true);

        UserDetails userDetails = User.withUsername("testuser")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        IncidenciaRequest incidenciaRequest = new IncidenciaRequest();
        incidenciaRequest.setTitulo("Fuga de agua");
        incidenciaRequest.setDescripcion("Se detecta fuga en la tubería principal");
        incidenciaRequest.setUrgencia(Urgencia.ALTA);
        incidenciaRequest.setImpacto(Impacto.ALTO);
        incidenciaRequest.setCategoriaId(1L);
        incidenciaRequest.setUsuarioId(1L);
        incidenciaRequest.setUbicacionId(1L);

        String datosJson = objectMapper.writeValueAsString(incidenciaRequest);

        MockMultipartFile datosPart = new MockMultipartFile(
                "datos",
                "datos",
                "application/json",
                datosJson.getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile archivoPart = new MockMultipartFile(
                "archivos",
                "imagen.jpg",
                "image/jpeg",
                "fake-image-content".getBytes(StandardCharsets.UTF_8)
        );

        // Act & Assert
        mockMvc.perform(multipart("/incidencias")
                        .file(datosPart)
                        .file(archivoPart)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
    }

}
