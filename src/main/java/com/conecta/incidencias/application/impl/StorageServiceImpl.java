package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.StorageService;
import com.conecta.incidencias.exception.StorageException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation = Paths.get("uploads");
    private static final String ERROR_TIPO = "Tipo de archivo no permitido: ";
    private static final String ERROR_VACIO = "Archivo vacío o no proporcionado";
    private static final java.util.List<String> TIPOS_VALIDOS = java.util.List.of(
            "image/jpeg", "image/png", "video/mp4"
    );

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(rootLocation);
    }

    @Override
    public String subirArchivo(MultipartFile archivo) {
        validarTipoDeArchivo(archivo);

        try {
            String filename = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Files.copy(archivo.getInputStream(), rootLocation.resolve(filename));
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new StorageException("Falla de almacenamiento", e);
        }
    }

    private void validarTipoDeArchivo(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException(ERROR_VACIO);
        }

        String tipo = archivo.getContentType();
        if (!TIPOS_VALIDOS.contains(tipo)) {
            throw new IllegalArgumentException(ERROR_TIPO + tipo);
        }
    }
}
