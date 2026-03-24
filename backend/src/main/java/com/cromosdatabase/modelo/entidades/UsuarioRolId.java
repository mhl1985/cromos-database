package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * Clave primaria compuesta de la tabla "usuarios_roles".
 */
@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRolId implements Serializable {

    /**
     * ID del usuario.
     */
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /**
     * ID del rol.
     */
    @Column(name = "id_rol")
    private Integer idRol;
}
