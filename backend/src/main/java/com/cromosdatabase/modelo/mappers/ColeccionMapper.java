package com.cromosdatabase.modelo.mappers;

import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.Coleccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper de conversión entre la entidad Coleccion
 * y sus DTOs de salida.
 * Con "uses=..." reutilizamos mappers ya creados de otras
 * entidades que van dentro de colección.
 */
@Mapper(
        componentModel = "spring",
        uses = {
                EditorialMapper.class,
                CategoriaColeccionMapper.class,
                SubcategoriaColeccionMapper.class
        }
)
public interface ColeccionMapper {

    /**
     * Convierte una entidad Coleccion en un DTO
     * de respuesta resumida.
     *
     * @param coleccion entidad colección
     * @return DTO resumido de la colección
     */
    @Mapping(source = "idColeccion", target = "id")
    ColeccionResumenResponse toResumenResponse(Coleccion coleccion);

    /**
     * Convierte una lista de entidades Coleccion en una lista
     * de DTOs de respuesta resumida.
     *
     * @param colecciones lista de colecciones
     * @return lista de DTOs resumidos
     */
    List<ColeccionResumenResponse> toResumenResponseList(List<Coleccion> colecciones);

    /**
     * Convierte una entidad Coleccion en un DTO
     * de respuesta detallada.
     *
     * @param coleccion entidad colección
     * @return DTO detallado de la colección
     */
    @Mapping(source = "idColeccion", target = "id")
    ColeccionDetalleResponse toDetalleResponse(Coleccion coleccion);

    /**
     * Convierte una lista de entidades Coleccion en una lista
     * de DTOs de respuesta detallada.
     *
     * @param colecciones lista de colecciones
     * @return lista de DTOs detallados
     */
    List<ColeccionDetalleResponse> toDetalleResponseList(List<Coleccion> colecciones);
}