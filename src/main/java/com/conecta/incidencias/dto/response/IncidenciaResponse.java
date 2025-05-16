package com.conecta.incidencias.dto.response;

import com.conecta.incidencias.enums.Estado;
import com.conecta.incidencias.enums.Impacto;
import com.conecta.incidencias.enums.Urgencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidenciaResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long categoriaId;
    private Impacto impacto;
    private Urgencia urgencia;
    private Estado estado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private Long usuarioId;
    private Long ubicacionId;
    private List<ArchivoResponse> archivos;
    private Double latitud;
    private Double longitud;

}
