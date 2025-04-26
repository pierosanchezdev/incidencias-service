package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.IncidenciaService;
import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.IncidenciaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/incidencias")
@RequiredArgsConstructor
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    @PostMapping
    public ResponseEntity<IncidenciaResponse> crearIncidencia(@RequestBody @Valid IncidenciaRequest incidenciaRequest) {
        IncidenciaResponse response = incidenciaService.crearIncidencia(incidenciaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
