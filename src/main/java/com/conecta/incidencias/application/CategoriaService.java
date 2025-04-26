package com.conecta.incidencias.application;

import com.conecta.incidencias.dto.request.CategoriaRequest;
import com.conecta.incidencias.dto.response.CategoriaResponse;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    CategoriaResponse crearCategoria(CategoriaRequest request);
    Optional<CategoriaResponse> obtenerCategoriaPorId(Long id);
    CategoriaResponse actualizarCategoria(Long id, CategoriaRequest request);
    void eliminarCategoria(Long id);
    List<CategoriaResponse> listarCategorias();
}
