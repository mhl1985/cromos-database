package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Cromo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
}