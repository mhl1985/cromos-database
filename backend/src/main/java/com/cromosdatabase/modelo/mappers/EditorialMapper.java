package com.cromosdatabase.modelo.mappers;

import com.cromosdatabase.modelo.dtos.editorial.EditorialResumenResponse;
import com.cromosdatabase.modelo.entidades.Editorial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper de conversión entre la entidad Editorial
 * y sus DTOs de salida.
 */
@Mapper(componentModel = "spring")
public interface EditorialMapper {

    /**
     * Convierte una entidad Editorial en un DTO de respuesta resumida.
     *
     * @param editorial entidad editorial
     * @return DTO resumido de la editorial
     */
    @Mapping(source = "idEditorial", target = "id")
    EditorialResumenResponse toResumenResponse(Editorial editorial);

    /**
     * Convierte una lista de entidades Editorial en una lista
     * de DTOs de respuesta resumida.
     *
     * @param editoriales lista de editoriales
     * @return lista de DTOs resumidos
     */
    List<EditorialResumenResponse> toResumenResponseList(List<Editorial> editoriales);
}
