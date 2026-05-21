package com.cromosdatabase.modelo.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta con los datos de un rol asociado
 * a un usuario en la zona de administración.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUsuarioRolResponse {

    /**
     * Identificador único del rol.
     */
    private Integer id;

    /**
     * Nombre del rol.
     */
    private String nombre;
}