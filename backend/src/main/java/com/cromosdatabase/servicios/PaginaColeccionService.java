package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.paginas.PaginaColeccionResponse;

/**
 * Servicio para cargar la página de una colección.
 */
public interface PaginaColeccionService {

    /**
     * Carga todos los datos necesarios para pintar la página
     * de una colección.
     *
     * @param idColeccion id de la colección
     * @return DTO con los datos de la página de colección
     */
    PaginaColeccionResponse cargarPaginaColeccion(Integer idColeccion);
}