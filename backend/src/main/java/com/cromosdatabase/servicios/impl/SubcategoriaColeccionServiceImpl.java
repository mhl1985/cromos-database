package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.SubcategoriaColeccionNoEncontradaException;
import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.SubcategoriaColeccion;
import com.cromosdatabase.modelo.mappers.SubcategoriaColeccionMapper;
import com.cromosdatabase.repositorios.SubcategoriaColeccionRepository;
import com.cromosdatabase.repositorios.filtros.SubcategoriaColeccionFilters;
import com.cromosdatabase.servicios.SubcategoriaColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de subcategorías de colección.
 *
 * Centraliza la lógica de negocio relacionada con:
 * - obtención de subcategorías filtradas
 * - obtención del detalle de una subcategoría por id
 */
@Service
@RequiredArgsConstructor
public class SubcategoriaColeccionServiceImpl implements SubcategoriaColeccionService {

    /**
     * Repositorio de acceso a datos de subcategorías.
     */
    private final SubcategoriaColeccionRepository subcategoriaColeccionRepository;

    /**
     * Mapper de conversión entre entidad SubcategoriaColeccion y DTOs de respuesta.
     */
    private final SubcategoriaColeccionMapper subcategoriaColeccionMapper;

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
     * @param idCategoria identificador de la categoría
     * @return DTO de lista de subcategorías que cumplen los filtros en formato resumido
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubcategoriaColeccionResumenResponse> obtenerSubcategoriasFiltradas(
            String nombre,
            Integer idCategoria) {

        /*
         * Se normalizan los filtros de texto antes de construir la consulta.
         */
        String nombreNormalizado = normalizarTextoFiltro(nombre);

        Specification<SubcategoriaColeccion> filtroCompleto = null;

        // Filtro por nombre
        if (nombreNormalizado != null) {
            filtroCompleto = combinarFiltros(
                    filtroCompleto,
                    SubcategoriaColeccionFilters.byNombre(nombreNormalizado)
            );
        }

        // Filtro por idCategoria
        if (idCategoria != null) {
            filtroCompleto = combinarFiltros(
                    filtroCompleto,
                    SubcategoriaColeccionFilters.byIdCategoria(idCategoria)
            );
        }

        /*
         * Ejecución final
         */
        List<SubcategoriaColeccion> subcategorias;

        if (filtroCompleto == null) {
            subcategorias = subcategoriaColeccionRepository.findAll();
        } else {
            subcategorias = subcategoriaColeccionRepository.findAll(filtroCompleto);
        }

        // Mapeo a DTO
        List<SubcategoriaColeccionResumenResponse> response =
                subcategoriaColeccionMapper.toResumenResponseList(subcategorias);

        return response;
    }

    /**
     * Obtiene una subcategoría por su identificador.
     *
     * @param idSubcategoria identificador de la subcategoría
     * @return DTO de subcategoría encontrada en formato detallado
     */
    @Override
    @Transactional(readOnly = true)
    public SubcategoriaColeccionDetalleResponse obtenerSubcategoriaPorId(Integer idSubcategoria) {

        SubcategoriaColeccion subcategoria = subcategoriaColeccionRepository.findById(idSubcategoria)
                .orElseThrow(() -> new SubcategoriaColeccionNoEncontradaException(
                        "No existe ninguna subcategoría con id " + idSubcategoria
                ));

        SubcategoriaColeccionDetalleResponse response =
                subcategoriaColeccionMapper.toDetalleResponse(subcategoria);

        return response;
    }

    /**
     * Normaliza un texto recibido como filtro.
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
     * Combina dos filtros mediante AND.
     */
    private Specification<SubcategoriaColeccion> combinarFiltros(
            Specification<SubcategoriaColeccion> filtroBase,
            Specification<SubcategoriaColeccion> nuevoFiltro) {

        if (filtroBase == null) {
            return nuevoFiltro;
        }

        return filtroBase.and(nuevoFiltro);
    }
}