package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando no existe en base de datos
 * el rol por defecto necesario para registrar usuarios.
 */
public class RolNoEncontradoException extends RuntimeException {

    /**
     * Constructor con mensaje de error.
     *
     * @param message mensaje descriptivo del error
     */
    public RolNoEncontradoException(String message) {
        super(message);
    }
}
