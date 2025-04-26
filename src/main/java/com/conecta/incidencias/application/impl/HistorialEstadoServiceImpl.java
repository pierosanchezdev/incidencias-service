package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.HistorialEstadoService;
import com.conecta.incidencias.dto.request.CambioEstadoRequest;
import com.conecta.incidencias.dto.response.HistorialEstadoResponse;
import com.conecta.incidencias.entity.*;
import com.conecta.incidencias.enums.Estado;
import com.conecta.incidencias.mapper.HistorialEstadoMapper;
import com.conecta.incidencias.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialEstadoServiceImpl implements HistorialEstadoService {

    private final HistorialEstadoRepository historialEstadoRepository;
    private final IncidenciaRepository incidenciaRepository;
    private final OperadorRepository operadorRepository;
    private final HistorialEstadoMapper historialEstadoMapper;

    @Override
    @Transactional
    public HistorialEstadoResponse registrarCambioEstado(Long incidenciaId, Long operadorId, CambioEstadoRequest request) {
        Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        Operador operador = operadorRepository.findById(operadorId)
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));

        Estado estadoAnterior = incidencia.getEstado();

        incidencia.setEstado(request.getEstadoNuevo());
        incidenciaRepository.save(incidencia);

        HistorialEstado historial = HistorialEstado.builder()
                .incidencia(incidencia)
                .operador(operador)
                .estadoAnterior(estadoAnterior)
                .estadoNuevo(request.getEstadoNuevo())
                .comentario(request.getComentario())
                .build();

        HistorialEstado historialGuardado = historialEstadoRepository.save(historial);

        return historialEstadoMapper.toResponse(historialGuardado);
    }

    @Override
    public List<HistorialEstadoResponse> listarHistorialPorIncidencia(Long incidenciaId) {
        return historialEstadoRepository.findByIncidenciaIdOrderByFechaCambioAsc(incidenciaId)
                .stream()
                .map(historialEstadoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
