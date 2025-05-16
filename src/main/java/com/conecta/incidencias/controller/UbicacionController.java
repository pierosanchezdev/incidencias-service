package com.conecta.incidencias.controller;

import com.conecta.incidencias.application.UbicacionService;
import com.conecta.incidencias.dto.request.UbicacionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/ubicacion")
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionService ubicacionService;
    @PostMapping
    public ResponseEntity<Integer> crearUbicacion(@RequestBody UbicacionRequest request) {
        Integer id = ubicacionService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
