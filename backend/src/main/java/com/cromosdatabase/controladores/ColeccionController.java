package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionResumenResponse;
import com.cromosdatabase.servicios.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la consulta de colecciones.
 */
@RestController
@RequestMapping("/colecciones")
@RequiredArgsConstructor
public class ColeccionController {

    /**
     * Servicio de lógica de negocio de colecciones.
     */
    private final ColeccionService coleccionService;

    /**
     * Obtiene el listado de DTO´s de colecciones aplicando filtros opcionales.
     *
     * @param nombre texto a buscar dentro del nombre de la colección
     * @param idCategoria identificador de la categoría
     * @param idEditorial identificador de la editorial
     * @param idSubcategoria identificador de la subcategoría
     * @param periodo texto a buscar dentro del periodo de la colección
     * @return DTO de lista de colecciones filtradas en formato resumido
     */
    @GetMapping
    public List<ColeccionResumenResponse> obtenerColecciones(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(required = false) Integer idEditorial,
            @RequestParam(required = false) Integer idSubcategoria,
            @RequestParam(required = false) String periodo) {

        List<ColeccionResumenResponse> response =
                coleccionService.obtenerColeccionesFiltradas(
                        nombre,
                        idCategoria,
                        idEditorial,
                        idSubcategoria,
                        periodo
                );

        return response;
    }

    /**
     * Obtiene el DTO del detalle de una colección a partir de su identificador.
     *
     * @param id identificador de la colección
     * @return DTO del detalle de la colección
     */
    @GetMapping("/{id}")
    public ColeccionDetalleResponse obtenerColeccionPorId(
            @PathVariable Integer id) {

        ColeccionDetalleResponse response =
                coleccionService.obtenerColeccionPorId(id);

        return response;
    }
}