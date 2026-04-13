package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionResumenResponse;
import com.cromosdatabase.servicios.CategoriaColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la consulta de categorías de colección.
 */
@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaColeccionController {

    /**
     * Servicio de lógica de negocio de categorías.
     */
    private final CategoriaColeccionService categoriaColeccionService;

    /**
     * Obtiene el listado de DTO´s de categorías aplicando filtros opcionales.
     *
     * @param nombre texto a buscar dentro del nombre de la categoría
     * @return DTO de lista de categorías filtradas en formato resumido
     */
    @GetMapping
    public List<CategoriaColeccionResumenResponse> obtenerCategorias(
            @RequestParam(required = false) String nombre) {

        List<CategoriaColeccionResumenResponse> response =
                categoriaColeccionService.obtenerCategoriasFiltradas(nombre);

        return response;
    }

    /**
     * Obtiene el DTO del detalle de una categoría a partir de su identificador.
     *
     * @param id identificador de la categoría
     * @return DTO del detalle de la categoría
     */
    @GetMapping("/{id}")
    public CategoriaColeccionDetalleResponse obtenerCategoriaPorId(
            @PathVariable Integer id) {

        CategoriaColeccionDetalleResponse response =
                categoriaColeccionService.obtenerCategoriaPorId(id);

        return response;
    }
}