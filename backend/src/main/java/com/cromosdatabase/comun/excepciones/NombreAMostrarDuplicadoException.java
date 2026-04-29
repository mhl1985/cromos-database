package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta registrar un usuario
 * con un nombre a mostrar que ya existe en el sistema.
 */
public class NombreAMostrarDuplicadoException extends RuntimeException {

    /**
     * Constructor con mensaje de error.
     *
     * @param message mensaje descriptivo del error
     */
    public NombreAMostrarDuplicadoException(String message) {
        super(message);
    }
}
