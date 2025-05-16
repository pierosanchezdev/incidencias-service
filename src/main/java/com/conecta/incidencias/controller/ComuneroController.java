package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.ComuneroService;
import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comuneros")
@RequiredArgsConstructor
public class ComuneroController {

    private final ComuneroService comuneroService;

    @PostMapping
    public ComuneroResponse crearComunero(@Valid @RequestBody ComuneroRequest request) {
        return comuneroService.crearComunero(request);
    }

    @GetMapping("/{id}")
    public Optional<ComuneroResponse> obtenerComunero(@PathVariable Long id) {
        return comuneroService.obtenerComuneroPorId(id);
    }

    @PutMapping("/{id}")
    public ComuneroResponse actualizarComunero(@PathVariable Long id,
                                               @Valid @RequestBody ComuneroRequest request) {
        return comuneroService.actualizarComunero(id, request);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ComuneroResponse> obtenerPorEmail(@PathVariable String email) {
        ComuneroResponse response = comuneroService.findByUsuarioEmail(email);
        return ResponseEntity.ok(response);
    }

}
