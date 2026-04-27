package com.cromosdatabase.comun.utiles;

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
}
