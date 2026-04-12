package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
}