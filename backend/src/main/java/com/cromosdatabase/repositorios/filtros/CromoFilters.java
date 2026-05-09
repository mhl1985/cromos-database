package com.cromosdatabase.repositorios.filtros;

import com.cromosdatabase.comun.utiles.FiltroUtils;
import com.cromosdatabase.modelo.entidades.Cromo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase utilitaria que centraliza la construcción de filtros dinámicos
 * para la entidad Cromo.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Constructor privado vacío.
public final class CromoFilters {

    /**
     * Filtro por identificador de colección.
     *
     * Realiza una coincidencia exacta sobre el id de la colección.
     *
     * @param idColeccion identificador de la colección
     * @return Specification con el filtro aplicado
     */
    public static Specification<Cromo> byIdColeccion(Integer idColeccion) {

        Specification<Cromo> filtroIdColeccion =
                (root, query, criteriaBuilder) -> {
                    /*
                     * Se accede al atributo "coleccion" de la entidad Cromo,
                     * que es una relación @ManyToOne.
                     *
                     * Después se accede al campo "idColeccion" dentro de esa entidad.
                     *
                     * Equivale conceptualmente a:
                     * WHERE id_coleccion = :idColeccion
                     */
                    return criteriaBuilder.equal(
                            root.get("coleccion").get("idColeccion"),
                            idColeccion
                    );
                };

        return filtroIdColeccion;
    }

    /**
     * Filtro por número de cromo.
     *
     * Realiza una búsqueda parcial (LIKE) sin distinguir entre mayúsculas
     * y minúsculas.
     *
     * Ejemplo: "10" devuelve coincidencias como "10", "110" o "ESP10".
     *
     * @param numero texto a buscar dentro del número del cromo
     * @return Specification con el filtro aplicado
     */
    public static Specification<Cromo> byNumero(String numero) {

        return FiltroUtils.crearFiltroParaTextoLikeIgnoreCase("numero", numero);
    }

    /**
     * Filtro por nombre de cromo.
     *
     * Realiza una búsqueda parcial (LIKE) sin distinguir entre mayúsculas
     * y minúsculas.
     *
     * Ejemplo: "messi" devuelve coincidencias como "Messi", "MESSI" o "Leo Messi".
     *
     * @param nombre texto a buscar dentro del nombre del cromo
     * @return Specification con el filtro aplicado
     */
    public static Specification<Cromo> byNombre(String nombre) {

        return FiltroUtils.crearFiltroParaTextoLikeIgnoreCase("nombre", nombre);
    }

    /**
     * Filtro por tipo de cromo.
     *
     * Realiza una búsqueda parcial (LIKE) sin distinguir entre mayúsculas
     * y minúsculas.
     *
     * Ejemplo: "especial" devuelve coincidencias como "Especial", "especial"
     * o "Edición especial".
     *
     * @param tipo texto a buscar dentro del tipo del cromo
     * @return Specification con el filtro aplicado
     */
    public static Specification<Cromo> byTipo(String tipo) {

        return FiltroUtils.crearFiltroParaTextoLikeIgnoreCase("tipo", tipo);
    }
}