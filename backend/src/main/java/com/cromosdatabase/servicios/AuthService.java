package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.auth.AuthResponse;
import com.cromosdatabase.modelo.dtos.auth.LoginRequest;
import com.cromosdatabase.modelo.dtos.auth.PerfilUsuarioAuthResponse;

/**
 * Servicio encargado de gestionar la autenticación de usuarios.
 *
 * Define las operaciones relacionadas con el inicio de sesión
 * y con la obtención del usuario autenticado actual.
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

    /**
     * Obtiene los datos del perfil del usuario autenticado actual.
     *
     * La obtención se realiza a partir del contexto de seguridad
     * de Spring Security, sin necesidad de realizar login de nuevo.
     *
     * @return datos del perfil del usuario autenticado actual
     */
    PerfilUsuarioAuthResponse obtenerPerfilUsuarioAutenticado();
}