package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa la tabla "subcategorias_coleccion".
 */
@Entity
@Table(name = "subcategorias_coleccion")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoriaColeccion {

    /**
     * Clave primaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_subcategoria")
    private Integer idSubcategoria;

    /**
     * Categoría a la que pertenece la subcategoría.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    @ToString.Exclude
    private CategoriaColeccion categoria;

    /**
     * ID de la categoría a la que pertenece la subcategoría.
     *
     * Se mapea también como campo simple para permitir referencias compuestas
     * desde otras entidades, como Coleccion.
     *
     * IMPORTANTE:
     * - El valor real de la relación lo gestiona el campo "categoria".
     * - Este campo se deja de solo lectura en JPA.
     */
    @Column(name = "id_categoria", nullable = false, insertable = false, updatable = false)
    private Integer idCategoria;

    /**
     * Nombre de la subcategoría.
     */
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    /**
     * Descripción de la subcategoría.
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    /**
     * Relación con las colecciones de esta subcategoría.
     */
    @OneToMany(mappedBy = "subcategoria")
    @ToString.Exclude
    private Set<Coleccion> colecciones = new HashSet<>();
}
