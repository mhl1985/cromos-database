package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.cromo.CromoDetalleResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoResumenResponse;

import java.util.List;

/**
 * Servicio que centraliza la lógica de negocio relacionada con los cromos.
 */
public interface CromoService {

    /**
     * Obtiene el listado de cromos aplicando los filtros recibidos.
     *
     * Todos los filtros son opcionales y pueden combinarse entre sí.
     *
     * Reglas de búsqueda:
     * - idColeccion: coincidencia exacta
     * - numero: búsqueda parcial sin distinguir mayúsculas/minúsculas
     * - nombre: búsqueda parcial sin distinguir mayúsculas/minúsculas
     * - tipo: búsqueda parcial sin distinguir mayúsculas/minúsculas
     *
     * @param idColeccion identificador de la colección
     * @param numero texto a buscar dentro del número del cromo
     * @param nombre texto a buscar dentro del nombre del cromo
     * @param tipo texto a buscar dentro del tipo del cromo
     * @return lista de DTO´s de cromos en formato resumido que cumplen los filtros
     */
    List<CromoResumenResponse> obtenerCromosFiltrados(Integer idColeccion,
                                                      String numero,
                                                      String nombre,
                                                      String tipo);

    /**
     * Obtiene el detalle de un cromo por su identificador.
     *
     * @param idCromo identificador del cromo
     * @return DTO del cromo encontrado en formato detallado
     */
    CromoDetalleResponse obtenerCromoPorId(Integer idCromo);
}