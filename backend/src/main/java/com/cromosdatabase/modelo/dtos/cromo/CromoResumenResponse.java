package com.cromosdatabase.modelo.dtos.cromo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta resumida para un cromo.
 *
 * Se utiliza en el endpoint de listado o filtrado de cromos,
 * devolviendo la información principal necesaria para mostrar cada
 * cromo en pantalla.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CromoResumenResponse {

    /**
     * Identificador único del cromo.
     */
    private Integer id;

    /**
     * Número identificativo del cromo dentro de su colección.
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
     * URL de la imagen delantera del cromo.
     */
    private String urlImgDelantera;
}