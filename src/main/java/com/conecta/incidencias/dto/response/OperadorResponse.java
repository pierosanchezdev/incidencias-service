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
public class OperadorResponse {

    private Long id;
    private Long usuarioId;
    private String nombres;
    private String apellidos;
    private String cargo;
    private String telefono;
}
