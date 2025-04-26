package com.conecta.incidencias.dto.request;

import com.conecta.incidencias.enums.Estado;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CambioEstadoRequest {

    @NotNull(message = "El nuevo estado es obligatorio")
    private Estado estadoNuevo;

    @Size(max = 500)
    private String comentario;
}
