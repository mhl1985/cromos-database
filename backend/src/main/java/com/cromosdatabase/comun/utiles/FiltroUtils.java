package com.cromosdatabase.comun.utiles;

import jakarta.persistence.criteria.Expression;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

/**
 * Utilidades comunes para el tratamiento de filtros.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FiltroUtils {

    /**
     * Normaliza un texto recibido como filtro.
     */
    public static String normalizarTextoFiltro(String texto) {

        if (texto == null) {
            return null;
        }

        String textoNormalizado = texto.trim();

        if (textoNormalizado.isEmpty()) {
            return null;
        }

        return textoNormalizado;
    }

    /**
     * Combina dos filtros mediante una operación AND.
     *
     * Si el filtro base es null, devuelve directamente el nuevo filtro.
     */
    public static <T> Specification<T> combinarFiltros(
            Specification<T> filtroBase,
            Specification<T> nuevoFiltro) {

        if (filtroBase == null) {
            return nuevoFiltro;
        }

        return filtroBase.and(nuevoFiltro);
    }

    /**
     * Crea un filtro de búsqueda parcial de texto (LIKE) sin
     * distinguir entre mayúsculas y minúsculas.
     *
     * El texto recibido debe venir ya normalizado desde el servicio.
     *
     * Equivale a:
     * WHERE LOWER(campo) LIKE '%texto%'
     *
     * @param campo nombre del campo de la entidad sobre el que se aplica el filtro
     * @param texto texto a buscar dentro del campo indicado
     * @return Specification con el filtro LIKE aplicado
     */
    public static <T> Specification<T> crearFiltroParaTextoLikeIgnoreCase(
            String campo,
            String texto) {

        return (root, query, criteriaBuilder) -> {

            // Accedemos al campo de texto indicado de la entidad.
            Expression<String> campoTexto = root.get(campo);

            // Convertimos el valor del campo a minúsculas para ignorar mayúsculas/minúsculas.
            Expression<String> campoTextoEnMinusculas = criteriaBuilder.lower(campoTexto);

            // Construimos el patrón de búsqueda parcial para el LIKE.
            String patronBusqueda = "%" + texto.toLowerCase() + "%";

            // Creamos el filtro final y lo devolvemos:
            // WHERE LOWER(campo) LIKE '%texto%'
            return criteriaBuilder.like(
                    campoTextoEnMinusculas,
                    patronBusqueda
            );
        };
    }
}
