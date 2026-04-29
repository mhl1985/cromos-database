package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa la tabla "cromos" en la base de datos.
 */
@Entity
@Table(name = "cromos")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cromo {

    /**
     * Clave primaria.
     * Se genera automáticamente en la BD (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_cromo")
    private Integer idCromo;

    /**
     * Colección a la que pertenece el cromo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coleccion", nullable = false)
    @ToString.Exclude
    private Coleccion coleccion;

    /**
     * Número identificativo del cromo dentro de su colección.
     * La unicidad real está definida en BD junto con id_coleccion.
     */
    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    /**
     * Nombre del cromo.
     */
    @Column(name = "nombre", length = 100)
    private String nombre;

    /**
     * Tipo del cromo.
     */
    @Column(name = "tipo", length = 50)
    private String tipo;

    /**
     * Descripción del cromo.
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    /**
     * URL de la imagen delantera del cromo.
     */
    @Column(name = "url_img_delantera", length = 255)
    private String urlImgDelantera;

    /**
     * URL de la imagen trasera del cromo.
     */
    @Column(name = "url_img_trasera", length = 255)
    private String urlImgTrasera;

    /**
     * Relación con los usuarios que tienen este cromo.
     */
    @OneToMany(mappedBy = "cromo")
    @ToString.Exclude
    private Set<UsuarioCromo> usuariosCromos = new HashSet<>();
}
