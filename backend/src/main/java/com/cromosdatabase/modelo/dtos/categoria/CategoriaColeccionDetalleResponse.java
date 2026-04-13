package com.cromosdatabase.modelo.dtos.categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta detallada para una categoría de colección.
 *
 * Se utiliza cuando es necesario devolver, además de los datos básicos
 * identificativos, la descripción de la categoría.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaColeccionDetalleResponse {

    /**
     * Identificador único de la categoría.
     */
    private Integer id;

    /**
     * Nombre de la categoría.
     */
    private String nombre;

    /**
     * Descripción de la categoría.
     */
    private String descripcion;
}