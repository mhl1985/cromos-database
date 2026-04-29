package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.cromo.CromoDetalleResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoResumenResponse;
import com.cromosdatabase.servicios.CromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la consulta de cromos.
 */
@RestController
@RequestMapping("/cromos")
@RequiredArgsConstructor
public class CromoController {

    /**
     * Servicio de lógica de negocio de cromos.
     */
    private final CromoService cromoService;

    /**
     * Obtiene el listado de DTO´s de cromos aplicando filtros opcionales.
     *
     * @param idColeccion identificador de la colección
     * @param numero texto a buscar dentro del número del cromo
     * @param nombre texto a buscar dentro del nombre del cromo
     * @param tipo texto a buscar dentro del tipo del cromo
     * @return DTO de lista de cromos filtrados en formato resumido
     */
    @GetMapping
    public List<CromoResumenResponse> obtenerCromos(
            @RequestParam(required = false) Integer idColeccion,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipo) {

        List<CromoResumenResponse> response =
                cromoService.obtenerCromosFiltrados(
                        idColeccion,
                        numero,
                        nombre,
                        tipo
                );

        return response;
    }

    /**
     * Obtiene el DTO del detalle de un cromo a partir de su identificador.
     *
     * @param id identificador del cromo
     * @return DTO del detalle del cromo
     */
    @GetMapping("/{id}")
    public CromoDetalleResponse obtenerCromoPorId(
            @PathVariable Integer id) {

        CromoDetalleResponse response =
                cromoService.obtenerCromoPorId(id);

        return response;
    }
}