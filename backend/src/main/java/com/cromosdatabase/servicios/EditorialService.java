package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.editorial.EditorialDetalleResponse;
import com.cromosdatabase.modelo.dtos.editorial.EditorialResumenResponse;

import java.util.List;

/**
 * Servicio que centraliza la lógica de negocio relacionada con las editoriales.
 */
public interface EditorialService {

    /**
     * Obtiene el listado de editoriales aplicando los filtros recibidos.
     *
     * Todos los filtros son opcionales y pueden combinarse entre sí.
     *
     * Reglas de búsqueda:
     * - nombre: búsqueda parcial sin distinguir mayúsculas/minúsculas
     *
     * @param nombre texto a buscar dentro del nombre de la editorial
     * @return lista de DTO´s de editoriales en formato resumido que cumplen los filtros
     */
    List<EditorialResumenResponse> obtenerEditorialesFiltradas(String nombre);

    /**
     * Obtiene el detalle de una editorial por su identificador.
     *
     * @param idEditorial identificador de la editorial
     * @return DTO de la editorial encontrada en formato detallado
     */
    EditorialDetalleResponse obtenerEditorialPorId(Integer idEditorial);
}