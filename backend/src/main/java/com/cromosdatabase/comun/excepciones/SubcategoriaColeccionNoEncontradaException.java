package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a una subcategoría de colección
 * que no existe en la base de datos.
 */
public class SubcategoriaColeccionNoEncontradaException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public SubcategoriaColeccionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}