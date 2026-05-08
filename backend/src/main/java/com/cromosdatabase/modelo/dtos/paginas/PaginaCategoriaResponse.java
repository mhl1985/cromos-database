package com.cromosdatabase.modelo.dtos.paginas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta para cargar la página de una categoría.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginaCategoriaResponse {

    /**
     * Identificador único de la categoría.
     */
    private Integer idCategoria;

    /**
     * Nombre de la categoría.
     */
    private String nombreCategoria;

    /**
     * Descripción de la categoría.
     */
    private String descripcionCategoria;

    /**
     * Subcategorías asociadas a la categoría.
     *
     * Cada subcategoría incluye sus colecciones asociadas.
     */
    private List<PaginaCategoriaSubcategoriaResponse> subcategorias;
}