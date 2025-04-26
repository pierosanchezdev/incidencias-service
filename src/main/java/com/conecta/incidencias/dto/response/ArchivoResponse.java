package com.conecta.incidencias.dto.response;

import com.conecta.incidencias.enums.TipoArchivo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArchivoResponse {
    private Long id;
    private String url;
    private TipoArchivo tipo;
    private Long tamanoBytes;
    private LocalDateTime fechaSubida;
}
