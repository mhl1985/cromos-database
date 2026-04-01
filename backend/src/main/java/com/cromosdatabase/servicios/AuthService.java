package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.auth.AuthResponse;
import com.cromosdatabase.modelo.dtos.auth.LoginRequest;

/**
 * Servicio encargado de gestionar la autenticación de usuarios.
 *
 * Define las operaciones relacionadas con el inicio de sesión en la aplicación.
 */
public interface AuthService {

    /**
     * Autentica a un usuario a partir de sus credenciales de acceso.
     *
     * Si las credenciales son correctas, se generará una respuesta con el
     * token JWT y los datos básicos del usuario autenticado.
     *
     * @param loginRequest datos de entrada del login
     * @return respuesta de autenticación con el token y datos del usuario
     */
    AuthResponse login(LoginRequest loginRequest);
}
