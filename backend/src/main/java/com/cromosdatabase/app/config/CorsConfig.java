package com.cromosdatabase.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración CORS de la aplicación.
 */
@Configuration
public class CorsConfig {

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

        /*
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