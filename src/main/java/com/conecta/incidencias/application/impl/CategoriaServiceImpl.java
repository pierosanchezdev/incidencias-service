package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.CategoriaService;
import com.conecta.incidencias.dto.request.CategoriaRequest;
import com.conecta.incidencias.dto.response.CategoriaResponse;
import com.conecta.incidencias.entity.Categoria;
import com.conecta.incidencias.mapper.CategoriaMapper;
import com.conecta.incidencias.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public CategoriaResponse crearCategoria(CategoriaRequest request) {
        Categoria categoria = categoriaMapper.toEntity(request);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return categoriaMapper.toResponse(categoriaGuardada);
    }

    @Override
    public Optional<CategoriaResponse> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toResponse);
    }

    @Override
    public CategoriaResponse actualizarCategoria(Long id, CategoriaRequest request) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoriaExistente.setNombre(request.getNombre());
        categoriaExistente.setDescripcion(request.getDescripcion());

        Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);
        return categoriaMapper.toResponse(categoriaActualizada);
    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public List<CategoriaResponse> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponse)
                .collect(Collectors.toList());
    }
}
