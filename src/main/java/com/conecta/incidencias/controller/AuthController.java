package com.conecta.incidencias.controller;

import com.conecta.incidencias.dto.request.LoginRequest;
import com.conecta.incidencias.dto.response.LoginResponse;
import com.conecta.incidencias.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }
}
