package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import com.conecta.incidencias.entity.Comunero;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.enums.RolUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComuneroMapperTest {

    private ComuneroMapper comuneroMapper;

    @BeforeEach
    void setUp() {
        comuneroMapper = new ComuneroMapperImpl();
    }

    @Test
    void toEntity_deberiaMapearRequestAEntidad() {
        // Arrange
        ComuneroRequest request = new ComuneroRequest();
        request.setNombres("Ana");
        request.setApellidos("Gonzales");
        request.setTelefono("999888777");
        request.setCorreo("ana@example.com");
        request.setTipoDocumento("DNI");
        request.setNumeroDocumento("12345678");

        // Act
        Comunero comunero = comuneroMapper.toEntity(request);

        // Assert
        assertThat(comunero).isNotNull();
        assertThat(comunero.getNombres()).isEqualTo("Ana");
        assertThat(comunero.getApellidos()).isEqualTo("Gonzales");
        assertThat(comunero.getTelefono()).isEqualTo("999888777");
        assertThat(comunero.getCorreo()).isEqualTo("ana@example.com");
        assertThat(comunero.getTipoDocumento()).isEqualTo("DNI");
        assertThat(comunero.getNumeroDocumento()).isEqualTo("12345678");
    }

    @Test
    void toResponse_deberiaMapearEntidadAResponse() {
        // Arrange
        Comunero comunero = new Comunero();
        comunero.setId(1L);
        comunero.setNombres("Ana");
        comunero.setApellidos("Gonzales");
        comunero.setTelefono("999888777");
        comunero.setCorreo("ana@example.com");

        // Act
        ComuneroResponse response = comuneroMapper.toResponse(comunero);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNombres()).isEqualTo("Ana");
        assertThat(response.getApellidos()).isEqualTo("Gonzales");
        assertThat(response.getTelefono()).isEqualTo("999888777");
        assertThat(response.getCorreo()).isEqualTo("ana@example.com");
    }

    @Test
    void toResponse_deberiaMapearTodosLosCamposIncluyendoUsuarioYUbicacion() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(10L)
                .username("ana_user")
                .email("ana@correo.com")
                .rol(RolUsuario.COMUNERO)
                .build();

        UbicacionGeografica ubicacion = UbicacionGeografica.builder()
                .pais("Perú")
                .departamento("Lima")
                .provincia("Lima")
                .distrito("San Juan")
                .ubigeo("150132")
                .nombreLocalidad("Villa")
                .latitud(-12.0432)
                .longitud(-77.0282)
                .build();

        Comunero comunero = Comunero.builder()
                .id(1L)
                .nombres("Ana")
                .apellidos("Gonzales")
                .tipoDocumento("DNI")
                .numeroDocumento("12345678")
                .telefono("999888777")
                .correo("ana@example.com")
                .usuario(usuario)
                .ubicacion(ubicacion)
                .build();

        // Act
        ComuneroResponse response = comuneroMapper.toResponse(comunero);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNombres()).isEqualTo("Ana");
        assertThat(response.getApellidos()).isEqualTo("Gonzales");
        assertThat(response.getTipoDocumento()).isEqualTo("DNI");
        assertThat(response.getNumeroDocumento()).isEqualTo("12345678");
        assertThat(response.getTelefono()).isEqualTo("999888777");
        assertThat(response.getCorreo()).isEqualTo("ana@example.com");

        assertThat(response.getUsuarioId()).isEqualTo(10L);
        assertThat(response.getUsername()).isEqualTo("ana_user");
        assertThat(response.getEmailUsuario()).isEqualTo("ana@correo.com");
        assertThat(response.getRol()).isEqualTo("COMUNERO");

        assertThat(response.getPais()).isEqualTo("Perú");
        assertThat(response.getDepartamento()).isEqualTo("Lima");
        assertThat(response.getProvincia()).isEqualTo("Lima");
        assertThat(response.getDistrito()).isEqualTo("San Juan");
        assertThat(response.getUbigeo()).isEqualTo("150132");
        assertThat(response.getNombreLocalidad()).isEqualTo("Villa");
        assertThat(response.getLatitud()).isEqualTo(-12.0432);
        assertThat(response.getLongitud()).isEqualTo(-77.0282);
    }

    @Test
    void toResponse_deberiaRetornarCamposNulosCuandoUsuarioYUbicacionSonNulos() {
        // Arrange
        Comunero comunero = Comunero.builder()
                .id(2L)
                .nombres("Luis")
                .apellidos("Torres")
                .tipoDocumento("DNI")
                .numeroDocumento("87654321")
                .telefono("999000111")
                .correo("luis@example.com")
                .usuario(null)
                .ubicacion(null)
                .build();

        // Act
        ComuneroResponse response = comuneroMapper.toResponse(comunero);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getUsuarioId()).isNull();
        assertThat(response.getUsername()).isNull();
        assertThat(response.getEmailUsuario()).isNull();
        assertThat(response.getRol()).isNull();

        assertThat(response.getPais()).isNull();
        assertThat(response.getDepartamento()).isNull();
        assertThat(response.getProvincia()).isNull();
        assertThat(response.getDistrito()).isNull();
        assertThat(response.getUbigeo()).isNull();
        assertThat(response.getNombreLocalidad()).isNull();
        assertThat(response.getLatitud()).isNull();
        assertThat(response.getLongitud()).isNull();
    }
}
