package com.cromosdatabase.servicios.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.cromosdatabase.app.seguridad.JwtService;
import com.cromosdatabase.app.seguridad.UsuarioAuth;
import com.cromosdatabase.modelo.dtos.auth.AuthResponse;
import com.cromosdatabase.modelo.dtos.auth.LoginRequest;
import com.cromosdatabase.servicios.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de autenticación.
 *
 * Se encarga de autenticar al usuario con Spring Security, generar el token JWT
 * y construir la respuesta de login.
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

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        String email = loginRequest.getEmail(); // Email introducido
        String contrasena = loginRequest.getContrasena(); // Contraseña en texto plano

        // Intento de autenticación con Spring Security
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, contrasena);

        // Spring valida usuario y contraseña
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Usuario autenticado devuelto por Spring
        UsuarioAuth usuarioAuth = (UsuarioAuth) authentication.getPrincipal();

        // Generación del JWT
        String token = jwtService.generarToken(usuarioAuth);

        // Tipo de token estandarizado
        String tipo = "Bearer";

        Integer idUsuario = usuarioAuth.getIdUsuario(); // ID del usuario
        String emailUsuario = usuarioAuth.getUsername(); // Email
        String nombreMostrar = usuarioAuth.getNombreMostrar(); // Nombre visible

        // Conversión de roles a lista de texto
        List<String> roles = obtenerNombresRoles(usuarioAuth.getAuthorities());

        AuthResponse authResponse = new AuthResponse(
                token,
                tipo,
                idUsuario,
                emailUsuario,
                nombreMostrar,
                roles
        );

        return authResponse;
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
            String nombreRol = authority.getAuthority();
            roles.add(nombreRol);
        }

        return roles;
    }
}