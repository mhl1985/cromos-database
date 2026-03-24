package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa la tabla "roles" en la base de datos.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    /**
     * Clave primaria.
     * Se genera automáticamente en la BD (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_rol")
    private Integer idRol;

    /**
     * Nombre del rol.
     * - Obligatorio
     * - Único (definido en BD)
     * Ejemplo: ROLE_USER, ROLE_ADMIN
     */
    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    /**
     * Descripción del rol.
     * - Opcional
     */
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    /**
     * Relación con los usuarios que tienen este rol, a través de la tabla intermedia usuarios_roles.
     *
     * mappedBy = "rol" indica que la relación se gestiona desde UsuarioRol.
     */
    @OneToMany(mappedBy = "rol")
    /**
     * Se excluye del toString para evitar recursividad infinita
     * debido a relaciones bidireccionales (UsuarioRol <-> Usuario).
     */
    @ToString.Exclude
    /**
     * Inicialización por defecto necesaria cuando se usa @Builder.
     * Evita que la colección sea null al construir el objeto.
     */
    @Builder.Default
    private Set<UsuarioRol> usuariosRoles = new HashSet<>();
}
