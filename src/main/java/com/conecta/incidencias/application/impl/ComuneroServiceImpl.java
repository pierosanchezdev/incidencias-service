package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.ComuneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.conecta.incidencias.dto.request.ComuneroRequest;
import com.conecta.incidencias.dto.response.ComuneroResponse;
import com.conecta.incidencias.entity.Comunero;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.mapper.ComuneroMapper;
import com.conecta.incidencias.repository.ComuneroRepository;
import com.conecta.incidencias.repository.UbicacionGeograficaRepository;
import com.conecta.incidencias.repository.UsuarioRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComuneroServiceImpl implements ComuneroService {

    private final ComuneroRepository comuneroRepository;
    private final UsuarioRepository usuarioRepository;
    private final UbicacionGeograficaRepository ubicacionRepository;
    private final ComuneroMapper comuneroMapper;

    @Override
    public ComuneroResponse crearComunero(ComuneroRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UbicacionGeografica ubicacion = ubicacionRepository.findById(request.getUbicacionId())
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        Comunero comunero = comuneroMapper.toEntity(request);
        comunero.setUsuario(usuario);
        comunero.setUbicacion(ubicacion);

        Comunero comuneroGuardado = comuneroRepository.save(comunero);
        return comuneroMapper.toResponse(comuneroGuardado);
    }

    @Override
    public Optional<ComuneroResponse> obtenerComuneroPorId(Long id) {
        return comuneroRepository.findById(id)
                .map(comuneroMapper::toResponse);
    }

    @Override
    public ComuneroResponse actualizarComunero(Long id, ComuneroRequest request) {
        Comunero comuneroExistente = comuneroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comunero no encontrado"));

        UbicacionGeografica ubicacion = ubicacionRepository.findById(request.getUbicacionId())
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        comuneroExistente.setNombres(request.getNombres());
        comuneroExistente.setApellidos(request.getApellidos());
        comuneroExistente.setTipoDocumento(request.getTipoDocumento());
        comuneroExistente.setNumeroDocumento(request.getNumeroDocumento());
        comuneroExistente.setTelefono(request.getTelefono());
        comuneroExistente.setCorreo(request.getCorreo());
        comuneroExistente.setUbicacion(ubicacion);

        Comunero comuneroActualizado = comuneroRepository.save(comuneroExistente);
        return comuneroMapper.toResponse(comuneroActualizado);
    }
}
