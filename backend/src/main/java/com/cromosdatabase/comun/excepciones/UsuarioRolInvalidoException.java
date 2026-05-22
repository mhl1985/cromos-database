package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando la combinación de roles
 * asignada a un usuario no es válida.
 */
public class UsuarioRolInvalidoException extends RuntimeException {

    public UsuarioRolInvalidoException(String message) {
        super(message);
    }
}