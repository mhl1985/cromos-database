package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.ColeccionNoEncontradaException;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.Coleccion;
import com.cromosdatabase.modelo.mappers.ColeccionMapper;
import com.cromosdatabase.repositorios.ColeccionRepository;
import com.cromosdatabase.repositorios.filtros.ColeccionFilters;
import com.cromosdatabase.servicios.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de colecciones.
 *
 * Centraliza la lógica de negocio relacionada con:
 * - obtención de colecciones filtradas
 * - obtención del detalle de una colección por id
 */
@Service
@RequiredArgsConstructor
public class ColeccionServiceImpl implements ColeccionService {

    /**
     * Repositorio de acceso a datos de colecciones.
     */
    private final ColeccionRepository coleccionRepository;

    /**
     * Mapper de conversión entre entidad Coleccion y DTOs de respuesta.
     */
    private final ColeccionMapper coleccionMapper;

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
     * @return DTO de lista de colecciones que cumplen los filtros en formato resumido
     */
    @Override
    @Transactional(readOnly = true)
    public List<ColeccionResumenResponse> obtenerColeccionesFiltradas(String nombre,
                                                                      Integer idCategoria,
                                                                      Integer idEditorial,
                                                                      Integer idSubcategoria,
                                                                      String periodo) {

        /*
         * Se normalizan los filtros de texto antes de construir la consulta.
         * Se eliminan espacios sobrantes al principio y al final.
         * Se convierten cadenas vacías en null.
         */
        String nombreNormalizado = normalizarTextoFiltro(nombre);
        String periodoNormalizado = normalizarTextoFiltro(periodo);

        Specification<Coleccion> filtroCompleto = null;

        // Filtro por nombre.
        if (nombreNormalizado != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    ColeccionFilters.byNombre(nombreNormalizado));
        }

        // Filtro por categoría.
        if (idCategoria != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    ColeccionFilters.byIdCategoria(idCategoria));
        }

        // Filtro por editorial.
        if (idEditorial != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    ColeccionFilters.byIdEditorial(idEditorial));
        }

        // Filtro por subcategoría
        if (idSubcategoria != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    ColeccionFilters.byIdSubcategoria(idSubcategoria));
        }

        // Filtro por periodo
        if (periodoNormalizado != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    ColeccionFilters.byPeriodo(periodoNormalizado));
        }

        /*
         * Ejecución final:
         * - Si no hay filtros: devuelve todas las colecciones
         * - Si hay filtros: los aplica y devuelve las colecciones resultantes
         */
        List<Coleccion> colecciones;

        if (filtroCompleto == null) {
            colecciones = coleccionRepository.findAll();
        } else {
            colecciones = coleccionRepository.findAll(filtroCompleto);
        }

        // Mapeo a DTO
        List<ColeccionResumenResponse> response =
                coleccionMapper.toResumenResponseList(colecciones);

        return response;
    }

    /**
     * Obtiene una colección por su identificador.
     *
     * Si no existe ninguna colección con el id indicado, se lanza una
     * excepción controlada para que sea gestionada por el
     * GlobalExceptionHandler.
     *
     * @param idColeccion identificador de la colección
     * @return DTO de colección encontrada en formato detallado
     */
    @Override
    @Transactional(readOnly = true)
    public ColeccionDetalleResponse obtenerColeccionPorId(Integer idColeccion) {

        Coleccion coleccion = coleccionRepository.findById(idColeccion)
                .orElseThrow(() -> new ColeccionNoEncontradaException(
                        "No existe ninguna colección con id " + idColeccion
                ));

        // Mapeo a DTO
        ColeccionDetalleResponse response =
                coleccionMapper.toDetalleResponse(coleccion);

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
    private Specification<Coleccion> combinarFiltros(Specification<Coleccion> filtroBase,
                                                     Specification<Coleccion> nuevoFiltro) {
        if (filtroBase == null) {
            return nuevoFiltro;
        }

        return filtroBase.and(nuevoFiltro);
    }
}