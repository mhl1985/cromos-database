package com.cromosdatabase.modelo.dtos.comun;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO genérico para devolver respuestas de error de la API.
 *
 * Se utilizará como body de respuesta en los distintos manejadores
 * globales de excepciones.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorGenericoResponse {

    /**
     * Código HTTP de la respuesta.
     */
    private int status;

    /**
     * Texto descriptivo breve del tipo de error.
     */
    private String error;

    /**
     * Mensaje detallado del error.
     */
    private String mensaje;

}
