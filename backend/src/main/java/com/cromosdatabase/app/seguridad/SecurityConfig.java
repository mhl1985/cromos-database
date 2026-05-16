package com.cromosdatabase.app.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración principal de seguridad de la aplicación.
 *
 * Define las reglas de acceso a los endpoints y los beans necesarios
 * para la autenticación con Spring Security.
 */
@Configuration
public class SecurityConfig {

    /**
     * Filtro JWT de autenticación.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param jwtAuthenticationFilter filtro JWT de autenticación
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Define la cadena principal de filtros de seguridad.
     *
     * @param http objeto de configuración de seguridad HTTP
     * @return cadena de filtros de seguridad
     * @throws Exception si ocurre algún error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Desactiva CSRF (API REST)
        http.csrf(csrf -> csrf.disable());

        // Habilita CORS para permitir llamadas desde el frontend
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // La aplicación no usará sesión de servidor
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Configuración de accesos
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/registro").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/colecciones/**").permitAll()
                .requestMatchers("/editoriales/**").permitAll()
                .requestMatchers("/categorias/**").permitAll()
                .requestMatchers("/subcategorias/**").permitAll()
                .requestMatchers("/cromos/**").permitAll()
                .requestMatchers("/paginas/**").permitAll()
                .anyRequest().authenticated()
        );

        // Añade el filtro JWT antes del filtro estándar de usuario/contraseña
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        SecurityFilterChain securityFilterChain = http.build();

        return securityFilterChain;
    }

    /**
     * Define el codificador de contraseñas de la aplicación.
     *
     * Se utiliza BCrypt para comparar contraseñas en login
     * y para futuras operaciones de alta o cambio de contraseña.
     *
     * @return codificador de contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder;
    }

    /**
     * Expone el AuthenticationManager de Spring Security como bean.
     *
     * Este bean será utilizado en el servicio de autenticación
     * para validar las credenciales del usuario.
     *
     * @param authenticationConfiguration configuración interna de autenticación
     * @return gestor de autenticación
     * @throws Exception si ocurre algún error al obtenerlo
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        AuthenticationManager authenticationManager =
                authenticationConfiguration.getAuthenticationManager();

        return authenticationManager;
    }

    /**
     * Define la configuración CORS global de la aplicación.
     *
     * Permite que el frontend pueda realizar peticiones HTTP
     * al backend desde otros orígenes (dominio, puerto o protocolo),
     * por ejemplo durante el desarrollo en local.
     *
     * Se configuran:
     * - Orígenes permitidos
     * - Métodos HTTP permitidos
     * - Cabeceras permitidas
     * - Aplicación de la configuración a todas las rutas
     *
     * @return fuente de configuración CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        // Configuración CORS principal
        CorsConfiguration configuration = new CorsConfiguration();

        /**
         * Configuración de orígenes permitidos para peticiones CORS.
         * Se habilitan distintos hosts y puertos habituales de desarrollo local
         * para permitir que el frontend pueda comunicarse con el backend
         * desde diferentes herramientas y entornos de ejecución.
         *
         * - localhost:5500
         *      Usado normalmente por Live Server de VSCode.
         *
         * - localhost / localhost:80
         *      Acceso local mediante puerto HTTP estándar.
         *
         * - 127.0.0.1:5500
         *      Variante usando IP loopback en lugar de localhost.
         *
         * - localhost:63342
         *      Puerto utilizado por el servidor embebido de IntelliJ IDEA.
         *
         * Esto evita bloqueos CORS durante el desarrollo local del frontend.
         */
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5500",
                "http://localhost",
                "http://localhost:80",
                "http://127.0.0.1:5500",
                "http://localhost:63342"
        ));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        // Cabeceras permitidas en las peticiones
        configuration.setAllowedHeaders(List.of("*"));

        // Indica si se permiten cookies o credenciales
        configuration.setAllowCredentials(false);

        // Fuente de configuración basada en rutas URL
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // Aplica esta configuración a todos los endpoints
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
