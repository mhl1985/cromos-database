package com.cromosdatabase.modelo.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta para mostrar los cromos de una colección junto con
 * la información guardada por el usuario sobre cada uno de ellos.
 *
 * Este DTO se utiliza en la pantalla de edición de cromos
 * dentro de la colección perteneciente al usuario autenticado.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioColeccionCromoResponse {

    /**
     * ID del cromo.
     */
    private Integer idCromo;

    /**
     * Número del cromo dentro de la colección.
     */
    private String numero;

    /**
     * Nombre del cromo.
     */
    private String nombre;

    /**
     * Tipo del cromo.
     */
    private String tipo;

    /**
     * Descripción del cromo.
     */
    private String descripcion;

    /**
     * Indica si el usuario tiene marcado este cromo
     * dentro de su colección.
     */
    private Boolean marcado;

    /**
     * Cantidad total de unidades que el usuario tiene
     * de este cromo.
     */
    private Integer cantidadTotal;

    /**
     * Cantidad de unidades disponibles para intercambio.
     */
    private Integer cantidadIntercambiable;

    /**
     * Observaciones del usuario sobre este cromo.
     */
    private String observaciones;
}