package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a una colección
 * que no está asociada al usuario autenticado.
 */
public class UsuarioColeccionNoEncontradaException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public UsuarioColeccionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}