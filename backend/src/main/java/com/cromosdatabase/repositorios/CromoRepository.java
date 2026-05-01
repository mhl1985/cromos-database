package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Cromo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Cromo.
 *
 * Extiende:
 * - JpaRepository: Proporciona operaciones CRUD básicas (findAll, findById, save, delete, etc).
 * - JpaSpecificationExecutor: Permite ejecutar consultas dinámicas utilizando Specification.
 */
public interface CromoRepository extends JpaRepository<Cromo, Integer>,
        JpaSpecificationExecutor<Cromo> {

    /**
     * Busca un cromo por colección y número.
     *
     * @param idColeccion ID de la colección.
     * @param numero Número del cromo.
     * @return Optional con el cromo si existe.
     */
    Optional<Cromo> findByColeccion_IdColeccionAndNumero(Integer idColeccion, String numero);

    /**
     * Obtiene todos los cromos de una colección.
     *
     * @param idColeccion ID de la colección.
     * @return Lista de cromos de la colección.
     */
    List<Cromo> findByColeccion_IdColeccion(Integer idColeccion);

    /**
     * Comprueba si existe un cromo con un número concreto dentro de una colección.
     *
     * @param idColeccion ID de la colección.
     * @param numero Número del cromo.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByColeccion_IdColeccionAndNumero(Integer idColeccion, String numero);

    /**
     * Obtiene los cromos de una colección cuyos IDs estén
     * incluidos en la lista recibida.
     *
     * @param idColeccion ID de la colección.
     * @param idsCromo lista de IDs de cromo.
     * @return Lista de cromos encontrados en la colección.
     */
    List<Cromo> findByColeccion_IdColeccionAndIdCromoIn(Integer idColeccion, List<Integer> idsCromo);

    /**
     * Obtiene los 10 últimos cromos añadidos al sistema.
     *
     * Actualmente, se considera como criterio de "últimos"
     * el identificador de cromo en orden descendente.
     *
     * @return lista de los 10 últimos cromos
     */
    List<Cromo> findTop10ByOrderByIdCromoDesc();

    /**
     * Obtiene 10 cromos aleatorios.
     *
     * Se utiliza consulta nativa porque la ordenación aleatoria
     * depende de la base de datos.
     *
     * @return lista de 10 cromos aleatorios
     */
    @Query(value =
            "SELECT * " +
                    "FROM cromos " +
                    "ORDER BY RAND() " +
                    "LIMIT 10", nativeQuery = true)
    List<Cromo> find10Aleatorios();
}