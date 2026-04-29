package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.EditorialNoEncontradaException;
import com.cromosdatabase.comun.utiles.FiltroUtils;
import com.cromosdatabase.modelo.dtos.editorial.EditorialDetalleResponse;
import com.cromosdatabase.modelo.dtos.editorial.EditorialResumenResponse;
import com.cromosdatabase.modelo.entidades.Editorial;
import com.cromosdatabase.modelo.mappers.EditorialMapper;
import com.cromosdatabase.repositorios.EditorialRepository;
import com.cromosdatabase.repositorios.filtros.EditorialFilters;
import com.cromosdatabase.servicios.EditorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de editoriales.
 *
 * Centraliza la lógica de negocio relacionada con:
 * - obtención de editoriales filtradas
 * - obtención del detalle de una editorial por id
 */
@Service
@RequiredArgsConstructor
public class EditorialServiceImpl implements EditorialService {

    /**
     * Repositorio de acceso a datos de editoriales.
     */
    private final EditorialRepository editorialRepository;

    /**
     * Mapper de conversión entre entidad Editorial y DTOs de respuesta.
     */
    private final EditorialMapper editorialMapper;

    /**
     * Obtiene el listado de editoriales aplicando los filtros recibidos.
     *
     * Todos los filtros son opcionales y pueden combinarse entre sí.
     *
     * Reglas de búsqueda:
     * - nombre: búsqueda parcial sin distinguir mayúsculas/minúsculas
     *
     * @param nombre texto a buscar dentro del nombre de la editorial
     * @return DTO de lista de editoriales que cumplen los filtros en formato resumido
     */
    @Override
    @Transactional(readOnly = true)
    public List<EditorialResumenResponse> obtenerEditorialesFiltradas(String nombre) {

        String nombreNormalizado = FiltroUtils.normalizarTextoFiltro(nombre);

        Specification<Editorial> filtroCompleto = null;

        // Filtro por nombre.
        if (nombreNormalizado != null) {
            filtroCompleto = FiltroUtils.combinarFiltros(
                    filtroCompleto,
                    EditorialFilters.byNombre(nombreNormalizado)
            );
        }

        /*
         * Ejecución final:
         * - Si no hay filtros: devuelve todas las editoriales
         * - Si hay filtros: los aplica y devuelve las editoriales resultantes
         */
        List<Editorial> editoriales;

        if (filtroCompleto == null) {
            editoriales = editorialRepository.findAll();
        } else {
            editoriales = editorialRepository.findAll(filtroCompleto);
        }

        // Mapeo a DTO
        List<EditorialResumenResponse> response =
                editorialMapper.toResumenResponseList(editoriales);

        return response;
    }

    /**
     * Obtiene una editorial por su identificador.
     *
     * Si no existe ninguna editorial con el id indicado, se lanza una
     * excepción controlada para que sea gestionada por el
     * GlobalExceptionHandler.
     *
     * @param idEditorial identificador de la editorial
     * @return DTO de editorial encontrada en formato detallado
     */
    @Override
    @Transactional(readOnly = true)
    public EditorialDetalleResponse obtenerEditorialPorId(Integer idEditorial) {

        Editorial editorial = editorialRepository.findById(idEditorial)
                .orElseThrow(() -> new EditorialNoEncontradaException(
                        "No existe ninguna editorial con id " + idEditorial
                ));

        // Mapeo a DTO
        EditorialDetalleResponse response =
                editorialMapper.toDetalleResponse(editorial);

        return response;
    }

}