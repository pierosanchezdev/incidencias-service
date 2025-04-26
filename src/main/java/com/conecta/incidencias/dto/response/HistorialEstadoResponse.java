package com.conecta.incidencias.dto.response;

import com.conecta.incidencias.enums.Estado;
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
public class HistorialEstadoResponse {

    private Long id;
    private Long incidenciaId;
    private Long operadorId;
    private Estado estadoNuevo;
    private Estado estadoAnterior;
    private String comentario;
    private LocalDateTime fechaCambio;
}
