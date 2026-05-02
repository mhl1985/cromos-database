package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.paginas.ColeccionPageResponse;
import com.cromosdatabase.modelo.dtos.paginas.PaginaInicioResponse;
import com.cromosdatabase.servicios.PaginaColeccionService;
import com.cromosdatabase.servicios.PaginaInicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para cargar páginas completas del frontend.
 *
 * Estos endpoints no sustituyen a los endpoints REST existentes.
 * Su objetivo es agrupar la información necesaria para pintar
 * pantallas concretas de la aplicación.
 */
@RestController
@RequestMapping("/paginas")
@RequiredArgsConstructor
public class PaginaController {

    /**
     * Servicio de carga de la página de inicio.
     */
    private final PaginaInicioService paginaInicioService;

    /**
     * Servicio de carga de la página de colección.
     */
    private final PaginaColeccionService paginaColeccionService;

    /**
     * Carga la página de inicio.
     *
     * Endpoint público:
     * - Sin token: devuelve información pública.
     * - Con token válido: añade información resumida del usuario autenticado.
     *
     * @return datos necesarios para pintar la página de inicio
     */
    @GetMapping("/inicio")
    public ResponseEntity<PaginaInicioResponse> cargarPaginaInicio() {

        PaginaInicioResponse response = paginaInicioService.cargarPaginaInicio();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Carga la página de una colección.
     *
     * Endpoint público:
     * - Sin token: devuelve información pública de la colección.
     * - Con token válido: Se añade información de los cromos que el usuario
     * tiene de esa colección.
     *
     * @param idColeccion id de la colección
     * @return datos necesarios para pintar la página de colección
     */
    @GetMapping("/colecciones/{idColeccion}")
    public ResponseEntity<ColeccionPageResponse> cargarPaginaColeccion(
            @PathVariable Integer idColeccion) {

        ColeccionPageResponse response =
                paginaColeccionService.cargarPaginaColeccion(idColeccion);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}