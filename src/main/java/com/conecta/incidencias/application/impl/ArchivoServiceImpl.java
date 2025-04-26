package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.ArchivoService;
import com.conecta.incidencias.dto.response.ArchivoResponse;
import com.conecta.incidencias.mapper.ArchivoMapper;
import com.conecta.incidencias.repository.ArchivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;
    private final ArchivoMapper archivoMapper;

    @Override
    public Optional<ArchivoResponse> obtenerArchivoPorId(Long id) {
        return archivoRepository.findById(id)
                .map(archivoMapper::toResponse);
    }

    @Override
    public List<ArchivoResponse> listarArchivosPorIncidencia(Long incidenciaId) {
        return archivoRepository.findByIncidenciaId(incidenciaId)
                .stream()
                .map(archivoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarArchivo(Long id) {
        archivoRepository.deleteById(id);
    }
}
