package com.cromosdatabase.modelo.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta con los datos públicos de un usuario
 * que tiene un cromo disponible para intercambio.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPoseedorCromoResponse {

    /**
     * Identificador único del usuario.
     */
    private Integer idUsuario;

    /**
     * Nombre visible del usuario.
     */
    private String nombreMostrar;

    /**
     * Email del usuario.
     */
    private String email;
}