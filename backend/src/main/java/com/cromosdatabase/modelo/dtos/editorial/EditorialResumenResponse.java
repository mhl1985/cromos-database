package com.cromosdatabase.modelo.dtos.editorial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta resumida para una editorial.
 *
 * Se utiliza cuando no es necesario devolver toda la información
 * de la editorial, sino solo sus datos básicos identificativos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditorialResumenResponse {

    /**
     * Identificador único de la editorial.
     */
    private Integer id;

    /**
     * Nombre de la editorial.
     */
    private String nombre;
}
