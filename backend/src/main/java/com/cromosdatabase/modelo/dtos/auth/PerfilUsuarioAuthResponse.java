package com.cromosdatabase.modelo.dtos.auth;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta con los datos del perfil del usuario autenticado.
 *
 * Se utiliza para devolver la información básica del usuario autenticado actual.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilUsuarioAuthResponse {

    /**
     * Identificador único del usuario.
     */
    private Integer idUsuario;

    /**
     * Email del usuario.
     */
    private String email;

    /**
     * Nombre visible del usuario en la aplicación.
     */
    private String nombreMostrar;

    /**
     * Lista de roles del usuario.
     */
    private List<String> roles;

    /*
     * TODO:
     * Revisar ampliación de este DTO con:
     * - fechaRegistro
     * - colecciones del usuario
     * - cromos del usuario
     * - DTOs anidados de roles en lugar de List<String>
     * - Etc.
     */
}
