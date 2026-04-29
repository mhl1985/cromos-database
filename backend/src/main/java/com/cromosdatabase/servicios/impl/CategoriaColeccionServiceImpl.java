package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.CategoriaColeccionNoEncontradaException;
import com.cromosdatabase.comun.utiles.FiltroUtils;
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
        String nombreNormalizado = FiltroUtils.normalizarTextoFiltro(nombre);

        Specification<CategoriaColeccion> filtroCompleto = null;

        // Filtro por nombre.
        if (nombreNormalizado != null) {
            filtroCompleto = FiltroUtils.combinarFiltros(
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
}