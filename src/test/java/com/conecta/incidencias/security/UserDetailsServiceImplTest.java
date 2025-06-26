package com.conecta.incidencias.security;

import com.conecta.incidencias.entity.Usuario;
import com.conecta.incidencias.enums.RolUsuario;
import com.conecta.incidencias.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_deberiaRetornarUserDetailsCuandoUsuarioExiste() {
        // Arrange
        String email = "test@conecta.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword("hashedPassword123");
        usuario.setActivo(true);
        usuario.setRol(RolUsuario.OPERADOR);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Assert
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(email);
        assertThat(userDetails.getPassword()).isEqualTo("hashedPassword123");
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.getAuthorities()).hasSize(1);
    }

    @Test
    void loadUserByUsername_deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        // Arrange
        String email = "noexiste@correo.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email));
    }
}
