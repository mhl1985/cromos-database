package com.cromosdatabase.repositorios.filtros;

import com.cromosdatabase.modelo.entidades.Editorial;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase utilitaria que centraliza la construcción de filtros dinámicos
 * para la entidad Editorial.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EditorialFilters {

    /**
     * Filtro por nombre de editorial.
     *
     * Realiza una búsqueda parcial (LIKE) sin distinguir entre mayúsculas
     * y minúsculas.
     *
     * Ejemplo: "pan" devuelve coincidencias como "Panini".
     *
     * @param nombre texto a buscar dentro del nombre de la editorial
     * @return Specification con el filtro aplicado
     */
    public static Specification<Editorial> byNombre(String nombre) {

        Specification<Editorial> filtroNombre =
                (root, query, criteriaBuilder) -> {

                    String patronBusqueda = "%" + nombre.toLowerCase() + "%";

                    return criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("nombre")),
                            patronBusqueda
                    );
                };

        return filtroNombre;
    }
}
