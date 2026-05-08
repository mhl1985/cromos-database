package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.paginas.PaginaCategoriaResponse;

/**
 * Servicio para cargar la página de una categoría.
 */
public interface PaginaCategoriaService {

    /**
     * Carga todos los datos necesarios para pintar la página
     * de una categoría.
     *
     * @param idCategoria id de la categoría
     * @return DTO con los datos de la página de categoría
     */
    PaginaCategoriaResponse cargarPaginaCategoria(Integer idCategoria);
}