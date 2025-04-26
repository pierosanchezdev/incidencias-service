package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.IncidenciaService;
import com.conecta.incidencias.dto.request.ArchivoRequest;
import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.IncidenciaResponse;
import com.conecta.incidencias.entity.*;
import com.conecta.incidencias.mapper.ArchivoMapper;
import com.conecta.incidencias.mapper.IncidenciaMapper;
import com.conecta.incidencias.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidenciaServiceImpl implements IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final UbicacionGeograficaRepository ubicacionRepository;
    private final ArchivoRepository archivoRepository;
    private final IncidenciaMapper incidenciaMapper;
    private final ArchivoMapper archivoMapper;

    @Override
    @Transactional
    public IncidenciaResponse crearIncidencia(IncidenciaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        UbicacionGeografica ubicacion = ubicacionRepository.findById(request.getUbicacionId())
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        Incidencia incidencia = incidenciaMapper.toEntity(request);
        incidencia.setUsuario(usuario);
        incidencia.setCategoria(categoria);
        incidencia.setUbicacion(ubicacion);
        incidencia.setEstado(com.conecta.incidencias.enums.Estado.PENDIENTE);

        Incidencia incidenciaGuardada = incidenciaRepository.save(incidencia);

        // Manejar los archivos asociados
        if (request.getArchivos() != null) {
            List<Archivo> archivos = request.getArchivos().stream()
                    .map(archivoRequest -> crearArchivoDesdeRequest(archivoRequest, incidenciaGuardada))
                    .collect(Collectors.toList());
            archivoRepository.saveAll(archivos);
        }

        return incidenciaMapper.toResponse(incidenciaGuardada);
    }

    private Archivo crearArchivoDesdeRequest(ArchivoRequest archivoRequest, Incidencia incidencia) {
        Archivo archivo = archivoMapper.toEntity(archivoRequest);
        archivo.setIncidencia(incidencia);
        return archivo;
    }

    @Override
    public Optional<IncidenciaResponse> obtenerIncidenciaPorId(Long id) {
        return incidenciaRepository.findById(id)
                .map(incidenciaMapper::toResponse);
    }

    @Override
    public List<IncidenciaResponse> listarIncidencias() {
        return incidenciaRepository.findAll()
                .stream()
                .map(incidenciaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncidenciaResponse> listarIncidenciasPorUsuario(Long usuarioId) {
        return incidenciaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(incidenciaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IncidenciaResponse actualizarIncidencia(Long id, IncidenciaRequest request) {
        Incidencia incidenciaExistente = incidenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        UbicacionGeografica ubicacion = ubicacionRepository.findById(request.getUbicacionId())
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        incidenciaExistente.setTitulo(request.getTitulo());
        incidenciaExistente.setDescripcion(request.getDescripcion());
        incidenciaExistente.setImpacto(request.getImpacto());
        incidenciaExistente.setUrgencia(request.getUrgencia());
        incidenciaExistente.setCategoria(categoria);
        incidenciaExistente.setUbicacion(ubicacion);

        Incidencia incidenciaActualizada = incidenciaRepository.save(incidenciaExistente);
        return incidenciaMapper.toResponse(incidenciaActualizada);
    }
}
