package com.conecta.incidencias.dto.response;
import com.conecta.incidencias.enums.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String username;
    private String email;
    private RolUsuario rol;
    private boolean activo;
}
