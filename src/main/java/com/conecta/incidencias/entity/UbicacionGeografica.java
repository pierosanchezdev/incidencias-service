package com.conecta.incidencias.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ubicaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionGeografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 100)
    private String pais;

    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 100)
    private String departamento;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 100)
    private String provincia;

    @NotBlank(message = "El distrito es obligatorio")
    @Size(max = 100)
    private String distrito;

    @NotBlank(message = "El código ubigeo es obligatorio")
    @Pattern(regexp = "\\d{6}", message = "El código ubigeo debe tener 6 dígitos numéricos")
    @Column(nullable = false)
    private String ubigeo;

    @Size(max = 10)
    private String codigoPostal;

    @NotBlank(message = "El nombre de la localidad es obligatorio")
    @Size(max = 255)
    private String nombreLocalidad;

    @DecimalMin(value = "-90.0", message = "Latitud mínima permitida: -90.0")
    @DecimalMax(value = "90.0", message = "Latitud máxima permitida: 90.0")
    private Double latitud;

    @DecimalMin(value = "-180.0", message = "Longitud mínima permitida: -180.0")
    @DecimalMax(value = "180.0", message = "Longitud máxima permitida: 180.0")
    private Double longitud;
}
