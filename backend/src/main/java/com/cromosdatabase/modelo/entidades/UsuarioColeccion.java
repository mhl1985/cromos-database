package com.cromosdatabase.modelo.entidades;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa la tabla intermedia "usuarios_colecciones".
 * Relaciona usuarios con colecciones y almacena la fecha en que el usuario
 * añadió la colección a la aplicación.
 */
@Entity
@Table(name = "usuarios_colecciones")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioColeccion {

    /**
     * Clave primaria compuesta.
     */
    @EmbeddedId
    @EqualsAndHashCode.Include
    private UsuarioColeccionId id;

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
     * Colección asociada.
     *
     * - @MapsId("idColeccion") indica que este campo usa la parte idColeccion
     * de la clave primaria compuesta.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idColeccion")
    @JoinColumn(name = "id_coleccion", nullable = false)
    @ToString.Exclude
    private Coleccion coleccion;

    /**
     * Fecha en la que el usuario agregó la colección.
     *
     * IMPORTANTE:
     * - La genera automáticamente la BD (DEFAULT CURRENT_TIMESTAMP)
     * - JPA NO debe incluir este campo en INSERT ni UPDATE
     */
    @Column(name = "fecha_agregada", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaAgregada;
}
