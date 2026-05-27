package com.cromosdatabase.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI para la documentación de la API REST.
 *
 * Define la información general que se muestra en la interfaz
 * Swagger de la aplicación.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Definimos la configuración principal de OpenAPI.
     *
     * @return configuración OpenAPI personalizada
     */
    @Bean
    public OpenAPI customOpenAPI() {

        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("Cromos Database API")
                        .version("1.0")
                        .description("API REST de gestión de colecciones y cromos." +
                                "<br><br>José Miguel Martín  y  Miguel Herrero"));

        return openAPI;
    }
}