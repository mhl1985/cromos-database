package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionResumenResponse;
import com.cromosdatabase.servicios.SubcategoriaColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la consulta de subcategorías de colección.
 */
@RestController
@RequestMapping("/subcategorias")
@RequiredArgsConstructor
public class SubcategoriaColeccionController {

    /**
     * Servicio de lógica de negocio de subcategorías.
     */
    private final SubcategoriaColeccionService subcategoriaColeccionService;

    /**
     * Obtiene el listado de DTO´s de subcategorías aplicando filtros opcionales.
     *
     * @param nombre texto a buscar dentro del nombre de la subcategoría
     * @param idCategoria identificador de la categoría a la que pertenece la subcategoría
     * @return DTO de lista de subcategorías filtradas en formato resumido
     */
    @GetMapping
    public List<SubcategoriaColeccionResumenResponse> obtenerSubcategorias(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer idCategoria) {

        List<SubcategoriaColeccionResumenResponse> response =
                subcategoriaColeccionService.obtenerSubcategoriasFiltradas(
                        nombre,
                        idCategoria
                );

        return response;
    }

    /**
     * Obtiene el DTO del detalle de una subcategoría a partir de su identificador.
     *
     * @param id identificador de la subcategoría
     * @return DTO del detalle de la subcategoría
     */
    @GetMapping("/{id}")
    public SubcategoriaColeccionDetalleResponse obtenerSubcategoriaPorId(
            @PathVariable Integer id) {

        SubcategoriaColeccionDetalleResponse response =
                subcategoriaColeccionService.obtenerSubcategoriaPorId(id);

        return response;
    }
}