package com.conecta.incidencias.integration;

import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.entity.*;
import com.conecta.incidencias.enums.Estado;
import com.conecta.incidencias.enums.Impacto;
import com.conecta.incidencias.enums.RolUsuario;
import com.conecta.incidencias.enums.Urgencia;
import com.conecta.incidencias.repository.*;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class IncidenciaIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private UbicacionGeograficaRepository ubicacionRepository;
    @Autowired private IncidenciaRepository incidenciaRepository;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private Long usuarioId, categoriaId, ubicacionId;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("password");
        usuario.setEmail("test@example.com");
        usuario.setRol(RolUsuario.COMUNERO);
        usuarioId = usuarioRepository.save(usuario).getId();

        Categoria categoria = new Categoria();
        categoria.setNombre("Agua");
        categoriaId = categoriaRepository.save(categoria).getId();

        UbicacionGeografica ubicacion = new UbicacionGeografica();
        ubicacion.setLatitud(-12.0);
        ubicacion.setLongitud(-77.0);
        ubicacion.setDistrito("Lima");
        ubicacion.setProvincia("Lima");
        ubicacion.setDepartamento("Lima");
        ubicacion.setPais("Perú");
        ubicacion.setUbigeo("150101");
        ubicacion.setNombreLocalidad("Centro");
        ubicacionId = ubicacionRepository.save(ubicacion).getId();

        when(jwtUtils.extraerUsername(anyString())).thenReturn("testuser");
        when(jwtUtils.validarToken(any(), any())).thenReturn(true);

        UserDetails mockUser = User.withUsername("testuser")
                .password("password")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_COMUNERO")))
                .build();
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(mockUser);
    }

    @Test
    void crearIncidencia_deberiaRetornar201() throws Exception {
        IncidenciaRequest request = new IncidenciaRequest();
        request.setTitulo("Fuga de agua");
        request.setDescripcion("Se ha detectado fuga en calle");
        request.setUrgencia(Urgencia.MEDIA);
        request.setImpacto(Impacto.MEDIO);
        request.setCategoriaId(categoriaId);
        request.setUsuarioId(usuarioId);
        request.setUbicacionId(ubicacionId);

        String json = objectMapper.writeValueAsString(request);

        MockMultipartFile datos = new MockMultipartFile(
                "datos", "datos", "application/json", json.getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile archivo = new MockMultipartFile(
                "archivos", "foto.jpg", "image/jpeg", "contenido".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/incidencias")
                        .file(datos)
                        .file(archivo)
                        .header("Authorization", "Bearer fake-jwt-token")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    void obtenerIncidenciaPorId_deberiaRetornar200() throws Exception {
        // Crear incidencia previamente
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo("Alarma");
        incidencia.setDescripcion("Sonido constante");
        incidencia.setUsuario(usuarioRepository.findById(usuarioId).get());
        incidencia.setCategoria(categoriaRepository.findById(categoriaId).get());
        incidencia.setUbicacion(ubicacionRepository.findById(ubicacionId).get());
        incidencia.setUrgencia(Urgencia.MEDIA);
        incidencia.setImpacto(Impacto.MEDIO);
        incidencia.setEstado(com.conecta.incidencias.enums.Estado.PENDIENTE);

        Long id = incidenciaRepository.save(incidencia).getId();

        mockMvc.perform(get("/incidencias/" + id)
                        .header("Authorization", "Bearer fake-jwt-token"))
                .andExpect(status().isOk());
    }

    @Test
    void listarIncidenciasPorUsuario_deberiaRetornarLista() throws Exception {
        // Crear incidencia
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo("Cable suelto");
        incidencia.setDescripcion("Riesgo eléctrico");
        incidencia.setUsuario(usuarioRepository.findById(usuarioId).get());
        incidencia.setCategoria(categoriaRepository.findById(categoriaId).get());
        incidencia.setUbicacion(ubicacionRepository.findById(ubicacionId).get());
        incidencia.setUrgencia(Urgencia.ALTA);
        incidencia.setImpacto(Impacto.ALTO);
        incidencia.setEstado(com.conecta.incidencias.enums.Estado.PENDIENTE);

        incidenciaRepository.save(incidencia);

        mockMvc.perform(get("/incidencias/mis-incidencias")
                        .param("usuarioId", String.valueOf(usuarioId))
                        .header("Authorization", "Bearer fake-jwt-token"))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarIncidencia_deberiaRetornar200() throws Exception {
        // Crear incidencia previa
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo("Falla eléctrica");
        incidencia.setDescripcion("Tensión inestable");
        incidencia.setUsuario(usuarioRepository.findById(usuarioId).get());
        incidencia.setCategoria(categoriaRepository.findById(categoriaId).get());
        incidencia.setUbicacion(ubicacionRepository.findById(ubicacionId).get());
        incidencia.setUrgencia(Urgencia.MEDIA);
        incidencia.setImpacto(Impacto.MEDIO);
        incidencia.setEstado(com.conecta.incidencias.enums.Estado.PENDIENTE);

        Long id = incidenciaRepository.save(incidencia).getId();

        IncidenciaRequest updated = new IncidenciaRequest();
        updated.setTitulo("Falla eléctrica grave");
        updated.setDescripcion("Tensión intermitente");
        updated.setUsuarioId(usuarioId);
        updated.setCategoriaId(categoriaId);
        updated.setUbicacionId(ubicacionId);
        updated.setImpacto(Impacto.ALTO);
        updated.setUrgencia(Urgencia.ALTA);
        updated.setEstado(Estado.RESUELTA);

        String json = objectMapper.writeValueAsString(updated);

        mockMvc.perform(put("/incidencias/" + id)
                        .header("Authorization", "Bearer fake-jwt-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }


}
