package com.cromosdatabase.app.seguridad;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro encargado de procesar el token JWT en cada petición HTTP.
 *
 * Si la petición incluye una cabecera Authorization con un token válido,
 * el usuario se carga desde base de datos y se registra como autenticado
 * en el contexto de seguridad de Spring.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Nombre de la cabecera HTTP que contiene el token.
     */
    private static final String CABECERA_AUTHORIZATION = "Authorization";

    /**
     * Tipo estándar de autenticación Bearer.
     */
    private static final String TIPO_BEARER = "Bearer";

    /**
     * Prefijo completo "Bearer " usado en la cabecera Authorization.
     */
    private static final String PREFIJO_BEARER = TIPO_BEARER + " ";

    /**
     * Servicio encargado de generar, leer y validar tokens JWT.
     */
    private final JwtService jwtService;

    /**
     * Servicio encargado de cargar usuarios para Spring Security.
     */
    private final UsuarioAuthDetailsService usuarioAuthDetailsService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param jwtService servicio JWT
     * @param usuarioAuthDetailsService servicio de carga de usuarios
     */
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UsuarioAuthDetailsService usuarioAuthDetailsService) {

        this.jwtService = jwtService;
        this.usuarioAuthDetailsService = usuarioAuthDetailsService;
    }

    /**
     * Procesa cada petición HTTP para comprobar si contiene un token JWT válido.
     *
     * Si el token existe, se extrae el email, se carga el usuario desde base de
     * datos y, si el token es correcto, se registra al usuario como autenticado
     * en el contexto de seguridad de Spring.
     *
     * @param request petición HTTP entrante
     * @param response respuesta HTTP saliente
     * @param filterChain cadena de filtros de Spring Security
     * @throws ServletException si ocurre un error del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Lee la cabecera Authorization de la petición
        String cabeceraAuthorization = request.getHeader(CABECERA_AUTHORIZATION);

        // Si no hay cabecera o no empieza por "Bearer ", se continúa sin autenticar
        if (cabeceraAuthorization == null || !cabeceraAuthorization.startsWith(PREFIJO_BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrae el token eliminando el prefijo "Bearer "
        String token = cabeceraAuthorization.substring(PREFIJO_BEARER.length());

        // Obtiene el email almacenado dentro del token
        String email = jwtService.extraerEmail(token);

        // Solo continúa si hay email y aún no hay autenticación en el contexto
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carga el usuario desde base de datos
            UsuarioAuth usuarioAuth =
                    (UsuarioAuth) usuarioAuthDetailsService.loadUserByUsername(email);

            // Comprueba si el token es válido para ese usuario
            boolean tokenValido = jwtService.esTokenValido(token, usuarioAuth);

            // Si el token es válido, se autentica al usuario en el contexto
            if (tokenValido) {

                // Crea el objeto de autenticación para Spring Security
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                usuarioAuth,
                                null,
                                usuarioAuth.getAuthorities()
                        );

                // Añade detalles de la petición actual
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Guarda la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
