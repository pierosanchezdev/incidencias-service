package com.conecta.incidencias.application.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageServiceImplTest {

    private StorageServiceImpl storageService;

    @BeforeEach
    void setUp() throws IOException {
        storageService = new StorageServiceImpl();
        storageService.init(); // crea la carpeta uploads
    }

    @Test
    void subirArchivo_deberiaGuardarArchivoCorrectamente() throws IOException {
        // Arrange
        byte[] content = "contenido de prueba".getBytes();
        MultipartFile archivo = new MockMultipartFile("archivo", "prueba.jpg", "image/png", content);

        // Act
        String path = storageService.subirArchivo(archivo);

        // Assert
        assertThat(path).startsWith("/uploads/");
        Path fullPath = Path.of("uploads", path.substring("/uploads/".length()));
        assertThat(Files.exists(fullPath)).isTrue();

        // Limpieza
        Files.deleteIfExists(fullPath);
    }

    @Test
    void subirArchivo_deberiaLanzarExcepcionSiFallaIO() throws IOException {
        // Arrange
        MultipartFile archivoMock = Mockito.mock(MultipartFile.class);
        Mockito.when(archivoMock.getOriginalFilename()).thenReturn("error.txt");
        Mockito.when(archivoMock.getInputStream()).thenThrow(new IOException("Falla simulada"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> storageService.subirArchivo(archivoMock));
    }
}
