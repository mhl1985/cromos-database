package com.cromosdatabase.modelo.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de salida para la respuesta del registro de usuario.
 *
 * Contiene la información básica del usuario recién creado
 * que se devolverá al cliente tras un registro exitoso.
 * Luego tendrá que hacer login en la aplicación.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioResponse {

    /**
     * Identificador único del usuario en base de datos.
     */
    private Integer id;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Nombre visible del usuario en la aplicación.
     */
    private String nombreMostrar;

}
