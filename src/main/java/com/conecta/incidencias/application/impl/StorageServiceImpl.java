package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.StorageService;
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

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(rootLocation);
    }

    @Override
    public String subirArchivo(MultipartFile archivo) {
        try {
            String filename = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Files.copy(archivo.getInputStream(), rootLocation.resolve(filename));
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }
}
