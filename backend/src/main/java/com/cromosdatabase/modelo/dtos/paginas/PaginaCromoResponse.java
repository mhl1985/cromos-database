package com.cromosdatabase.modelo.dtos.paginas;

import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoDetalleResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioPoseedorCromoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta para cargar la página de un cromo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginaCromoResponse {

    /**
     * Datos de la colección a la que pertenece el cromo.
     */
    private ColeccionDetalleResponse coleccion;

    /**
     * Datos del cromo.
     */
    private CromoDetalleResponse cromo;

    /**
     * Información del cromo para el usuario autenticado.
     *
     * Se devuelve null en estos casos:
     * - si no hay usuario autenticado
     * - si el usuario no tiene agregada la colección del cromo
     * - si el usuario tiene la colección, pero no tiene este cromo
     */
    private UsuarioColeccionCromoResponse cromoUsuario;

    /**
     * Usuarios que tienen este cromo disponible para intercambio.
     *
     * No se incluye el usuario autenticado actual (si lo hubiera).
     */
    private List<UsuarioPoseedorCromoResponse> usuariosPoseedores;
}