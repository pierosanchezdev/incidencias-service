package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import com.conecta.incidencias.dto.response.IncidenciaResponse;
import com.conecta.incidencias.entity.Archivo;
import com.conecta.incidencias.entity.Categoria;
import com.conecta.incidencias.entity.Incidencia;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.enums.Estado;
import com.conecta.incidencias.enums.Impacto;
import com.conecta.incidencias.enums.Urgencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class IncidenciaMapperTest {

    @InjectMocks
    private IncidenciaMapperImpl incidenciaMapper;

    @Mock
    private ArchivoMapper archivoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toEntity_deberiaMapearRequestAEntidad() {
        // Arrange
        IncidenciaRequest request = new IncidenciaRequest();
        request.setTitulo("Fuga de agua");
        request.setDescripcion("Fuga detectada en el edificio principal");
        request.setImpacto(Impacto.MEDIO);
        request.setUrgencia(Urgencia.BAJA);

        // Act
        Incidencia incidencia = incidenciaMapper.toEntity(request);

        // Assert
        assertThat(incidencia).isNotNull();
        assertThat(incidencia.getTitulo()).isEqualTo("Fuga de agua");
        assertThat(incidencia.getDescripcion()).isEqualTo("Fuga detectada en el edificio principal");
        assertThat(incidencia.getImpacto()).isEqualTo(Impacto.MEDIO);
        assertThat(incidencia.getUrgencia()).isEqualTo(Urgencia.BAJA);
    }

    @Test
    void toResponse_deberiaMapearEntidadAResponse() {
        // Arrange
        Categoria categoria = new Categoria();
        categoria.setId(5L);

        Usuario usuario = new Usuario();
        usuario.setId(10L);

        UbicacionGeografica ubicacion = new UbicacionGeografica();
        ubicacion.setId(15L);

        Archivo archivo = new Archivo();
        archivo.setId(20L);

        ArchivoResponse archivoResponse = new ArchivoResponse();
        archivoResponse.setId(20L);

        when(archivoMapper.toResponse(archivo)).thenReturn(archivoResponse);

        Incidencia incidencia = new Incidencia();
        incidencia.setId(1L);
        incidencia.setTitulo("Incendio");
        incidencia.setDescripcion("Incendio en el piso 2");
        incidencia.setImpacto(Impacto.ALTO);
        incidencia.setUrgencia(Urgencia.ALTA);
        incidencia.setEstado(Estado.EN_PROCESO);
        incidencia.setCategoria(categoria);
        incidencia.setUsuario(usuario);
        incidencia.setUbicacion(ubicacion);
        incidencia.setFechaRegistro(LocalDateTime.of(2024, 4, 25, 10, 0));
        incidencia.setFechaActualizacion(LocalDateTime.of(2024, 4, 26, 10, 0));
        incidencia.setArchivos(Collections.singletonList(archivo));

        // Act
        IncidenciaResponse response = incidenciaMapper.toResponse(incidencia);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitulo()).isEqualTo("Incendio");
        assertThat(response.getDescripcion()).isEqualTo("Incendio en el piso 2");
        assertThat(response.getImpacto()).isEqualTo(Impacto.ALTO);
        assertThat(response.getUrgencia()).isEqualTo(Urgencia.ALTA);
        assertThat(response.getEstado()).isEqualTo(Estado.EN_PROCESO);
        assertThat(response.getCategoriaId()).isEqualTo(5L);
        assertThat(response.getUsuarioId()).isEqualTo(10L);
        assertThat(response.getUbicacionId()).isEqualTo(15L);
        assertThat(response.getFechaRegistro()).isEqualTo(LocalDateTime.of(2024, 4, 25, 10, 0));
        assertThat(response.getFechaActualizacion()).isEqualTo(LocalDateTime.of(2024, 4, 26, 10, 0));
        assertThat(response.getArchivos()).hasSize(1);
        assertThat(response.getArchivos().get(0).getId()).isEqualTo(20L);
    }

    @Test
    void toResponse_deberiaManejarValoresNulosEnObjetosAnidados() {
        // Arrange
        Incidencia incidencia = new Incidencia();
        incidencia.setId(99L);
        incidencia.setTitulo("Título incompleto");
        incidencia.setDescripcion(null);
        incidencia.setImpacto(Impacto.BAJO);
        incidencia.setUrgencia(Urgencia.MEDIA);
        incidencia.setEstado(Estado.RESUELTA);
        incidencia.setCategoria(null);
        incidencia.setUsuario(null);
        incidencia.setUbicacion(null);
        incidencia.setFechaRegistro(LocalDateTime.now());
        incidencia.setFechaActualizacion(LocalDateTime.now());
        incidencia.setArchivos(null);

        // Act
        IncidenciaResponse response = incidenciaMapper.toResponse(incidencia);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(99L);
        assertThat(response.getTitulo()).isEqualTo("Título incompleto");
        assertThat(response.getDescripcion()).isNull();
        assertThat(response.getImpacto()).isEqualTo(Impacto.BAJO);
        assertThat(response.getUrgencia()).isEqualTo(Urgencia.MEDIA);
        assertThat(response.getEstado()).isEqualTo(Estado.RESUELTA);
        assertThat(response.getCategoriaId()).isNull();
        assertThat(response.getUsuarioId()).isNull();
        assertThat(response.getUbicacionId()).isNull();
        assertThat(response.getLatitud()).isNull();
        assertThat(response.getLongitud()).isNull();
        assertThat(response.getArchivos()).isNull();
    }

    @Test
    void toResponse_deberiaManejarListaVaciaDeArchivos() {
        // Arrange
        Incidencia incidencia = new Incidencia();
        incidencia.setId(2L);
        incidencia.setTitulo("Sin archivos");
        incidencia.setDescripcion("Prueba sin archivos");
        incidencia.setImpacto(Impacto.ALTO);
        incidencia.setUrgencia(Urgencia.ALTA);
        incidencia.setEstado(Estado.PENDIENTE);
        incidencia.setFechaRegistro(LocalDateTime.now());
        incidencia.setFechaActualizacion(LocalDateTime.now());
        incidencia.setCategoria(new Categoria());
        incidencia.setUsuario(new Usuario());
        incidencia.setUbicacion(new UbicacionGeografica());
        incidencia.setArchivos(Collections.emptyList());

        // Act
        IncidenciaResponse response = incidenciaMapper.toResponse(incidencia);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getArchivos()).isEmpty();
    }


}
