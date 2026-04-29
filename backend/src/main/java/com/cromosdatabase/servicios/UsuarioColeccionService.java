package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionResumenResponse;

import java.util.List;

/**
 * Servicio para gestionar las colecciones
 * del usuario autenticado.
 */
public interface UsuarioColeccionService {

    /**
     * Obtiene el listado de colecciones asociadas
     * al usuario autenticado.
     *
     * @return listado de colecciones del usuario autenticado.
     */
    List<UsuarioColeccionResumenResponse> obtenerColeccionesUsuarioAutenticado();

    /**
     * Añade una colección al usuario autenticado.
     *
     * @param idColeccion ID de la colección a asociar.
     */
    void anadirColeccionUsuarioAutenticado(Integer idColeccion);

    /**
     * Elimina una colección del usuario autenticado.
     *
     * Además de eliminar la relación usuario-colección,
     * también elimina las relaciones usuario-cromo
     * correspondientes a los cromos de esa colección.
     *
     * @param idColeccion ID de la colección a desasociar.
     */
    void eliminarColeccionUsuarioAutenticado(Integer idColeccion);
}