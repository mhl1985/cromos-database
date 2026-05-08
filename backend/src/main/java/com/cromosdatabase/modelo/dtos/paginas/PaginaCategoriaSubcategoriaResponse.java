package com.cromosdatabase.modelo.dtos.paginas;

import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta para una subcategoría dentro
 * de la página de categoría.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginaCategoriaSubcategoriaResponse {

    /**
     * Identificador único de la subcategoría.
     */
    private Integer idSubcategoria;

    /**
     * Nombre de la subcategoría.
     */
    private String nombreSubcategoria;

    /**
     * Descripción de la subcategoría.
     */
    private String descripcionSubcategoria;

    /**
     * Colecciones asociadas a esta subcategoría.
     *
     * Si no existen colecciones, se devuelve lista vacía.
     */
    private List<ColeccionDetalleResponse> coleccionesSubcategoria;
}