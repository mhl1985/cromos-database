package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.CategoriaColeccionNoEncontradaException;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.paginas.PaginaCategoriaResponse;
import com.cromosdatabase.modelo.dtos.paginas.PaginaCategoriaSubcategoriaResponse;
import com.cromosdatabase.modelo.entidades.CategoriaColeccion;
import com.cromosdatabase.modelo.entidades.Coleccion;
import com.cromosdatabase.modelo.entidades.SubcategoriaColeccion;
import com.cromosdatabase.modelo.mappers.ColeccionMapper;
import com.cromosdatabase.repositorios.CategoriaColeccionRepository;
import com.cromosdatabase.repositorios.ColeccionRepository;
import com.cromosdatabase.repositorios.SubcategoriaColeccionRepository;
import com.cromosdatabase.servicios.PaginaCategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del servicio para cargar la página de una categoría.
 *
 * Este servicio agrupa la categoría, sus subcategorías y las colecciones
 * asociadas a cada subcategoría.
 */
@Service
@RequiredArgsConstructor
public class PaginaCategoriaServiceImpl implements PaginaCategoriaService {

    /**
     * Repositorio de categorías.
     */
    private final CategoriaColeccionRepository categoriaColeccionRepository;

    /**
     * Repositorio de subcategorías.
     */
    private final SubcategoriaColeccionRepository subcategoriaColeccionRepository;

    /**
     * Repositorio de colecciones.
     */
    private final ColeccionRepository coleccionRepository;

    /**
     * Mapper de colecciones.
     */
    private final ColeccionMapper coleccionMapper;

    /**
     * Carga todos los datos necesarios para pintar la página
     * de una categoría.
     *
     * @param idCategoria id de la categoría
     * @return DTO con los datos de la página de categoría
     */
    @Override
    @Transactional(readOnly = true)
    public PaginaCategoriaResponse cargarPaginaCategoria(Integer idCategoria) {

        // Obtenemos la categoría solicitada.
        // Si no existe, se lanza excepción controlada.
        CategoriaColeccion categoria = categoriaColeccionRepository.findById(idCategoria)
                .orElseThrow(() -> new CategoriaColeccionNoEncontradaException(
                        "No existe ninguna categoría con id " + idCategoria
                ));

        // Obtenemos las subcategorías hijas de la categoría.
        List<SubcategoriaColeccion> subcategoriasEntidad =
                subcategoriaColeccionRepository.findByCategoria_IdCategoria(idCategoria);

        // Construimos el listado de subcategorías con sus colecciones.
        List<PaginaCategoriaSubcategoriaResponse> subcategorias =
                construirSubcategoriasResponse(idCategoria, subcategoriasEntidad);

        PaginaCategoriaResponse response = new PaginaCategoriaResponse(
                categoria.getIdCategoria(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                subcategorias
        );

        return response;
    }

    /**
     * Construye el listado de subcategorías de la página,
     * incluyendo las colecciones asociadas a cada una de ellas.
     *
     * @param idCategoria id de la categoría padre
     * @param subcategoriasEntidad lista de subcategorías
     * @return lista de subcategorías con sus colecciones
     */
    private List<PaginaCategoriaSubcategoriaResponse> construirSubcategoriasResponse(
            Integer idCategoria,
            List<SubcategoriaColeccion> subcategoriasEntidad) {

        List<PaginaCategoriaSubcategoriaResponse> subcategoriasResponse = new ArrayList<>();

        for (SubcategoriaColeccion subcategoria : subcategoriasEntidad) {

            // Obtenemos las colecciones asociadas a esta subcategoría.
            List<Coleccion> coleccionesEntidad =
                    coleccionRepository.findByCategoria_IdCategoriaAndIdSubcategoria(
                            idCategoria,
                            subcategoria.getIdSubcategoria()
                    );

            // Si no hay colecciones, MapStruct devolverá lista vacía.
            List<ColeccionDetalleResponse> colecciones =
                    coleccionMapper.toDetalleResponseList(coleccionesEntidad);

            PaginaCategoriaSubcategoriaResponse subcategoriaResponse =
                    new PaginaCategoriaSubcategoriaResponse(
                            subcategoria.getIdSubcategoria(),
                            subcategoria.getNombre(),
                            subcategoria.getDescripcion(),
                            colecciones
                    );

            subcategoriasResponse.add(subcategoriaResponse);
        }

        return subcategoriasResponse;
    }
}