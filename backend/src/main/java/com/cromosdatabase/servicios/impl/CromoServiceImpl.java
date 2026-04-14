package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.CromoNoEncontradoException;
import com.cromosdatabase.modelo.dtos.cromo.CromoDetalleResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoResumenResponse;
import com.cromosdatabase.modelo.entidades.Cromo;
import com.cromosdatabase.modelo.mappers.CromoMapper;
import com.cromosdatabase.repositorios.CromoRepository;
import com.cromosdatabase.repositorios.filtros.CromoFilters;
import com.cromosdatabase.servicios.CromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de cromos.
 *
 * Centraliza la lógica de negocio relacionada con:
 * - obtención de cromos filtrados
 * - obtención del detalle de un cromo por id
 */
@Service
@RequiredArgsConstructor
public class CromoServiceImpl implements CromoService {

    /**
     * Repositorio de acceso a datos de cromos.
     */
    private final CromoRepository cromoRepository;

    /**
     * Mapper de conversión entre entidad Cromo y DTOs de respuesta.
     */
    private final CromoMapper cromoMapper;

    /**
     * Obtiene el listado de cromos aplicando los filtros recibidos.
     *
     * Todos los filtros son opcionales y pueden combinarse entre sí.
     *
     * Reglas de búsqueda:
     * - idColeccion: coincidencia exacta
     * - numero: búsqueda parcial sin distinguir mayúsculas/minúsculas
     * - nombre: búsqueda parcial sin distinguir mayúsculas/minúsculas
     * - tipo: búsqueda parcial sin distinguir mayúsculas/minúsculas
     *
     * @param idColeccion identificador de la colección
     * @param numero texto a buscar dentro del número del cromo
     * @param nombre texto a buscar dentro del nombre del cromo
     * @param tipo texto a buscar dentro del tipo del cromo
     * @return DTO de lista de cromos que cumplen los filtros en formato resumido
     */
    @Override
    @Transactional(readOnly = true)
    public List<CromoResumenResponse> obtenerCromosFiltrados(Integer idColeccion,
                                                             String numero,
                                                             String nombre,
                                                             String tipo) {

        /*
         * Se normalizan los filtros de texto antes de construir la consulta.
         * Se eliminan espacios sobrantes al principio y al final.
         * Se convierten cadenas vacías en null.
         */
        String numeroNormalizado = normalizarTextoFiltro(numero);
        String nombreNormalizado = normalizarTextoFiltro(nombre);
        String tipoNormalizado = normalizarTextoFiltro(tipo);

        Specification<Cromo> filtroCompleto = null;

        // Filtro por colección.
        if (idColeccion != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    CromoFilters.byIdColeccion(idColeccion));
        }

        // Filtro por número.
        if (numeroNormalizado != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    CromoFilters.byNumero(numeroNormalizado));
        }

        // Filtro por nombre.
        if (nombreNormalizado != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    CromoFilters.byNombre(nombreNormalizado));
        }

        // Filtro por tipo.
        if (tipoNormalizado != null) {
            filtroCompleto = combinarFiltros(filtroCompleto,
                    CromoFilters.byTipo(tipoNormalizado));
        }

        /*
         * Ejecución final:
         * - Si no hay filtros: devuelve todos los cromos
         * - Si hay filtros: los aplica y devuelve los cromos resultantes
         */
        List<Cromo> cromos;

        if (filtroCompleto == null) {
            cromos = cromoRepository.findAll();
        } else {
            cromos = cromoRepository.findAll(filtroCompleto);
        }

        // Mapeo a DTO
        List<CromoResumenResponse> response =
                cromoMapper.toResumenResponseList(cromos);

        return response;
    }

    /**
     * Obtiene un cromo por su identificador.
     *
     * Si no existe ningún cromo con el id indicado, se lanza una
     * excepción controlada para que sea gestionada por el
     * GlobalExceptionHandler.
     *
     * @param idCromo identificador del cromo
     * @return DTO del cromo encontrado en formato detallado
     */
    @Override
    @Transactional(readOnly = true)
    public CromoDetalleResponse obtenerCromoPorId(Integer idCromo) {

        Cromo cromo = cromoRepository.findById(idCromo)
                .orElseThrow(() -> new CromoNoEncontradoException(
                        "No existe ningún cromo con id " + idCromo
                ));

        // Mapeo a DTO
        CromoDetalleResponse response =
                cromoMapper.toDetalleResponse(cromo);

        return response;
    }

    /**
     * Normaliza un texto recibido como filtro.
     *
     * Reglas:
     * - Si el valor es null, devuelve null
     * - Elimina espacios al inicio y al final
     * - Si tras el trim queda vacío, devuelve null
     *
     * @param texto texto recibido como filtro
     * @return texto normalizado o null si no aporta valor para filtrar
     */
    private String normalizarTextoFiltro(String texto) {

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
     *
     * @param filtroBase filtro acumulado hasta el momento
     * @param nuevoFiltro nuevo filtro a añadir
     * @return filtro combinado
     */
    private Specification<Cromo> combinarFiltros(Specification<Cromo> filtroBase,
                                                 Specification<Cromo> nuevoFiltro) {
        if (filtroBase == null) {
            return nuevoFiltro;
        }

        return filtroBase.and(nuevoFiltro);
    }
}