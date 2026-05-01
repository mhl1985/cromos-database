package com.cromosdatabase.modelo.dtos.paginas;

import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionResumenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta con la actividad resumida del usuario autenticado
 * para la página de inicio.
 *
 * Se devuelve únicamente cuando la petición llega con un usuario autenticado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActividadUsuarioInicioResponse {

    /**
     * Últimas colecciones añadidas por el usuario autenticado.
     */
    private List<UsuarioColeccionResumenResponse> ultimasColeccionesAgregadas;

    /**
     * Número total de colecciones asociadas al usuario autenticado.
     */
    private Integer totalColecciones;

    /**
     * Número total de cromos que tiene el usuario autenticado.
     */
    private Integer totalCromosUsuario;

    /**
     * Número total de cromos que el usuario autenticado tiene
     * disponibles para intercambio.
     */
    private Integer totalCromosIntercambiables;
}