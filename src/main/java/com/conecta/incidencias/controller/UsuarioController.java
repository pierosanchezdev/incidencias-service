package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.UsuarioService;
import com.conecta.incidencias.dto.request.UsuarioRequest;
import com.conecta.incidencias.dto.response.UsuarioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public UsuarioResponse crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.crearUsuario(request);
    }

    @GetMapping("/{id}")
    public Optional<UsuarioResponse> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizarUsuario(@PathVariable Long id,
                                             @Valid @RequestBody UsuarioRequest request) {
        return usuarioService.actualizarUsuario(id, request);
    }
}
