package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.CategoriaService;
import com.conecta.incidencias.dto.request.CategoriaRequest;
import com.conecta.incidencias.dto.response.CategoriaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public CategoriaResponse crearCategoria(@Valid @RequestBody CategoriaRequest request) {
        return categoriaService.crearCategoria(request);
    }

    @GetMapping("/{id}")
    public Optional<CategoriaResponse> obtenerCategoria(@PathVariable Long id) {
        return categoriaService.obtenerCategoriaPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaResponse actualizarCategoria(@PathVariable Long id,
                                                 @Valid @RequestBody CategoriaRequest request) {
        return categoriaService.actualizarCategoria(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
    }

    @GetMapping
    public List<CategoriaResponse> listarCategorias() {
        return categoriaService.listarCategorias();
    }
}
