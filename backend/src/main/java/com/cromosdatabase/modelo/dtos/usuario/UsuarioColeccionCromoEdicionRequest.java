package com.cromosdatabase.modelo.dtos.usuario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de petición para editar el estado de un cromo
 * dentro de una colección del usuario autenticado.
 * Cada instancia representa un cromo de la colección
 * perteneciente al usuario enviado desde frontend.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioColeccionCromoEdicionRequest {

    /**
     * Id del cromo.
     */
    @NotNull(message = "El id del cromo es obligatorio.")
    private Integer idCromo;

    /**
     * Indica si el usuario tiene marcado el cromo.
     */
    @NotNull(message = "El campo marcado es obligatorio.")
    private Boolean marcado;

    /**
     * Cantidad total de unidades que el usuario tiene
     * de este cromo.
     * No puede ser negativa.
     */
    @NotNull(message = "La cantidad total es obligatoria.")
    @Min(value = 0, message = "La cantidad total no puede ser negativa.")
    private Integer cantidadTotal;

    /**
     * Cantidad de unidades disponibles para intercambio.
     * No puede ser negativa.
     */
    @NotNull(message = "La cantidad intercambiable es obligatoria.")
    @Min(value = 0, message = "La cantidad intercambiable no puede ser negativa.")
    private Integer cantidadIntercambiable;

    /**
     * Observaciones del usuario sobre este cromo.
     * No pueden superar los 255 caracteres.
     */
    @Size(max = 255, message = "Las observaciones no pueden superar los 255 caracteres.")
    private String observaciones;
}