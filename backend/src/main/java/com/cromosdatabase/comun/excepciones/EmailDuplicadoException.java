package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta registrar un usuario
 * con un email que ya existe en el sistema.
 */
public class EmailDuplicadoException extends RuntimeException {

    /**
     * Constructor con mensaje de error.
     *
     * @param message mensaje descriptivo del error
     */
    public EmailDuplicadoException(String message) {
        super(message);
    }
}
