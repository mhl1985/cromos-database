package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.UsuarioColeccion;
import com.cromosdatabase.modelo.entidades.UsuarioColeccionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad UsuarioColeccion.
 */
public interface UsuarioColeccionRepository extends JpaRepository<UsuarioColeccion, UsuarioColeccionId> {

    /**
     * Obtiene las colecciones asociadas a un usuario.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de relaciones usuario-colección.
     */
    List<UsuarioColeccion> findByUsuario_IdUsuario(Integer idUsuario);

    /**
     * Obtiene los usuarios que tienen asociada una colección concreta.
     *
     * @param idColeccion ID de la colección.
     * @return Lista de relaciones usuario-colección.
     */
    List<UsuarioColeccion> findByColeccion_IdColeccion(Integer idColeccion);
}
