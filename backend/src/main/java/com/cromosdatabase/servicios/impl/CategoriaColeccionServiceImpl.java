package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.CategoriaColeccionNoEncontradaException;
import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.CategoriaColeccion;
import com.cromosdatabase.modelo.mappers.CategoriaColeccionMapper;
import com.cromosdatabase.repositorios.CategoriaColeccionRepository;
import com.cromosdatabase.repositorios.filtros.CategoriaColeccionFilters;
import com.cromosdatabase.servicios.CategoriaColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de categorías de colección.
 *
 * Centraliza la lógica de negocio relacionada con:
 * - obtención de categorías filtradas
 * - obtención del detalle de una categoría por id
 */
@Service
@RequiredArgsConstructor
public class CategoriaColeccionServiceImpl implements CategoriaColeccionService {

    /**
     * Repositorio de acceso a datos de categorías.
     */
    private final CategoriaColeccionRepository categoriaColeccionRepository;

    /**
     * Mapper de conversión entre entidad CategoriaColeccion y DTOs de respuesta.
     */
    private final CategoriaColeccionMapper categoriaColeccionMapper;

    /**
     * Obtiene el listado de categorías aplicando los filtros recibidos.
     *
     * Todos los filtros son opcionales y pueden combinarse entre sí.
     *
     * Reglas de búsqueda:
     * - nombre: búsqueda parcial sin distinguir mayúsculas/minúsculas
     *
     * @param nombre texto a buscar dentro del nombre de la categoría
     * @return DTO de lista de categorías que cumplen los filtros en formato resumido
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoriaColeccionResumenResponse> obtenerCategoriasFiltradas(String nombre) {

        /*
         * Se normalizan los filtros de texto antes de construir la consulta.
         * Se eliminan espacios sobrantes al principio y al final.
         * Se convierten cadenas vacías en null.
         */
        String nombreNormalizado = normalizarTextoFiltro(nombre);

        Specification<CategoriaColeccion> filtroCompleto = null;

        // Filtro por nombre.
        if (nombreNormalizado != null) {
            filtroCompleto = combinarFiltros(
                    filtroCompleto,
                    CategoriaColeccionFilters.byNombre(nombreNormalizado)
            );
        }

        /*
         * Ejecución final:
         * - Si no hay filtros: devuelve todas las categorías
         * - Si hay filtros: los aplica y devuelve las categorías resultantes
         */
        List<CategoriaColeccion> categorias;

        if (filtroCompleto == null) {
            categorias = categoriaColeccionRepository.findAll();
        } else {
            categorias = categoriaColeccionRepository.findAll(filtroCompleto);
        }

        // Mapeo a DTO
        List<CategoriaColeccionResumenResponse> response =
                categoriaColeccionMapper.toResumenResponseList(categorias);

        return response;
    }

    /**
     * Obtiene una categoría por su identificador.
     *
     * Si no existe ninguna categoría con el id indicado, se lanza una
     * excepción controlada para que sea gestionada por el
     * GlobalExceptionHandler.
     *
     * @param idCategoria identificador de la categoría
     * @return DTO de categoría encontrada en formato detallado
     */
    @Override
    @Transactional(readOnly = true)
    public CategoriaColeccionDetalleResponse obtenerCategoriaPorId(Integer idCategoria) {

        CategoriaColeccion categoriaColeccion = categoriaColeccionRepository.findById(idCategoria)
                .orElseThrow(() -> new CategoriaColeccionNoEncontradaException(
                        "No existe ninguna categoría con id " + idCategoria
                ));

        // Mapeo a DTO
        CategoriaColeccionDetalleResponse response =
                categoriaColeccionMapper.toDetalleResponse(categoriaColeccion);

        return response;
    }

    /**
     * Normaliza un texto recibido como filtro.
     *
     * Reglas:
     * - Si el valor es null, devuelve null
     * - Elimina espacios al inicio y al final
     * - Si tras el trim queda vacío, devuelve null
     *
     * @param texto texto recibido como filtro
     * @return texto normalizado o null si no aporta valor para filtrar
     */
    private String normalizarTextoFiltro(String texto) {

        if (texto == null) {
            return null;
        }

        String textoNormalizado = texto.trim();

        if (textoNormalizado.isEmpty()) {
            return null;
        }

        return textoNormalizado;
    }

    /**
     * Combina dos filtros mediante una operación AND.
     *
     * Si el filtro base es null, devuelve directamente el nuevo filtro.
     *
     * @param filtroBase filtro acumulado hasta el momento
     * @param nuevoFiltro nuevo filtro a añadir
     * @return filtro combinado
     */
    private Specification<CategoriaColeccion> combinarFiltros(
            Specification<CategoriaColeccion> filtroBase,
            Specification<CategoriaColeccion> nuevoFiltro) {

        if (filtroBase == null) {
            return nuevoFiltro;
        }

        return filtroBase.and(nuevoFiltro);
    }
}