package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.CategoriaColeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad CategoriaColeccion.
 */
public interface CategoriaColeccionRepository extends JpaRepository<CategoriaColeccion, Integer> {

    Optional<CategoriaColeccion> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
