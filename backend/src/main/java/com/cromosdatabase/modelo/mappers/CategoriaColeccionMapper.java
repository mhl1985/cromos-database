package com.cromosdatabase.modelo.mappers;

import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.CategoriaColeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper de conversión entre la entidad CategoriaColeccion
 * y sus DTOs de salida.
 */
@Mapper(componentModel = "spring")
public interface CategoriaColeccionMapper {

    /**
     * Convierte una entidad CategoriaColeccion en un DTO
     * de respuesta resumida.
     *
     * @param categoriaColeccion entidad de categoría
     * @return DTO resumido de la categoría
     */
    @Mapping(source = "idCategoria", target = "id")
    CategoriaColeccionResumenResponse toResumenResponse(
            CategoriaColeccion categoriaColeccion
    );

    /**
     * Convierte una lista de entidades CategoriaColeccion en una lista
     * de DTOs de respuesta resumida.
     *
     * @param categoriasColeccion lista de categorías
     * @return lista de DTOs resumidos
     */
    List<CategoriaColeccionResumenResponse> toResumenResponseList(
            List<CategoriaColeccion> categoriasColeccion
    );
}
