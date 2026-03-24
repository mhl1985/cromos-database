package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario.
     * @return Optional con el usuario, si existe.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca un usuario por su nombre visible.
     *
     * @param nombreMostrar Nombre visible del usuario.
     * @return Optional con el usuario si existe.
     */
    Optional<Usuario> findByNombreMostrar(String nombreMostrar);

    /**
     * Comprueba si ya existe un usuario con ese email.
     *
     * @param email Email a comprobar.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByEmail(String email);

    /**
     * Comprueba si ya existe un usuario con ese nombre visible.
     *
     * @param nombreMostrar Nombre visible a comprobar.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombreMostrar(String nombreMostrar);
}
