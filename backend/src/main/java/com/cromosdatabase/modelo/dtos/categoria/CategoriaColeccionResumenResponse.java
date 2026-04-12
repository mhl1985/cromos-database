package com.cromosdatabase.modelo.dtos.categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta resumida para una categoría de colección.
 *
 * Se utiliza cuando solo es necesario devolver los datos básicos
 * identificativos de la categoría.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaColeccionResumenResponse {

    /**
     * Identificador único de la categoría.
     */
    private Integer id;

    /**
     * Nombre de la categoría.
     */
    private String nombre;
}
