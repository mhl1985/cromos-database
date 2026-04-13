package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionResumenResponse;

import java.util.List;

/**
 * Servicio que centraliza la lógica de negocio relacionada con las subcategorías de colección.
 */
public interface SubcategoriaColeccionService {

    /**
     * Obtiene el listado de subcategorías aplicando los filtros recibidos.
     *
     * Todos los filtros son opcionales y pueden combinarse entre sí.
     *
     * Reglas de búsqueda:
     * - nombre: búsqueda parcial sin distinguir mayúsculas/minúsculas
     * - idCategoria: coincidencia exacta
     *
     * @param nombre texto a buscar dentro del nombre de la subcategoría
     * @param idCategoria identificador de la categoría a la que pertenece la subcategoría
     * @return lista de DTO´s de subcategorías en formato resumido que cumplen los filtros
     */
    List<SubcategoriaColeccionResumenResponse> obtenerSubcategoriasFiltradas(String nombre,
                                                                             Integer idCategoria);

    /**
     * Obtiene el detalle de una subcategoría por su identificador.
     *
     * @param idSubcategoria identificador de la subcategoría
     * @return DTO de la subcategoría encontrada en formato detallado
     */
    SubcategoriaColeccionDetalleResponse obtenerSubcategoriaPorId(Integer idSubcategoria);
}