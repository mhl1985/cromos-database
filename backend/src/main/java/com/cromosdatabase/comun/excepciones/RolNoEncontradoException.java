package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta utilizar
 * un rol que no existe en el sistema.
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
