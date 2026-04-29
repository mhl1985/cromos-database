package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Clave primaria compuesta de la tabla "usuarios_colecciones".
 */
@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioColeccionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID del usuario.
     */
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /**
     * ID de la colección.
     */
    @Column(name = "id_coleccion")
    private Integer idColeccion;
}
