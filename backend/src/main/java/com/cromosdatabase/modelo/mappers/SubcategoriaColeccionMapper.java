package com.cromosdatabase.modelo.mappers;

import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.SubcategoriaColeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper de conversión entre la entidad SubcategoriaColeccion
 * y sus DTOs de salida.
 */
@Mapper(componentModel = "spring")
public interface SubcategoriaColeccionMapper {

    /**
     * Convierte una entidad SubcategoriaColeccion en un DTO
     * de respuesta resumida.
     *
     * @param subcategoriaColeccion entidad de subcategoría
     * @return DTO resumido de la subcategoría
     */
    @Mapping(source = "idSubcategoria", target = "id")
    SubcategoriaColeccionResumenResponse toResumenResponse(
            SubcategoriaColeccion subcategoriaColeccion
    );

    /**
     * Convierte una lista de entidades SubcategoriaColeccion en una lista
     * de DTOs de respuesta resumida.
     *
     * @param subcategoriasColeccion lista de subcategorías
     * @return lista de DTOs resumidos
     */
    List<SubcategoriaColeccionResumenResponse> toResumenResponseList(
            List<SubcategoriaColeccion> subcategoriasColeccion
    );

    /**
     * Convierte una entidad SubcategoriaColeccion en un DTO
     * de respuesta detallada.
     *
     * @param subcategoriaColeccion entidad de subcategoría
     * @return DTO detallado de la subcategoría
     */
    @Mapping(source = "idSubcategoria", target = "id")
    SubcategoriaColeccionDetalleResponse toDetalleResponse(
            SubcategoriaColeccion subcategoriaColeccion
    );
}