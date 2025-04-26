package com.conecta.incidencias.mapper;

import com.conecta.incidencias.dto.request.IncidenciaRequest;
import com.conecta.incidencias.dto.response.IncidenciaResponse;
import com.conecta.incidencias.entity.Incidencia;
import com.conecta.incidencias.enums.Estado;
import com.conecta.incidencias.enums.Impacto;
import com.conecta.incidencias.enums.Urgencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ArchivoMapper.class})
public interface IncidenciaMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "categoria", ignore = true),
            @Mapping(target = "usuario", ignore = true),
            @Mapping(target = "ubicacion", ignore = true),
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "fechaRegistro", ignore = true),
            @Mapping(target = "fechaActualizacion", ignore = true),
            @Mapping(target = "archivos", ignore = true)
    })
    Incidencia toEntity(IncidenciaRequest request);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "titulo", source = "titulo"),
            @Mapping(target = "descripcion", source = "descripcion"),
            @Mapping(target = "categoriaId", source = "categoria.id"),
            @Mapping(target = "impacto", source = "impacto"),
            @Mapping(target = "urgencia", source = "urgencia"),
            @Mapping(target = "estado", source = "estado"),
            @Mapping(target = "fechaRegistro", source = "fechaRegistro"),
            @Mapping(target = "fechaActualizacion", source = "fechaActualizacion"),
            @Mapping(target = "usuarioId", source = "usuario.id"),
            @Mapping(target = "ubicacionId", source = "ubicacion.id"),
            @Mapping(target = "archivos", source = "archivos")
    })
    IncidenciaResponse toResponse(Incidencia incidencia);

    default Estado incidenciaEstado(Incidencia incidencia) {
        return incidencia.getEstado();
    }

    default Impacto incidenciaImpacto(Incidencia incidencia) {
        return incidencia.getImpacto();
    }

    default Urgencia incidenciaUrgencia(Incidencia incidencia) {
        return incidencia.getUrgencia();
    }
}
