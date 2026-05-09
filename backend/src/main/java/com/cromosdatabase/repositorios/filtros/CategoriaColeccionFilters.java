package com.cromosdatabase.repositorios.filtros;

import com.cromosdatabase.comun.utiles.FiltroUtils;
import com.cromosdatabase.modelo.entidades.CategoriaColeccion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase utilitaria que centraliza la construcción de filtros dinámicos
 * para la entidad CategoriaColeccion.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoriaColeccionFilters {

    /**
     * Filtro por nombre de categoría.
     *
     * Realiza una búsqueda parcial (LIKE) sin distinguir entre
     * mayúsculas y minúsculas.
     *
     * Ejemplo: "dep" devuelve coincidencias como "Deportes".
     *
     * @param nombre texto a buscar dentro del nombre de la categoría
     * @return Specification con el filtro aplicado
     */
    public static Specification<CategoriaColeccion> byNombre(String nombre) {

        return FiltroUtils.crearFiltroParaTextoLikeIgnoreCase("nombre", nombre);
    }

}
