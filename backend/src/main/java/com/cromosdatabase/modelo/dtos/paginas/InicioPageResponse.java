package com.cromosdatabase.modelo.dtos.paginas;

import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionResumenResponse;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionResumenResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoResumenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta para cargar la página de inicio.
 *
 * Agrupa en una única respuesta todos los bloques de información
 * que necesita el frontend para pintar la pantalla principal.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InicioPageResponse {

    /**
     * Actividad resumida del usuario autenticado.
     *
     * Si la petición no llega con usuario autenticado,
     * este campo se devolverá a null.
     */
    private ActividadUsuarioInicioResponse actividadUsuario;

    /**
     * Últimas colecciones añadidas al sistema.
     */
    private List<ColeccionResumenResponse> ultimasColecciones;

    /**
     * Categorías principales de colección.
     */
    private List<CategoriaColeccionResumenResponse> categorias;

    /**
     * Últimos cromos añadidos al sistema.
     *
     * Actualmente, se simula mediante ordenación por id descendente.
     */
    private List<CromoResumenResponse> ultimosCromos;

    /**
     * Cromos aleatorios para mostrar en la página de inicio.
     */
    private List<CromoResumenResponse> cromosAleatorios;
}