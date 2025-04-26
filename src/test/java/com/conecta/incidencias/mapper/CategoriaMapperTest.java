package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.CategoriaRequest;
import com.conecta.incidencias.dto.response.CategoriaResponse;
import com.conecta.incidencias.entity.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoriaMapperTest {

    private CategoriaMapper categoriaMapper;

    @BeforeEach
    void setUp() {
        categoriaMapper = new CategoriaMapperImpl();
    }

    @Test
    void toEntity_deberiaMapearRequestAEntidad() {
        // Arrange
        CategoriaRequest request = new CategoriaRequest();
        request.setNombre("Salud");
        request.setDescripcion("Relacionadas a salud publica");

        // Act
        Categoria categoria = categoriaMapper.toEntity(request);

        // Assert
        assertThat(categoria).isNotNull();
        assertThat(categoria.getNombre()).isEqualTo("Salud");
        assertThat(categoria.getDescripcion()).isEqualTo("Relacionadas a salud publica");
    }

    @Test
    void toResponse_deberiaMapearEntidadAResponse() {
        // Arrange
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Salud");
        categoria.setDescripcion("Relacionadas a salud publica");

        // Act
        CategoriaResponse response = categoriaMapper.toResponse(categoria);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNombre()).isEqualTo("Salud");
        assertThat(response.getDescripcion()).isEqualTo("Relacionadas a salud publica");
    }
}
