package com.cromosdatabase.modelo.dtos.editorial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta detallada para una editorial.
 *
 * Se utiliza cuando es necesario devolver, además de los datos básicos
 * identificativos, la descripción de la editorial.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditorialDetalleResponse {

    /**
     * Identificador único de la editorial.
     */
    private Integer id;

    /**
     * Nombre de la editorial.
     */
    private String nombre;

    /**
     * Descripción de la editorial.
     */
    private String descripcion;
}
