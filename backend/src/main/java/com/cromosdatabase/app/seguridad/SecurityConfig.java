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
}
