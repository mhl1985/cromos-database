package com.cromosdatabase.modelo.dtos.cromo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta detallada para un cromo.
 *
 * Se utiliza en el endpoint de detalle de cromo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CromoDetalleResponse {

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
     * Descripción del cromo.
     */
    private String descripcion;

    /**
     * URL de la imagen delantera del cromo.
     */
    private String urlImgDelantera;

    /**
     * URL de la imagen trasera del cromo.
     */
    private String urlImgTrasera;

    /**
     * Identificador de la colección a la que pertenece el cromo.
     */
    private Integer idColeccion;

    /**
     * Nombre de la colección a la que pertenece el cromo.
     */
    private String nombreColeccion;
}