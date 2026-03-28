package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la tabla intermedia "usuarios_roles".
 * Relaciona usuarios con roles mediante una clave primaria compuesta.
 */
@Entity
@Table(name = "usuarios_roles")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRol {

    /**
     * Clave primaria compuesta.
     */
    @EmbeddedId
    @EqualsAndHashCode.Include
    private UsuarioRolId id;

    /**
     * Usuario asociado.
     *
     * - @MapsId("idUsuario") indica que este campo usa la parte idUsuario
     * de la clave primaria compuesta.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", nullable = false)
    @ToString.Exclude
    private Usuario usuario;

    /**
     * Rol asociado.
     *
     * - @MapsId("idRol") indica que este campo usa la parte idRol
     * de la clave primaria compuesta.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRol")
    @JoinColumn(name = "id_rol", nullable = false)
    @ToString.Exclude
    private Rol rol;
}
