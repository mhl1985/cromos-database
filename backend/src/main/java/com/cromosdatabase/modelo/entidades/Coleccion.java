package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa la tabla "colecciones" en la base de datos.
 */
@Entity
@Table(name = "colecciones")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coleccion {

    /**
     * Clave primaria.
     * Se genera automáticamente en la BD (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_coleccion")
    private Integer idColeccion;

    /**
     * Nombre de la colección.
     * - Obligatorio
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Editorial de la colección.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_editorial", nullable = false)
    @ToString.Exclude
    private Editorial editorial;

    /**
     * Categoría principal de la colección.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    @ToString.Exclude
    private CategoriaColeccion categoria;

    /**
     * ID de la subcategoría asociada a la colección.
     *
     * Se mapea como campo simple para poder gestionar el valor de la columna
     * id_subcategoria sin entrar en conflicto con la relación compuesta
     * hacia SubcategoriaColeccion.
     */
    @Column(name = "id_subcategoria", nullable = false)
    private Integer idSubcategoria;

    /**
     * Subcategoría de la colección.
     *
     * La relación es compuesta (id_subcategoria, id_categoria) para garantizar
     * coherencia entre categoría y subcategoría.
     *
     * IMPORTANTE:
     * - Esta relación es de solo lectura a nivel JPA.
     * - El valor de id_categoria lo gestiona la relación "categoria".
     * - El valor de id_subcategoria lo gestiona el campo simple "idSubcategoria".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_subcategoria", referencedColumnName = "id_subcategoria", insertable = false, updatable = false),
            @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", insertable = false, updatable = false)
    })
    @ToString.Exclude
    private SubcategoriaColeccion subcategoria;

    /**
     * Periodo, año o rango asociado a la colección.
     * Ejemplos: 2022, 2025-2026, 1970-2006.
     */
    @Column(name = "periodo", length = 20)
    private String periodo;

    /**
     * País asociado a la colección.
     */
    @Column(name = "pais", length = 50)
    private String pais;

    /**
     * Descripción de la colección.
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    /**
     * URL de la imagen representativa de la colección.
     */
    @Column(name = "url_img_portada", length = 255)
    private String urlImgPortada;

    /**
     * Relación con los cromos de la colección.
     */
    @OneToMany(mappedBy = "coleccion")
    @ToString.Exclude
    private Set<Cromo> cromos = new HashSet<>();

    /**
     * Relación con los usuarios que tienen asociada esta colección.
     */
    @OneToMany(mappedBy = "coleccion")
    @ToString.Exclude
    private Set<UsuarioColeccion> usuariosColecciones = new HashSet<>();
}
