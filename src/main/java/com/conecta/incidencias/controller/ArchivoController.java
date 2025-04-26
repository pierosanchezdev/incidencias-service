package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.ArchivoService;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
