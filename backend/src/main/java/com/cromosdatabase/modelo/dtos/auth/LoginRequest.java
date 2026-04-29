package com.cromosdatabase.modelo.dtos.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de entrada para la petición de inicio de sesión.
 *
 * Esta clase representa los datos que el cliente debe enviar al backend
 * para autenticarse en la aplicación.
 *
 * Contiene el email del usuario y la contraseña en texto plano introducida
 * en el formulario de login.
 */
@Getter
@Setter
public class LoginRequest {

    /**
     * Email del usuario que intenta autenticarse.
     */
    private String email;

    /**
     * Contraseña en texto plano introducida por el usuario.
     */
    private String contrasena;
}