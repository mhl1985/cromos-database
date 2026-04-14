package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a un cromo
 * que no existe en la base de datos.
 */
public class CromoNoEncontradoException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public CromoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}