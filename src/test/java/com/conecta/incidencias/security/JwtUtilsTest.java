package com.conecta.incidencias.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private final String secret = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef"; // 64 bytes

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();

        // Inyectamos manualmente los valores porque normalmente vienen de application.properties
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 3600000);
    }

    @Test
    void generarToken_shouldReturnValidToken() {
        // Arrange
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());

        // Act
        String token = jwtUtils.generarToken(userDetails);

        // Assert
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
    }

    @Test
    void extraerUsername_shouldReturnUsername() {
        // Arrange
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        String token = jwtUtils.generarToken(userDetails);

        // Act
        String username = jwtUtils.extraerUsername(token);

        // Assert
        assertThat(username).isEqualTo("testuser");
    }

    @Test
    void validarToken_shouldReturnTrueIfValid() {
        // Arrange
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        String token = jwtUtils.generarToken(userDetails);

        // Act
        boolean isValid = jwtUtils.validarToken(token, userDetails);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void estaExpirado_shouldReturnFalseForNewToken() {
        // Arrange
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        String token = jwtUtils.generarToken(userDetails);

        // Act
        boolean isExpired = jwtUtils.estaExpirado(token);

        // Assert
        assertThat(isExpired).isFalse();
    }
}
