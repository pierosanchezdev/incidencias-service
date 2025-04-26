package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.UsuarioService;
import com.conecta.incidencias.dto.request.UsuarioRequest;
import com.conecta.incidencias.dto.response.UsuarioResponse;
import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.mapper.UsuarioMapper;
import com.conecta.incidencias.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setActivo(true);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuarioGuardado);
    }

    @Override
    public Optional<UsuarioResponse> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toResponse);
    }

    @Override
    public UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuarioExistente.setEmail(request.getEmail());
        usuarioExistente.setPassword(request.getPassword()); // Aquí luego debes encriptar password en seguridad
        usuarioExistente.setRol(request.getRol());

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toResponse(usuarioActualizado);
    }
}
