package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.OperadorService;
import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/operadores")
@RequiredArgsConstructor
public class OperadorController {

    private final OperadorService operadorService;

    @PostMapping
    public OperadorResponse crearOperador(@Valid @RequestBody OperadorRequest request) {
        return operadorService.crearOperador(request);
    }

    @GetMapping("/{id}")
    public Optional<OperadorResponse> obtenerOperador(@PathVariable Long id) {
        return operadorService.obtenerOperadorPorId(id);
    }

    @PutMapping("/{id}")
    public OperadorResponse actualizarOperador(@PathVariable Long id,
                                               @Valid @RequestBody OperadorRequest request) {
        return operadorService.actualizarOperador(id, request);
    }
}
