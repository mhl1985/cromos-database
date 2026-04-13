package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a una categoría de colección
 * que no existe en la base de datos.
 */
public class CategoriaColeccionNoEncontradaException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public CategoriaColeccionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}