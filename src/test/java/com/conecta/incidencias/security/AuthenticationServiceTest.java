package com.conecta.incidencias.security;

import com.conecta.incidencias.dto.request.LoginRequest;
import com.conecta.incidencias.dto.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_deberiaAutenticarYRetornarToken() {
        // Arrange
        String email = "usuario@correo.com";
        String password = "secreto123";
        String tokenEsperado = "jwt.token.generado";

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        UserDetails userDetailsMock = new User(email, password, Collections.emptyList());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetailsMock);
        when(jwtUtils.generarToken(userDetailsMock)).thenReturn(tokenEsperado);

        // Act
        LoginResponse response = authenticationService.login(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(tokenEsperado);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(email);
        verify(jwtUtils).generarToken(userDetailsMock);
    }
}
