package com.cromosdatabase.modelo.mappers;

import com.cromosdatabase.modelo.dtos.cromo.CromoDetalleResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoResumenResponse;
import com.cromosdatabase.modelo.entidades.Cromo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper de conversión entre la entidad Cromo
 * y sus DTOs de salida.
 */
@Mapper(componentModel = "spring")
public interface CromoMapper {

    /**
     * Convierte una entidad Cromo en un DTO
     * de respuesta resumida.
     *
     * @param cromo entidad cromo
     * @return DTO resumido del cromo
     */
    @Mapping(source = "idCromo", target = "id")
    CromoResumenResponse toResumenResponse(Cromo cromo);

    /**
     * Convierte una lista de entidades Cromo en una lista
     * de DTOs de respuesta resumida.
     *
     * @param cromos lista de cromos
     * @return lista de DTOs resumidos
     */
    List<CromoResumenResponse> toResumenResponseList(List<Cromo> cromos);

    /**
     * Convierte una entidad Cromo en un DTO
     * de respuesta detallada.
     *
     * @param cromo entidad cromo
     * @return DTO detallado del cromo
     */
    @Mapping(source = "idCromo", target = "id")
    @Mapping(source = "coleccion.idColeccion", target = "idColeccion")
    @Mapping(source = "coleccion.nombre", target = "nombreColeccion")
    CromoDetalleResponse toDetalleResponse(Cromo cromo);

    /**
     * Convierte una lista de entidades Cromo en una lista
     * de DTOs de respuesta detallada.
     *
     * @param cromos lista de cromos
     * @return lista de DTOs detallados
     */
    List<CromoDetalleResponse> toDetalleResponseList(List<Cromo> cromos);
}