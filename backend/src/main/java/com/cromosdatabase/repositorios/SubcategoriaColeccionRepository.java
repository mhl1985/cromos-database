package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.SubcategoriaColeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad SubcategoriaColeccion.
 */
public interface SubcategoriaColeccionRepository extends JpaRepository<SubcategoriaColeccion, Integer> {

    /**
     * Obtiene las subcategorías asociadas a una categoría.
     *
     * @param idCategoria ID de la categoría.
     * @return Lista de subcategorías.
     */
    List<SubcategoriaColeccion> findByCategoria_IdCategoria(Integer idCategoria);

}
