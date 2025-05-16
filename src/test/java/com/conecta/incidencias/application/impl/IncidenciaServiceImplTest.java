package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.StorageService;
import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.IncidenciaResponse;
import com.conecta.incidencias.entity.*;
import com.conecta.incidencias.enums.Impacto;
import com.conecta.incidencias.enums.Urgencia;
import com.conecta.incidencias.mapper.ArchivoMapper;
import com.conecta.incidencias.mapper.IncidenciaMapper;
import com.conecta.incidencias.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IncidenciaServiceImplTest {

    @Mock
    private IncidenciaRepository incidenciaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private UbicacionGeograficaRepository ubicacionRepository;

    @Mock
    private ArchivoRepository archivoRepository;

    @Mock
    private IncidenciaMapper incidenciaMapper;

    @Mock
    private ArchivoMapper archivoMapper;
    @Mock
    private StorageService storageService;

    @InjectMocks
    private IncidenciaServiceImpl incidenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearIncidencia_deberiaCrearIncidenciaYRetornarResponse() {
        // Arrange
        IncidenciaRequest request = new IncidenciaRequest();
        request.setUsuarioId(1L);
        request.setCategoriaId(2L);
        request.setUbicacionId(3L);

        Usuario usuario = new Usuario();
        Categoria categoria = new Categoria();
        UbicacionGeografica ubicacion = new UbicacionGeografica();
        Incidencia incidencia = new Incidencia();
        Incidencia incidenciaGuardada = new Incidencia();
        incidenciaGuardada.setId(1L);

        IncidenciaResponse responseEsperado = new IncidenciaResponse();
        responseEsperado.setId(1L);

        MultipartFile archivoMock = mock(MultipartFile.class);
        when(archivoMock.getOriginalFilename()).thenReturn("foto.jpg");
        when(archivoMock.getContentType()).thenReturn("image/jpeg");
        when(archivoMock.getSize()).thenReturn(123456L);

        when(storageService.subirArchivo(archivoMock)).thenReturn("http://fake.url/foto.jpg");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(categoria));
        when(ubicacionRepository.findById(3L)).thenReturn(Optional.of(ubicacion));
        when(incidenciaMapper.toEntity(request)).thenReturn(incidencia);
        when(incidenciaRepository.save(any(Incidencia.class))).thenReturn(incidenciaGuardada);
        when(incidenciaMapper.toResponse(incidenciaGuardada)).thenReturn(responseEsperado);

        // Act
        IncidenciaResponse resultado = incidenciaService.crearIncidencia(request, List.of(archivoMock));

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void obtenerIncidenciaPorId_deberiaRetornarIncidenciaCuandoExiste() {
        // Arrange
        Long incidenciaId = 1L;
        Incidencia incidencia = new Incidencia();
        IncidenciaResponse response = new IncidenciaResponse();
        response.setId(incidenciaId);

        when(incidenciaRepository.findById(incidenciaId)).thenReturn(Optional.of(incidencia));
        when(incidenciaMapper.toResponse(incidencia)).thenReturn(response);

        // Act
        Optional<IncidenciaResponse> resultado = incidenciaService.obtenerIncidenciaPorId(incidenciaId);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(incidenciaId);
    }

    @Test
    void listarIncidencias_deberiaRetornarListaDeIncidencias() {
        // Arrange
        Incidencia incidencia = new Incidencia();
        IncidenciaResponse response = new IncidenciaResponse();
        response.setId(1L);

        when(incidenciaRepository.findAll()).thenReturn(Collections.singletonList(incidencia));
        when(incidenciaMapper.toResponse(incidencia)).thenReturn(response);

        // Act
        List<IncidenciaResponse> resultado = incidenciaService.listarIncidencias();

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void listarIncidenciasPorUsuario_deberiaRetornarListaDeIncidencias() {
        // Arrange
        Long usuarioId = 1L;
        Incidencia incidencia = new Incidencia();
        IncidenciaResponse response = new IncidenciaResponse();
        response.setId(1L);

        when(incidenciaRepository.findByUsuarioId(usuarioId)).thenReturn(Collections.singletonList(incidencia));
        when(incidenciaMapper.toResponse(incidencia)).thenReturn(response);

        // Act
        List<IncidenciaResponse> resultado = incidenciaService.listarIncidenciasPorUsuario(usuarioId);

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void actualizarIncidencia_deberiaActualizarIncidenciaCuandoExiste() {
        // Arrange
        Long incidenciaId = 1L;
        IncidenciaRequest request = new IncidenciaRequest();
        request.setTitulo("Nuevo Titulo");
        request.setDescripcion("Nueva Descripción");
        request.setImpacto(Impacto.ALTO);
        request.setUrgencia(Urgencia.ALTA);
        request.setCategoriaId(2L);
        request.setUbicacionId(3L);

        Incidencia incidenciaExistente = new Incidencia();

        Categoria nuevaCategoria = new Categoria();
        UbicacionGeografica nuevaUbicacion = new UbicacionGeografica();

        Incidencia incidenciaActualizada = new Incidencia();
        incidenciaActualizada.setId(incidenciaId);

        IncidenciaResponse responseEsperado = new IncidenciaResponse();
        responseEsperado.setId(incidenciaId);

        when(incidenciaRepository.findById(incidenciaId)).thenReturn(Optional.of(incidenciaExistente));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(nuevaCategoria));
        when(ubicacionRepository.findById(3L)).thenReturn(Optional.of(nuevaUbicacion));
        when(incidenciaRepository.save(incidenciaExistente)).thenReturn(incidenciaActualizada);
        when(incidenciaMapper.toResponse(incidenciaActualizada)).thenReturn(responseEsperado);

        // Act
        IncidenciaResponse resultado = incidenciaService.actualizarIncidencia(incidenciaId, request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(incidenciaId);
    }

    @Test
    void actualizarIncidencia_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        Long incidenciaId = 1L;
        IncidenciaRequest request = new IncidenciaRequest();

        when(incidenciaRepository.findById(incidenciaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> incidenciaService.actualizarIncidencia(incidenciaId, request));
    }
}
