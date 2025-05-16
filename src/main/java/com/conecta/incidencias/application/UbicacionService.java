package com.conecta.incidencias.application;

import com.conecta.incidencias.dto.request.UbicacionRequest;

import java.math.BigDecimal;

public interface UbicacionService {
    Integer crear(UbicacionRequest request);
}
