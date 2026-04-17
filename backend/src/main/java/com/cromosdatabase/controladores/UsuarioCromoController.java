package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionListaCromosEdicionRequest;
import com.cromosdatabase.servicios.UsuarioCromoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar los cromos de las colecciones
 * pertenecientes al usuario autenticado.
 */
@RestController
@RequestMapping("/mis-colecciones")
@RequiredArgsConstructor
public class UsuarioCromoController {

    /**
     * Servicio de lógica de negocio de la relación usuario-cromo.
     */
    private final UsuarioCromoService usuarioCromoService;

    /**
     * Obtiene el listado completo de cromos de una colección
     * asociada al usuario autenticado, incluyendo la información
     * guardada por el usuario sobre cada cromo.
     *
     * @param idColeccion id de la colección.
     * @return listado de cromos de la colección con la información
     * guardada por el usuario sobre cada cromo.
     */
    @GetMapping("/{idColeccion}/cromos")
    public ResponseEntity<List<UsuarioColeccionCromoResponse>> obtenerCromosColeccionUsuario(
            @PathVariable Integer idColeccion) {

        List<UsuarioColeccionCromoResponse> response =
                usuarioCromoService.obtenerCromosColeccionUsuario(idColeccion);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Actualiza de forma masiva los cromos de una colección
     * asociada al usuario autenticado.
     *
     * @param idColeccion id de la colección.
     * @param request lista de cromos recibida desde frontend.
     * @return listado actualizado de cromos de la colección
     * perteneciente al usuario.
     */
    @PutMapping("/{idColeccion}/cromos")
    public ResponseEntity<List<UsuarioColeccionCromoResponse>> actualizarCromosColeccionUsuario(
            @PathVariable Integer idColeccion,
            @Valid @RequestBody UsuarioColeccionListaCromosEdicionRequest request) {

        List<UsuarioColeccionCromoResponse> response =
                usuarioCromoService.actualizarCromosColeccionUsuario(idColeccion, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}