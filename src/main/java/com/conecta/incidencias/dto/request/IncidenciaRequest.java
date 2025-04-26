package com.conecta.incidencias.dto.request;

import com.conecta.incidencias.enums.Impacto;
import com.conecta.incidencias.enums.Urgencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidenciaRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200)
    private String titulo;

    @Size(max = 1000)
    private String descripcion;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    @NotNull(message = "El impacto es obligatorio")
    private Impacto impacto;

    @NotNull(message = "La urgencia es obligatoria")
    private Urgencia urgencia;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID de la ubicación es obligatorio")
    private Long ubicacionId;

    private List<ArchivoRequest> archivos;
}
