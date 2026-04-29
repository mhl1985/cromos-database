package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando se intenta asociar al usuario autenticado
 * una colección que ya tiene añadida.
 */
public class UsuarioColeccionDuplicadaException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public UsuarioColeccionDuplicadaException(String mensaje) {
        super(mensaje);
    }
}