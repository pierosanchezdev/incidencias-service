package com.conecta.incidencias.application.impl;

import com.conecta.incidencias.application.UbicacionService;
import com.conecta.incidencias.dto.request.UbicacionRequest;
import com.conecta.incidencias.entity.UbicacionGeografica;
import com.conecta.incidencias.mapper.UbicacionMapper;
import com.conecta.incidencias.repository.UbicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService {
    private final UbicacionRepository ubicacionRepository;
    private final UbicacionMapper ubicacionMapper;

    @Override
    public Integer crear(UbicacionRequest request) {
        UbicacionGeografica entidad = ubicacionMapper.toEntity(request);
        ubicacionRepository.save(entidad);
        return entidad.getId().intValue();
    }
}
