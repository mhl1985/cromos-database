package com.cromosdatabase.modelo.dtos.subcategoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta resumida para una subcategoría de colección.
 *
 * Se utiliza cuando solo es necesario devolver los datos básicos
 * identificativos de la subcategoría.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoriaColeccionResumenResponse {

    /**
     * Identificador único de la subcategoría.
     */
    private Integer id;

    /**
     * Nombre de la subcategoría.
     */
    private String nombre;
}