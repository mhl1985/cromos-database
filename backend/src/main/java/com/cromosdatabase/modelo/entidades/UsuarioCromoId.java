package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Clave primaria compuesta de la tabla "usuarios_cromos".
 */
@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCromoId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID del usuario.
     */
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /**
     * ID del cromo.
     */
    @Column(name = "id_cromo")
    private Integer idCromo;
}
