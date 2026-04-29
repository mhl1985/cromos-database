package com.cromosdatabase.modelo.dtos.coleccion;

import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionResumenResponse;
import com.cromosdatabase.modelo.dtos.editorial.EditorialResumenResponse;
import com.cromosdatabase.modelo.dtos.subcategoria.SubcategoriaColeccionResumenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta resumida para una colección.
 *
 * Se utiliza en el endpoint de listado o filtrado de colecciones,
 * devolviendo la información principal necesaria para mostrar cada
 * colección en pantalla.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColeccionResumenResponse {

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
     * País asociado a la colección.
     */
    private String pais;

    /**
     * Descripción de la colección.
     */
    private String descripcion;

    /**
     * URL de la imagen de portada de la colección.
     */
    private String urlImgPortada;
}