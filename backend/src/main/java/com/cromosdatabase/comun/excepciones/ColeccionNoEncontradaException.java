package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a una colección
 * que no existe en la base de datos.
 */
public class ColeccionNoEncontradaException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public ColeccionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
