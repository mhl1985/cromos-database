package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la tabla intermedia "usuarios_cromos".
 * Relaciona usuarios con cromos y almacena cantidades y observaciones.
 */
@Entity
@Table(name = "usuarios_cromos")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCromo {

    /**
     * Clave primaria compuesta.
     */
    @EmbeddedId
    @EqualsAndHashCode.Include
    private UsuarioCromoId id;

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
     * Cromo asociado.
     *
     * - @MapsId("idCromo") indica que este campo usa la parte idCromo
     * de la clave primaria compuesta.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCromo")
    @JoinColumn(name = "id_cromo", nullable = false)
    @ToString.Exclude
    private Cromo cromo;

    /**
     * Cantidad total de unidades de este cromo que tiene el usuario.
     * No puede ser negativa.
     */
    @Column(name = "cantidad_total", nullable = false)
    private Integer cantidadTotal;

    /**
     * Cantidad de unidades disponibles de este cromo que tiene el usuario para intercambio.
     * No puede ser negativa ni mayor que cantidadTotal.
     */
    @Column(name = "cantidad_intercambiable", nullable = false)
    private Integer cantidadIntercambiable;

    /**
     * Observaciones del usuario sobre este cromo.
     */
    @Column(name = "observaciones", length = 255)
    private String observaciones;
}
