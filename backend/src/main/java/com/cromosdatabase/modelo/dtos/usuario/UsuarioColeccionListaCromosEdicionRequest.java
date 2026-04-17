package com.cromosdatabase.modelo.dtos.usuario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de petición para actualizar de forma masiva
 * los cromos de una colección del usuario autenticado.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioColeccionListaCromosEdicionRequest {

    /**
     * Lista de cromos de la colección con el estado
     * enviado por frontend.
     */
    @NotEmpty(message = "La lista de cromos no puede estar vacía.")
    @Valid
    private List<UsuarioColeccionCromoEdicionRequest> cromos;
}