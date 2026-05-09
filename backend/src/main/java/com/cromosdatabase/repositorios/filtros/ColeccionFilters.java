package com.cromosdatabase.repositorios.filtros;

import com.cromosdatabase.comun.utiles.FiltroUtils;
import com.cromosdatabase.modelo.entidades.Coleccion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase utilitaria que centraliza la construcción de filtros dinámicos
 * para la entidad Coleccion.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Constructor privado vacío.
public final class ColeccionFilters {

    /**
     * Filtro por nombre de colección.
     *
     * Realiza una búsqueda parcial (LIKE) sin distinguir entre mayúsculas
     * y minúsculas.
     *
     * Ejemplo: "Liga" devuelve coincidencias como "Liga 2002" o "Liga Femenina 2025".
     *
     * @param nombre texto a buscar dentro del nombre de la colección
     * @return Specification con el filtro aplicado
     */
    public static Specification<Coleccion> byNombre(String nombre) {

        return FiltroUtils.crearFiltroParaTextoLikeIgnoreCase("nombre", nombre);
    }

    /**
     * Filtro por identificador de categoría.
     *
     * Realiza una coincidencia exacta sobre el id de la categoría.
     *
     * @param idCategoria identificador de la categoría
     * @return Specification con el filtro aplicado
     */
    public static Specification<Coleccion> byIdCategoria(Integer idCategoria) {

        Specification<Coleccion> filtroIdCategoria =
                (root, query, criteriaBuilder) -> {
            /*
             * Se accede al atributo "categoria" de la entidad Coleccion,
             * que es una relación @ManyToOne.
             *
             * Después se accede al campo "idCategoria" dentro de esa entidad.
             *
             * Equivale conceptualmente a:
             * WHERE id_categoria = :idCategoria
             */
            return criteriaBuilder.equal(
                    root.get("categoria").get("idCategoria"),
                    idCategoria
            );
        };

        return filtroIdCategoria;
    }

    /**
     * Filtro por identificador de editorial.
     *
     * Realiza una coincidencia exacta sobre el id de la editorial.
     *
     * @param idEditorial identificador de la editorial
     * @return Specification con el filtro aplicado
     */
    public static Specification<Coleccion> byIdEditorial(Integer idEditorial) {

        Specification<Coleccion> filtroIdEditorial =
                (root, query, criteriaBuilder) -> {
            /*
             * Se accede al atributo "editorial" de la entidad Coleccion,
             * que es una relación @ManyToOne.
             *
             * Después se accede al campo "idEditorial".
             *
             * Equivale conceptualmente a:
             * WHERE id_editorial = :idEditorial
             */
            return criteriaBuilder.equal(
                    root.get("editorial").get("idEditorial"),
                    idEditorial
            );
        };

        return filtroIdEditorial;
    }

    /**
     * Filtro por identificador de subcategoría.
     *
     * Realiza una coincidencia exacta.
     *
     * Se utiliza directamente el campo simple idSubcategoria de la entidad
     * Coleccion para evitar navegar por la relación completa y evitar joins
     * innecesarios.
     *
     * @param idSubcategoria identificador de la subcategoría
     * @return Specification con el filtro aplicado
     */
    public static Specification<Coleccion> byIdSubcategoria(Integer idSubcategoria) {

        Specification<Coleccion> filtroIdSubcategoria =
                (root, query, criteriaBuilder) -> {
            /*
             * Se accede directamente al campo "idSubcategoria" de la entidad Coleccion.
             *
             * Equivale conceptualmente a:
             * WHERE id_subcategoria = :idSubcategoria
             */
            return criteriaBuilder.equal(
                    root.get("idSubcategoria"),
                    idSubcategoria
            );
        };

        return filtroIdSubcategoria;
    }

    /**
     * Filtro por periodo (año o rango).
     *
     * Realiza una búsqueda parcial (LIKE) sin distinguir entre mayúsculas
     * y minúsculas.
     *
     * Ejemplo: "2026" devuelve coincidencias como: "2026", "2025-2026", "1970-2026"
     *
     * @param periodo texto a buscar dentro del periodo de la colección
     * @return Specification con el filtro aplicado
     */
    public static Specification<Coleccion> byPeriodo(String periodo) {

        return FiltroUtils.crearFiltroParaTextoLikeIgnoreCase("periodo", periodo);
    }
}