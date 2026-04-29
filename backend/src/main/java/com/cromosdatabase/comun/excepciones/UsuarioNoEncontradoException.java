package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando no se encuentra un usuario
 * en la base de datos.
 */
public class UsuarioNoEncontradoException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}