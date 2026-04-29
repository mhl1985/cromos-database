package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Rol.
 */
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Busca un rol por su nombre.
     *
     * @param nombre Nombre del rol.
     * @return Optional con el rol, si existe.
     */
    Optional<Rol> findByNombre(String nombre);

    /**
     * Comprueba si existe un rol con ese nombre.
     */
    boolean existsByNombre(String nombre);
}
