package com.conecta.incidencias.dto.request;

import com.conecta.incidencias.enums.TipoArchivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ArchivoRequest {

    @NotBlank(message = "La URL del archivo es obligatoria")
    private String url;

    @NotNull(message = "El tipo de archivo es obligatorio")
    private TipoArchivo tipo;

    private Long tamanoBytes;
}
