package com.cromosdatabase.modelo.dtos.subcategoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta detallada para una subcategoría de colección.
 *
 * Se utiliza cuando es necesario devolver, además de los datos básicos
 * identificativos, la descripción de la subcategoría y la categoría
 * a la que pertenece.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoriaColeccionDetalleResponse {

    /**
     * Identificador único de la subcategoría.
     */
    private Integer id;

    /**
     * Identificador de la categoría a la que pertenece la subcategoría.
     */
    private Integer idCategoria;

    /**
     * Nombre de la subcategoría.
     */
    private String nombre;

    /**
     * Descripción de la subcategoría.
     */
    private String descripcion;
}