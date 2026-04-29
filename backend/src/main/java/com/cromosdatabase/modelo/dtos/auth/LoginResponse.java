package com.cromosdatabase.modelo.dtos.auth;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de salida para la respuesta de autenticación.
 *
 * Contiene la información que se devuelve al cliente tras un login correcto:
 * - Token JWT para autenticación en siguientes peticiones
 * - Datos básicos del usuario autenticado
 * - Lista de roles del usuario
 */
@Getter
@AllArgsConstructor
public class LoginResponse {

    /**
     * Token JWT generado tras la autenticación.
     */
    private String token;

    /**
     * Tipo de token. (por ejemplo: Bearer).
     */
    private String type;

    /**
     * Identificador del usuario autenticado.
     */
    private Integer idUsuario;

    /**
     * Email del usuario autenticado.
     */
    private String email;

    /**
     * Nombre visible del usuario.
     */
    private String nombreMostrar;

    /**
     * Lista de roles del usuario (por ejemplo: ROLE_ADMIN, ROLE_USER).
     */
    private List<String> roles;
}
