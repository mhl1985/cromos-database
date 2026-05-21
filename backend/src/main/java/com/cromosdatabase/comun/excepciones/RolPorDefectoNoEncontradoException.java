package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando no existe en base de datos
 * el rol por defecto necesario para registrar usuarios.
 */
public class RolPorDefectoNoEncontradoException extends RuntimeException {

    /**
     * Constructor con mensaje de error.
     *
     * @param message mensaje descriptivo del error
     */
    public RolPorDefectoNoEncontradoException(String message) {
        super(message);
    }
}
