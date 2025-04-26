package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.ArchivoRequest;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import com.conecta.incidencias.entity.Archivo;
import com.conecta.incidencias.enums.TipoArchivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArchivoMapperTest {

    private ArchivoMapper archivoMapper;

    @BeforeEach
    void setUp() {
        archivoMapper = new ArchivoMapperImpl();
    }

    @Test
    void toEntity_deberiaMapearRequestAEntidad() {
        // Arrange
        ArchivoRequest request = new ArchivoRequest();
        request.setUrl("https://s3.aws.com/archivo1.jpg");
        request.setTipo(TipoArchivo.IMAGEN);
        request.setTamanoBytes(2048L);

        // Act
        Archivo archivo = archivoMapper.toEntity(request);

        // Assert
        assertThat(archivo).isNotNull();
        assertThat(archivo.getUrl()).isEqualTo("https://s3.aws.com/archivo1.jpg");
        assertThat(archivo.getTipo().name()).isEqualTo("IMAGEN");
        assertThat(archivo.getTamanoBytes()).isEqualTo(2048L);
    }

    @Test
    void toResponse_deberiaMapearEntidadAResponse() {
        // Arrange
        Archivo archivo = new Archivo();
        archivo.setId(1L);
        archivo.setUrl("https://s3.aws.com/archivo1.jpg");
        archivo.setTipo(TipoArchivo.IMAGEN);
        archivo.setTamanoBytes(2048L);

        // Act
        ArchivoResponse response = archivoMapper.toResponse(archivo);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUrl()).isEqualTo("https://s3.aws.com/archivo1.jpg");
        assertThat(response.getTipo().toString()).isEqualTo("IMAGEN");
        assertThat(response.getTamanoBytes()).isEqualTo(2048L);
    }
}
