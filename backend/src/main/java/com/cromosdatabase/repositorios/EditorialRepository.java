package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Editorial.
 */
public interface EditorialRepository extends JpaRepository<Editorial, Integer> {

    /**
     * Busca una editorial por su nombre exacto.
     *
     * @param nombre Nombre de la editorial.
     * @return Optional con la editorial si existe.
     */
    Optional<Editorial> findByNombre(String nombre);

    /**
     * Comprueba si existe una editorial con ese nombre.
     *
     * @param nombre Nombre de la editorial.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombre(String nombre);

}
