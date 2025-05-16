package com.conecta.incidencias.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionRequest {
    private String pais;
    private String departamento;
    private String provincia;
    private String distrito;
    private String ubigeo;
    private String codigoPostal;
    private String nombreLocalidad;
    private BigDecimal latitud;
    private BigDecimal longitud;
}
