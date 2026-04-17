package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.UsuarioCromo;
import com.cromosdatabase.modelo.entidades.UsuarioCromoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad UsuarioCromo.
 */
public interface UsuarioCromoRepository extends JpaRepository<UsuarioCromo, UsuarioCromoId> {

    /**
     * Obtiene los cromos asociados a un usuario.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de relaciones usuario-cromo.
     */
    List<UsuarioCromo> findByUsuario_IdUsuario(Integer idUsuario);

    /**
     * Obtiene los usuarios que tienen un cromo concreto.
     *
     * @param idCromo ID del cromo.
     * @return Lista de relaciones usuario-cromo.
     */
    List<UsuarioCromo> findByCromo_IdCromo(Integer idCromo);

    /**
     * Obtiene los cromos de una colección concreta
     * asociados a un usuario.
     *
     * @param idUsuario ID del usuario.
     * @param idColeccion ID de la colección.
     * @return Lista de relaciones usuario-cromo
     * correspondientes a esa colección.
     */
    List<UsuarioCromo> findByUsuario_IdUsuarioAndCromo_Coleccion_IdColeccion(
            Integer idUsuario,
            Integer idColeccion
    );

    /**
     * Elimina la relación entre un usuario y un cromo concreto.
     *
     * @param idUsuario ID del usuario.
     * @param idCromo ID del cromo.
     */
    void deleteByUsuario_IdUsuarioAndCromo_IdCromo(Integer idUsuario, Integer idCromo);
}