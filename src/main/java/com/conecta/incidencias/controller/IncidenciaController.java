package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.IncidenciaService;
import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.IncidenciaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/incidencias")
@RequiredArgsConstructor
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearIncidencia(
            @RequestPart("datos") IncidenciaRequest request,
            @RequestPart(value = "archivos", required = false) List<MultipartFile> archivos) {

        System.out.println("Request recibida: " + request.getTitulo());
        if (archivos != null) {
            for (MultipartFile archivo : archivos) {
                System.out.println("Archivo: " + archivo.getOriginalFilename() + ", tipo: " + archivo.getContentType());
            }
        }
        var incidenciaResponse = incidenciaService.crearIncidencia(request, archivos);
        return ResponseEntity.status(HttpStatus.CREATED).body(incidenciaResponse);
    }

    @GetMapping("/{id}")
    public Optional<IncidenciaResponse> obtenerIncidencia(@PathVariable Long id) {
        return incidenciaService.obtenerIncidenciaPorId(id);
    }

    @GetMapping
    public List<IncidenciaResponse> listarIncidencias() {
        return incidenciaService.listarIncidencias();
    }

    @GetMapping("/mis-incidencias")
    public List<IncidenciaResponse> listarMisIncidencias(@RequestParam Long usuarioId) {
        return incidenciaService.listarIncidenciasPorUsuario(usuarioId);
    }

    @PutMapping("/{id}")
    public IncidenciaResponse actualizarIncidencia(@PathVariable Long id,
                                                   @Valid @RequestBody IncidenciaRequest request) {
        return incidenciaService.actualizarIncidencia(id, request);
    }
}
