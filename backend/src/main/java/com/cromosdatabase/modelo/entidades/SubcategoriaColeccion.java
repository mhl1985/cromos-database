package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

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
     * Nombre de la subcategoría.
     */
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    /**
     * Descripción de la subcategoría.
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;
}
