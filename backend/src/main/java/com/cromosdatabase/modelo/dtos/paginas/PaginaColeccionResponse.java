package com.cromosdatabase.modelo.dtos.paginas;

import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoResumenResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta para cargar la página de una colección.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginaColeccionResponse {

    /**
     * Últimos cromos añadidos a la colección.
     */
    private List<CromoResumenResponse> ultimosCromos;

    /**
     * Datos de la colección.
     */
    private ColeccionDetalleResponse coleccion;

    /**
     * Listado completo de cromos de la colección.
     */
    private List<CromoResumenResponse> cromos;

    /**
     * Cromos de esta colección que tiene realmente el usuario autenticado.
     *
     * Se devuelve null en estos casos:
     * - si la petición no llega con usuario autenticado.
     * - si el usuario autenticado no tiene agregada esta colección.
     * - si el usuario autenticado tiene la colección, pero no tiene cromos de ella.
     */
    private List<UsuarioColeccionCromoResponse> cromosUsuario;
}