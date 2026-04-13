package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.CategoriaColeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad CategoriaColeccion.
 */
public interface CategoriaColeccionRepository extends JpaRepository<CategoriaColeccion, Integer>,
        JpaSpecificationExecutor<CategoriaColeccion> {

    /**
     * Busca una categoría por su nombre exacto.
     *
     * @param nombre Nombre de la categoría.
     * @return Optional con la categoría si existe.
     */
    Optional<CategoriaColeccion> findByNombre(String nombre);

    /**
     * Comprueba si existe una categoría con ese nombre.
     *
     * @param nombre Nombre de la categoría.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombre(String nombre);

}