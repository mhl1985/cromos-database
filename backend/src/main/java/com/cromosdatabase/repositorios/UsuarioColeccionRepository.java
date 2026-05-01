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

    /**
     * Comprueba si una colección concreta está asociada
     * a un usuario determinado.
     *
     * @param idUsuario ID del usuario.
     * @param idColeccion ID de la colección.
     * @return true si la colección está asociada al usuario,
     * false en caso contrario.
     */
    boolean existsByUsuario_IdUsuarioAndColeccion_IdColeccion(Integer idUsuario, Integer idColeccion);

    /**
     * Elimina la relación entre un usuario y una colección concreta.
     *
     * @param idUsuario id del usuario.
     * @param idColeccion id de la colección.
     */
    void deleteByUsuario_IdUsuarioAndColeccion_IdColeccion(Integer idUsuario, Integer idColeccion);

    /**
     * Obtiene las 10 últimas colecciones añadidas por un usuario.
     *
     * @param idUsuario id del usuario
     * @return lista de relaciones usuario-colección ordenadas por fecha de agregación descendente
     */
    List<UsuarioColeccion> findTop10ByUsuario_IdUsuarioOrderByFechaAgregadaDesc(Integer idUsuario);
}