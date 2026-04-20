package com.cromosdatabase.modelo.dtos.usuario;

import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionResumenResponse;
import com.cromosdatabase.modelo.dtos.editorial.EditorialResumenResponse;
import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionResumenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta resumida para mostrar
 * las colecciones asociadas al usuario autenticado.
 *
 * Incluye la información principal de la colección
 * y la fecha en la que el usuario la añadió.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioColeccionResumenResponse {

    /**
     * Identificador único de la colección.
     */
    private Integer id;

    /**
     * Nombre de la colección.
     */
    private String nombre;

    /**
     * Editorial asociada a la colección.
     */
    private EditorialResumenResponse editorial;

    /**
     * Categoría principal de la colección.
     */
    private CategoriaColeccionResumenResponse categoria;

    /**
     * Subcategoría asociada a la colección.
     */
    private SubcategoriaColeccionResumenResponse subcategoria;

    /**
     * Periodo, año o rango asociado a la colección.
     */
    private String periodo;

    /**
     * URL de la imagen de portada de la colección.
     */
    private String urlImgPortada;

    /**
     * Fecha en la que el usuario añadió la colección.
     */
    private LocalDateTime fechaAgregada;
}