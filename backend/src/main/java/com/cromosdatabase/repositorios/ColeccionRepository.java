package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Coleccion.
 */
public interface ColeccionRepository extends JpaRepository<Coleccion, Integer> {

    /**
     * Busca una colección por su nombre exacto.
     *
     * @param nombre Nombre de la colección.
     * @return Optional con la colección si existe.
     */
    Optional<Coleccion> findByNombre(String nombre);

    /**
     * Obtiene las colecciones de una editorial.
     *
     * @param idEditorial ID de la editorial.
     * @return Lista de colecciones.
     */
    List<Coleccion> findByEditorial_IdEditorial(Integer idEditorial);

    /**
     * Obtiene las colecciones de una categoría.
     *
     * @param idCategoria ID de la categoría.
     * @return Lista de colecciones.
     */
    List<Coleccion> findByCategoria_IdCategoria(Integer idCategoria);

    /**
     * Obtiene las colecciones de una subcategoría.
     *
     * @param idSubcategoria ID de la subcategoría.
     * @return Lista de colecciones.
     */
    List<Coleccion> findBySubcategoria_IdSubcategoria(Integer idSubcategoria);
}
