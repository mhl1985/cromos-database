package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa la tabla "categorias_coleccion".
 */
@Entity
@Table(name = "categorias_coleccion")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaColeccion {

    /**
     * Clave primaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_categoria")
    private Integer idCategoria;

    /**
     * Nombre de la categoría.
     */
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    /**
     * Descripción de la categoría.
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    /**
     * Subcategorías asociadas a esta categoría.
     */
    @OneToMany(mappedBy = "categoria")
    @ToString.Exclude
    private Set<SubcategoriaColeccion> subcategorias = new HashSet<>();

    /**
     * Relación con las colecciones de esta categoría.
     */
    @OneToMany(mappedBy = "categoria")
    @ToString.Exclude
    private Set<Coleccion> colecciones = new HashSet<>();
}
