package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.entidades.Coleccion;

import java.util.List;

/**
 * Servicio que centraliza la lógica de negocio relacionada con las colecciones.
 */
public interface ColeccionService {

    /**
     * Obtiene el listado de colecciones aplicando los filtros recibidos.
     *
     * Todos los filtros son opcionales y pueden combinarse entre sí.
     *
     * Reglas de búsqueda:
     * - nombre: búsqueda parcial sin distinguir mayúsculas/minúsculas
     * - idCategoria: coincidencia exacta
     * - idEditorial: coincidencia exacta
     * - idSubcategoria: coincidencia exacta
     * - periodo: búsqueda parcial sin distinguir mayúsculas/minúsculas
     *
     * @param nombre texto a buscar dentro del nombre de la colección
     * @param idCategoria identificador de la categoría
     * @param idEditorial identificador de la editorial
     * @param idSubcategoria identificador de la subcategoría
     * @param periodo texto a buscar dentro del periodo de la colección
     * @return lista de colecciones que cumplen los filtros
     */
    List<Coleccion> obtenerColeccionesFiltradas(String nombre,
                                                Integer idCategoria,
                                                Integer idEditorial,
                                                Integer idSubcategoria,
                                                String periodo);

    /**
     * Obtiene el detalle de una colección por su identificador.
     *
     * @param idColeccion identificador de la colección
     * @return colección encontrada
     */
    Coleccion obtenerColeccionPorId(Integer idColeccion);
}
