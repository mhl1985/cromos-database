package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionDetalleResponse;

import java.util.List;

/**
 * Servicio que centraliza la lógica de negocio relacionada con las categorías de colección.
 */
public interface CategoriaColeccionService {

    /**
     * Obtiene el listado de categorías aplicando los filtros recibidos.
     *
     * Todos los filtros son opcionales y pueden combinarse entre sí.
     *
     * Reglas de búsqueda:
     * - nombre: búsqueda parcial sin distinguir mayúsculas/minúsculas
     *
     * @param nombre texto a buscar dentro del nombre de la categoría
     * @return lista de DTO´s de categorías que cumplen los filtros
     */
    List<CategoriaColeccionDetalleResponse> obtenerCategoriasFiltradas(String nombre);

    /**
     * Obtiene el detalle de una categoría por su identificador.
     *
     * @param idCategoria identificador de la categoría
     * @return DTO de la categoría encontrada en formato detallado
     */
    CategoriaColeccionDetalleResponse obtenerCategoriaPorId(Integer idCategoria);
}