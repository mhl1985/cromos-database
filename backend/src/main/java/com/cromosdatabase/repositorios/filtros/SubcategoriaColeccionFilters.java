package com.cromosdatabase.repositorios.filtros;

import com.cromosdatabase.modelo.entidades.SubcategoriaColeccion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase utilitaria que centraliza la construcción de filtros dinámicos
 * para la entidad SubcategoriaColeccion.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SubcategoriaColeccionFilters {

    /**
     * Filtro por nombre de subcategoría.
     *
     * Realiza una búsqueda parcial (LIKE) sin distinguir entre mayúsculas
     * y minúsculas.
     *
     * Ejemplo: "fut" devuelve coincidencias como "Fútbol".
     *
     * @param nombre texto a buscar dentro del nombre de la subcategoría
     * @return Specification con el filtro aplicado
     */
    public static Specification<SubcategoriaColeccion> byNombre(String nombre) {

        Specification<SubcategoriaColeccion> filtroNombre =
                (root, query, criteriaBuilder) -> {

                    String patronBusqueda = "%" + nombre.toLowerCase() + "%";

                    return criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("nombre")),
                            patronBusqueda
                    );
                };

        return filtroNombre;
    }

    /**
     * Filtro por identificador de categoría.
     *
     * Realiza una coincidencia exacta sobre el id de la categoría
     * a la que pertenece la subcategoría.
     *
     * Se utiliza directamente el campo simple idCategoria de la entidad
     * SubcategoriaColeccion para evitar navegar por la relación completa
     * y evitar joins innecesarios.
     *
     * @param idCategoria identificador de la categoría
     * @return Specification con el filtro aplicado
     */
    public static Specification<SubcategoriaColeccion> byIdCategoria(Integer idCategoria) {

        Specification<SubcategoriaColeccion> filtroIdCategoria =
                (root, query, criteriaBuilder) -> {
                    /*
                     * Se accede directamente al campo "idCategoria" de la entidad
                     * SubcategoriaColeccion.
                     *
                     * Equivale conceptualmente a:
                     * WHERE id_categoria = :idCategoria
                     */
                    return criteriaBuilder.equal(
                            root.get("idCategoria"),
                            idCategoria
                    );
                };

        return filtroIdCategoria;
    }
}