package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.CategoriaColeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    /**
     * Obtiene 5 categorías aleatorias.
     *
     * Se utiliza consulta nativa porque la ordenación aleatoria
     * depende de la base de datos.
     *
     * @return lista de 5 categorías aleatorias
     */
    @Query(value =
            "SELECT * " +
                    "FROM categorias_coleccion " +
                    "ORDER BY RAND() " +
                    "LIMIT 5", nativeQuery = true)
    List<CategoriaColeccion> find5Aleatorias();
}