package com.cromosdatabase.comun.excepciones;

/**
 * Excepción lanzada cuando los datos enviados para editar
 * los cromos de una colección del usuario no son válidos.
 */
public class UsuarioColeccionCromoEdicionInvalidaException extends RuntimeException {

    /**
     * Construye la excepción con el mensaje de error recibido.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public UsuarioColeccionCromoEdicionInvalidaException(String mensaje) {
        super(mensaje);
    }
}