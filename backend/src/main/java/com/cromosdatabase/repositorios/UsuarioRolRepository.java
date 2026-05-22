package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.UsuarioRol;
import com.cromosdatabase.modelo.entidades.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad UsuarioRol.
 */
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {

    /**
     * Obtiene los roles asociados a un usuario mediante su ID.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de relaciones usuario-rol.
     */
    List<UsuarioRol> findByUsuario_IdUsuario(Integer idUsuario);

    /**
     * Obtiene los roles asociados a un usuario mediante su email.
     *
     * @param email Email del usuario.
     * @return Lista de relaciones usuario-rol.
     */
    List<UsuarioRol> findByUsuario_Email(String email);

    /**
     * Elimina todas las relaciones usuario-rol de un usuario.
     *
     * @param idUsuario ID del usuario.
     */
    void deleteByUsuario_IdUsuario(Integer idUsuario);
}
