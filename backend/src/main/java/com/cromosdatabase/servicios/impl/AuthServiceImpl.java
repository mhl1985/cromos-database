package com.cromosdatabase.servicios.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cromosdatabase.app.seguridad.JwtService;
import com.cromosdatabase.app.seguridad.UsuarioAuth;
import com.cromosdatabase.modelo.dtos.auth.LoginResponse;
import com.cromosdatabase.modelo.dtos.auth.LoginRequest;
import com.cromosdatabase.modelo.dtos.auth.PerfilUsuarioAuthResponse;
import com.cromosdatabase.servicios.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de autenticación.
 *
 * Se encarga de:
 * - autenticar al usuario con Spring Security
 * - generar el token JWT
 * - construir la respuesta de login
 * - obtener el perfil del usuario autenticado
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * Gestor de autenticación de Spring Security.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Servicio encargado de generar y validar tokens JWT.
     */
    private final JwtService jwtService;

    /**
     * Autentica a un usuario a partir de sus credenciales de acceso.
     *
     * @param loginRequest datos de entrada del login
     * @return respuesta de autenticación con token y datos del usuario
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        // Extracción de credenciales
        String email = loginRequest.getEmail(); // Email introducido
        String contrasena = loginRequest.getContrasena(); // Contraseña en texto plano

        // Creación del token que Spring Security utilizará para autenticar
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, contrasena);

        // Spring valida usuario y contraseña contra UserDetailsService
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Obtenemos el usuario autenticado
        UsuarioAuth usuarioAuth = (UsuarioAuth) authentication.getPrincipal();

        // Generamos el token JWT a partir del usuario autenticado
        String token = jwtService.generarToken(usuarioAuth);

        String tipo = "Bearer"; // Tipo de token estandarizado

        // Extracción de datos del usuario
        Integer idUsuario = usuarioAuth.getIdUsuario();
        String emailUsuario = usuarioAuth.getUsername();
        String nombreMostrar = usuarioAuth.getNombreMostrar();

        // Convertimos authorities de Spring en lista de Strings
        List<String> roles = obtenerNombresRoles(usuarioAuth.getAuthorities());

        // Construimos y devolvemos respuesta
        LoginResponse loginResponse = new LoginResponse(
                token,
                tipo,
                idUsuario,
                emailUsuario,
                nombreMostrar,
                roles
        );

        return loginResponse;
    }

    /**
     * Obtiene los datos del perfil del usuario autenticado actual.
     *
     * Reutiliza directamente el principal autenticado que Spring Security
     * ha guardado en el contexto de seguridad durante la petición.
     *
     * @return datos del perfil del usuario autenticado actual
     */
    @Override
    public PerfilUsuarioAuthResponse obtenerPerfilUsuarioAutenticado() {

        // Obtenemos la autenticación actual del contexto de seguridad.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Obtenemos el usuario autenticado almacenado por Spring Security.
        UsuarioAuth usuarioAuth = (UsuarioAuth) authentication.getPrincipal();

        // Convertimos authorities de Spring en lista de Strings.
        List<String> roles = this.obtenerNombresRoles(usuarioAuth.getAuthorities());

        // Construimos y retornamos DTO de respuesta.
        PerfilUsuarioAuthResponse perfilUsuarioAuthResponse = new PerfilUsuarioAuthResponse();
        perfilUsuarioAuthResponse.setIdUsuario(usuarioAuth.getIdUsuario());
        perfilUsuarioAuthResponse.setEmail(usuarioAuth.getUsername());
        perfilUsuarioAuthResponse.setNombreMostrar(usuarioAuth.getNombreMostrar());
        perfilUsuarioAuthResponse.setRoles(roles);

        return perfilUsuarioAuthResponse;
    }

    /**
     * Convierte las authorities de Spring Security en una lista de nombres de rol.
     *
     * @param authorities authorities del usuario autenticado
     * @return lista con los nombres de los roles
     */
    private List<String> obtenerNombresRoles(Collection<? extends GrantedAuthority> authorities) {

        List<String> roles = new ArrayList<>();

        for (GrantedAuthority authority : authorities) {
            // Obtenemos nombre del rol y lo añadimos a la lista.
            String nombreRol = authority.getAuthority();
            roles.add(nombreRol);
        }

        return roles;
    }
}