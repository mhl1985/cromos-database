package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa la tabla "editoriales".
 */
@Entity
@Table(name = "editoriales")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Editorial {

    /**
     * Clave primaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_editorial")
    private Integer idEditorial;

    /**
     * Nombre de la editorial.
     * - Obligatorio
     * - Único (definido en BD)
     */
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    /**
     * Descripción de la editorial.
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    /**
     * Relación con colecciones de esta editorial.
     */
    @OneToMany(mappedBy = "editorial")
    @ToString.Exclude
    private Set<Coleccion> colecciones = new HashSet<>();
}
