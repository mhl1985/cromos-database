package com.cromosdatabase.repositorios.filtros;

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

        Specification<Cromo> filtroNumero =
                (root, query, criteriaBuilder) -> {
                    /*
                     * Se aplica una búsqueda parcial sobre el campo "numero".
                     *
                     * Se convierte tanto el valor del campo como el texto recibido
                     * a minúsculas para evitar diferencias entre mayúsculas/minúsculas.
                     *
                     * Equivale conceptualmente a:
                     * WHERE LOWER(numero) LIKE '%valor%'
                     */
                    String patronBusqueda = "%" + numero.toLowerCase() + "%";

                    return criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("numero")),
                            patronBusqueda
                    );
                };

        return filtroNumero;
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

        Specification<Cromo> filtroNombre =
                (root, query, criteriaBuilder) -> {
                    /*
                     * Se aplica una búsqueda parcial sobre el campo "nombre".
                     *
                     * Se convierte tanto el valor del campo como el texto recibido
                     * a minúsculas para evitar diferencias entre mayúsculas/minúsculas.
                     *
                     * Equivale conceptualmente a:
                     * WHERE LOWER(nombre) LIKE '%valor%'
                     */
                    String patronBusqueda = "%" + nombre.toLowerCase() + "%";

                    return criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("nombre")),
                            patronBusqueda
                    );
                };

        return filtroNombre;
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

        Specification<Cromo> filtroTipo =
                (root, query, criteriaBuilder) -> {
                    /*
                     * Se aplica una búsqueda parcial sobre el campo "tipo".
                     *
                     * Se convierte tanto el valor del campo como el texto recibido
                     * a minúsculas para evitar diferencias entre mayúsculas/minúsculas.
                     *
                     * Equivale conceptualmente a:
                     * WHERE LOWER(tipo) LIKE '%valor%'
                     */
                    String patronBusqueda = "%" + tipo.toLowerCase() + "%";

                    return criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("tipo")),
                            patronBusqueda
                    );
                };

        return filtroTipo;
    }
}