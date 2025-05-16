package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.ArchivoService;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/archivos")
@RequiredArgsConstructor
public class ArchivoController {

    private final ArchivoService archivoService;

    @GetMapping("/{id}")
    public Optional<ArchivoResponse> obtenerArchivo(@PathVariable Long id) {
        return archivoService.obtenerArchivoPorId(id);
    }

    @GetMapping("/incidencia/{incidenciaId}")
    public List<ArchivoResponse> listarArchivosPorIncidencia(@PathVariable Long incidenciaId) {
        return archivoService.listarArchivosPorIncidencia(incidenciaId);
    }

    @DeleteMapping("/{id}")
    public void eliminarArchivo(@PathVariable Long id) {
        archivoService.eliminarArchivo(id);
    }

    @GetMapping("/ver/uploads/{nombreArchivo:.+}")
    public ResponseEntity<Resource> verArchivo(@PathVariable String nombreArchivo) {
        try {
            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).normalize();
            Resource recurso = new UrlResource(rutaArchivo.toUri());
            if (!recurso.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(rutaArchivo);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                    .body(recurso);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
