package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a una editorial
 * que no existe en la base de datos.
 */
public class EditorialNoEncontradaException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public EditorialNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}