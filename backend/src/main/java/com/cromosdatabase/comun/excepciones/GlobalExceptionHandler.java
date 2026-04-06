package com.cromosdatabase.comun.excepciones;

import com.cromosdatabase.modelo.dtos.comun.ErrorGenericoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones.
 *
 * Centraliza el tratamiento de errores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestiona los errores de validación producidos por @Valid
     * en los DTOs de entrada.
     *
     * Este tipo de excepción se lanza cuando uno o varios campos
     * no cumplen las restricciones definidas con anotaciones
     * como @NotBlank, @Size o @Email.
     *
     * @param ex excepción de validación capturada
     * @return respuesta HTTP 400 con el detalle del error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorGenericoResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        // Obtenemos el primer mensaje de error de validación detectado.
        String mensajeError = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("La petición contiene errores de validación.");

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                mensajeError
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Gestiona el intento de registrar un usuario con
     * un email o un nombre a mostrar ya existentes.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 409 con el mensaje del error
     */
    @ExceptionHandler({
            EmailDuplicadoException.class,
            NombreAMostrarDuplicadoException.class
    })
    public ResponseEntity<ErrorGenericoResponse> handleRegistroDuplicadoException(RuntimeException ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Gestiona el caso en el que no existe el rol por defecto necesario
     * para registrar usuarios.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 500 con el mensaje del error
     */
    @ExceptionHandler(RolNoEncontradoException.class)
    public ResponseEntity<ErrorGenericoResponse> handleRolNoEncontradoException(
            RolNoEncontradoException ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Gestiona cualquier error no controlado de forma específica.
     *
     * Actúa como último nivel de captura para evitar que la API
     * devuelva respuestas no uniformes o exponga información interna.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 500 genérica
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorGenericoResponse> handleException(Exception ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Se ha producido un error interno en el servidor."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}