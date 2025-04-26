package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.OperadorService;
import com.conecta.incidencias.dto.request.OperadorRequest;
import com.conecta.incidencias.dto.response.OperadorResponse;
import com.conecta.incidencias.entity.Operador;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.mapper.OperadorMapper;
import com.conecta.incidencias.repository.OperadorRepository;
import com.conecta.incidencias.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperadorServiceImpl implements OperadorService {

    private final OperadorRepository operadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final OperadorMapper operadorMapper;

    @Override
    public OperadorResponse crearOperador(OperadorRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Operador operador = operadorMapper.toEntity(request);
        operador.setUsuario(usuario);

        Operador operadorGuardado = operadorRepository.save(operador);
        return operadorMapper.toResponse(operadorGuardado);
    }

    @Override
    public Optional<OperadorResponse> obtenerOperadorPorId(Long id) {
        return operadorRepository.findById(id)
                .map(operadorMapper::toResponse);
    }

    @Override
    public OperadorResponse actualizarOperador(Long id, OperadorRequest request) {
        Operador operadorExistente = operadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));

        operadorExistente.setNombres(request.getNombres());
        operadorExistente.setApellidos(request.getApellidos());
        operadorExistente.setCargo(request.getCargo());
        operadorExistente.setTelefono(request.getTelefono());

        Operador operadorActualizado = operadorRepository.save(operadorExistente);
        return operadorMapper.toResponse(operadorActualizado);
    }
}
