package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.HistorialEstadoService;
import com.conecta.incidencias.dto.request.CambioEstadoRequest;
import com.conecta.incidencias.dto.response.HistorialEstadoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidencias/{incidenciaId}/historial")
@RequiredArgsConstructor
public class HistorialEstadoController {

    private final HistorialEstadoService historialEstadoService;

    @PostMapping
    public HistorialEstadoResponse registrarCambioEstado(@PathVariable Long incidenciaId,
                                                         @RequestParam Long operadorId,
                                                         @Valid @RequestBody CambioEstadoRequest request) {
        return historialEstadoService.registrarCambioEstado(incidenciaId, operadorId, request);
    }

    @GetMapping
    public List<HistorialEstadoResponse> listarHistorial(@PathVariable Long incidenciaId) {
        return historialEstadoService.listarHistorialPorIncidencia(incidenciaId);
    }
}
