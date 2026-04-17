package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionListaCromosEdicionRequest;

import java.util.List;

/**
 * Servicio para gestionar los cromos de cada colección
 * del usuario autenticado.
 */
public interface UsuarioCromoService {

    /**
     * Obtiene el listado completo de cromos de una colección
     * del usuario autenticado, incluyendo la información guardada
     * por el usuario sobre cada uno de ellos.
     *
     * @param idColeccion ID de la colección.
     * @return listado de cromos de la colección con la información
     * guardada por el usuario.
     */
    List<UsuarioColeccionCromoResponse> obtenerCromosColeccionUsuario(Integer idColeccion);

    /**
     * Actualiza de forma masiva los cromos de una colección
     * del usuario autenticado.
     *
     * @param idColeccion ID de la colección.
     * @param request lista de cromos enviada desde frontend.
     * @return listado actualizado de cromos de la colección
     * perteneciente al usuario.
     */
    List<UsuarioColeccionCromoResponse> actualizarCromosColeccionUsuario(
            Integer idColeccion,
            UsuarioColeccionListaCromosEdicionRequest request
    );
}