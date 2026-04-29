package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionResumenResponse;
import com.cromosdatabase.servicios.UsuarioColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar las colecciones
 * del usuario autenticado.
 */
@RestController
@RequestMapping("/mis-colecciones")
@RequiredArgsConstructor
public class UsuarioColeccionController {

    /**
     * Servicio de lógica de negocio de la relación usuario-colección.
     */
    private final UsuarioColeccionService usuarioColeccionService;

    /**
     * Obtiene el listado de colecciones asociadas
     * al usuario autenticado.
     *
     * @return listado de colecciones del usuario autenticado.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioColeccionResumenResponse>> obtenerColeccionesUsuarioAutenticado() {

        List<UsuarioColeccionResumenResponse> response =
                usuarioColeccionService.obtenerColeccionesUsuarioAutenticado();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Añade una colección al usuario autenticado.
     *
     * @param idColeccion id de la colección.
     * @return respuesta sin contenido.
     */
    @PostMapping("/{idColeccion}")
    public ResponseEntity<Void> anadirColeccionUsuarioAutenticado(
            @PathVariable Integer idColeccion) {

        usuarioColeccionService.anadirColeccionUsuarioAutenticado(idColeccion);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Elimina una colección del usuario autenticado.
     *
     * Además de eliminar la relación usuario-colección,
     * también elimina las relaciones usuario-cromo
     * correspondientes a los cromos de esa colección.
     *
     * @param idColeccion id de la colección.
     * @return respuesta sin contenido.
     */
    @DeleteMapping("/{idColeccion}")
    public ResponseEntity<Void> eliminarColeccionUsuarioAutenticado(
            @PathVariable Integer idColeccion) {

        usuarioColeccionService.eliminarColeccionUsuarioAutenticado(idColeccion);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}