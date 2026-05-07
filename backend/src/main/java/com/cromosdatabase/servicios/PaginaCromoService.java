package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.paginas.PaginaCromoResponse;

/**
 * Servicio para cargar la página de un cromo.
 */
public interface PaginaCromoService {

    /**
     * Carga todos los datos necesarios para pintar la página
     * de un cromo.
     *
     * @param idCromo id del cromo
     * @return DTO con los datos de la página de cromo
     */
    PaginaCromoResponse cargarPaginaCromo(Integer idCromo);
}