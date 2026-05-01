package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.paginas.InicioPageResponse;

/**
 * Servicio para cargar la página de inicio.
 */
public interface PaginaInicioService {

    /**
     * Carga todos los datos necesarios para pintar la página de inicio.
     *
     * Si la petición llega con usuario autenticado, también se incluirá
     * información resumida de su actividad.
     *
     * @return DTO con los datos de la página de inicio
     */
    InicioPageResponse cargarPaginaInicio();
}