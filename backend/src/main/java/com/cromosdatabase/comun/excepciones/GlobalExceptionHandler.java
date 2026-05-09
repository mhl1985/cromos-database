package com.cromosdatabase.comun.excepciones;

import com.cromosdatabase.modelo.dtos.comun.ErrorGenericoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Manejador global de excepciones.
 * Centraliza el tratamiento de errores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestiona los errores de validación producidos por @Valid
     * en los DTOs de entrada.
     * Ejemplo: Cuando uno o varios campos
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
     * Gestiona errores de autenticación producidos por credenciales incorrectas.
     * Ejemplo: Cuando el email existe pero la contraseña informada no es
     * válida, o cuando se intenta acceder con credenciales erróneas.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 401 con mensaje genérico de autenticación
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorGenericoResponse> handleBadCredentialsException(
            BadCredentialsException ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Email o contraseña incorrectos."
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Gestiona otros errores de autenticación de Spring Security.
     * Respaldo para excepciones de autenticación distintas de
     * BadCredentialsException.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 401 con mensaje genérico de autenticación
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorGenericoResponse> handleAuthenticationException(
            AuthenticationException ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "No se ha podido autenticar al usuario."
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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
     * Gestiona los casos en los que se solicita un recurso que no existe
     * o no está disponible para el usuario autenticado.
     *
     * @param ex excepción de recurso no encontrado capturada
     * @return respuesta HTTP 404 con el mensaje del error
     */
    @ExceptionHandler({
            ColeccionNoEncontradaException.class,
            EditorialNoEncontradaException.class,
            CategoriaColeccionNoEncontradaException.class,
            SubcategoriaColeccionNoEncontradaException.class,
            CromoNoEncontradoException.class,
            UsuarioNoEncontradoException.class,
            UsuarioColeccionNoEncontradaException.class
    })
    public ResponseEntity<ErrorGenericoResponse> handleRecursoNoEncontradoException(RuntimeException ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Gestiona el caso en el que se intenta asociar al usuario autenticado
     * una colección que ya tiene añadida.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 409 con el mensaje del error
     */
    @ExceptionHandler(UsuarioColeccionDuplicadaException.class)
    public ResponseEntity<ErrorGenericoResponse> handleUsuarioColeccionDuplicadaException(
            UsuarioColeccionDuplicadaException ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Gestiona los errores de validación de negocio producidos
     * al editar los cromos de una colección del usuario.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 400 con el mensaje del error
     */
    @ExceptionHandler(UsuarioColeccionCromoEdicionInvalidaException.class)
    public ResponseEntity<ErrorGenericoResponse> handleUsuarioColeccionCromoEdicionInvalidaException(
            UsuarioColeccionCromoEdicionInvalidaException ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Gestiona errores de tipo en parámetros de entrada de la petición.
     * Ejemplo: Cuando un parámetro que debería ser numérico
     * recibe un valor no válido, como idCategoria=abc.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 400 con detalle del error
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorGenericoResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {

        String nombreParametro = ex.getName();
        String valorRecibido = ex.getValue() != null ? ex.getValue().toString() : "null";

        String mensajeError = "El parámetro '" + nombreParametro
                + "' tiene un valor no válido: '" + valorRecibido + "'.";

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                mensajeError
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Gestiona peticiones a URLs no válidas o incompletas.
     * Ejemplo: Cuando se invoca un endpoint que requiere
     * un id en el path pero este no se informa.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 400 con mensaje genérico de URL no válida
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorGenericoResponse> handleNoResourceFoundException(
            NoResourceFoundException ex) {

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "La URL solicitada no es válida. " +
                        "Revise que se hayan informado todos los parámetros obligatorios."
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Gestiona el caso en el que falta una variable obligatoria del path.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 400 con detalle del parámetro ausente
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorGenericoResponse> handleMissingPathVariableException(
            MissingPathVariableException ex) {

        String mensajeError = "Falta un parámetro obligatorio en la URL: "
                + ex.getVariableName() + ".";

        ErrorGenericoResponse response = new ErrorGenericoResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                mensajeError
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Gestiona cualquier error no controlado de forma específica.
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