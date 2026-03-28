package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Editorial.
 */
public interface EditorialRepository extends JpaRepository<Editorial, Integer> {

    Optional<Editorial> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
