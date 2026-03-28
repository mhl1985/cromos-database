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
}
