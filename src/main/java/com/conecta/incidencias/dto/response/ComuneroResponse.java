package com.conecta.incidencias.dto.response;

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
public class ComuneroResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private String telefono;
    private String correo;

    private String emailUsuario;
    private String username;
    private String rol;
    private Long usuarioId;

    // Ubicación
    private String pais;
    private String departamento;
    private String provincia;
    private String distrito;
    private String ubigeo;
    private String nombreLocalidad;
    private Double latitud;
    private Double longitud;
}
