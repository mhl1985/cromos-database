package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Coleccion.
 *
 * Extiende:
 * - JpaRepository: Proporciona operaciones CRUD básicas (findAll, findById, save, delete, etc).
 * - JpaSpecificationExecutor: Permite ejecutar consultas dinámicas utilizando Specification.
 */
public interface ColeccionRepository extends JpaRepository<Coleccion, Integer>,
        JpaSpecificationExecutor<Coleccion> {

    /**
     * Busca una colección por su nombre exacto.
     *
     * @param nombre nombre exacto de la colección
     * @return Optional con la colección si existe
     */
    Optional<Coleccion> findByNombre(String nombre);

    /**
     * Obtiene las 10 últimas colecciones añadidas al sistema.
     *
     * Actualmente, se considera como criterio de "últimas"
     * el identificador de colección en orden descendente.
     *
     * @return lista de las 10 últimas colecciones
     */
    List<Coleccion> findTop10ByOrderByIdColeccionDesc();

    /**
     * Obtiene las colecciones asociadas a una categoría y subcategoría concretas.
     *
     * @param idCategoria id de la categoría
     * @param idSubcategoria id de la subcategoría
     * @return lista de colecciones de esa subcategoría
     */
    List<Coleccion> findByCategoria_IdCategoriaAndIdSubcategoria(
            Integer idCategoria,
            Integer idSubcategoria
    );
}