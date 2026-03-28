package com.cromosdatabase.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa la tabla "usuarios" en la base de datos.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Solo el indicado explicitamente (id).
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
public class Usuario {

    /**
     * Clave primaria.
     * Se genera automáticamente en la BD (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /**
     * Email del usuario.
     * - Obligatorio
     * - Único (restricción definida en la BD)
     */
    @Column(name = "email", nullable = false, length = 80)
    private String email;

    /**
     * Contraseña en formato hash (BCrypt).
     * - Nunca se guarda en texto plano
     * - Obligatoria
     */
    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    /**
     * Nombre visible del usuario.
     * - Obligatorio
     * - Único (restricción definida en la BD)
     */
    @Column(name = "nombre_mostrar", nullable = false, length = 50)
    private String nombreMostrar;

    /**
     * Indica si el usuario está activo.
     * - TRUE por defecto en BD
     * - Se usará en Spring Security (enabled)
     */
    @Column(name = "activo", nullable = false)
    private boolean activo;

    /**
     * Fecha de registro del usuario.
     *
     * IMPORTANTE:
     * - La genera automáticamente la BD (DEFAULT CURRENT_TIMESTAMP)
     * - JPA NO debe incluir este campo en INSERT ni UPDATE
     */
    @Column(name = "fecha_registro", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    /**
     * Relación con los roles del usuario a través de la tabla intermedia usuarios_roles.
     *
     * mappedBy = "usuario" indica que la relación se gestiona desde la entidad UsuarioRol.
     * Se inicializa con HashSet para evitar nulls.
     */
    @OneToMany(mappedBy = "usuario")
    /**
     * Se excluye del toString para evitar recursividad infinita
     * debido a relaciones bidireccionales (Usuario <-> UsuarioRol).
     */
    @ToString.Exclude
    private Set<UsuarioRol> usuariosRoles = new HashSet<>();
}
