package com.cromosdatabase.modelo.mappers;

import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.UsuarioColeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper para convertir la entidad UsuarioColeccion
 * en DTOs de respuesta para las colecciones
 * del usuario autenticado.
 */
@Mapper(
        componentModel = "spring",
        uses = {
                EditorialMapper.class,
                CategoriaColeccionMapper.class,
                SubcategoriaColeccionMapper.class
        }
)
public interface UsuarioColeccionMapper {

    /**
     * Convierte una relación usuario-colección
     * en un DTO resumido para respuesta.
     *
     * @param usuarioColeccion relación usuario-colección
     * @return DTO resumido de colección del usuario
     */
    @Mapping(source = "coleccion.idColeccion", target = "id")
    @Mapping(source = "coleccion.nombre", target = "nombre")
    @Mapping(source = "coleccion.editorial", target = "editorial")
    @Mapping(source = "coleccion.categoria", target = "categoria")
    @Mapping(source = "coleccion.subcategoria", target = "subcategoria")
    @Mapping(source = "coleccion.periodo", target = "periodo")
    @Mapping(source = "coleccion.urlImgPortada", target = "urlImgPortada")
    @Mapping(source = "fechaAgregada", target = "fechaAgregada")
    UsuarioColeccionResumenResponse toResumenResponse(UsuarioColeccion usuarioColeccion);

    /**
     * Convierte una lista de relaciones usuario-colección
     * en una lista de DTOs resumidos.
     *
     * @param usuariosColecciones lista de relaciones usuario-colección
     * @return lista de DTOs resumidos
     */
    List<UsuarioColeccionResumenResponse> toResumenResponseList(List<UsuarioColeccion> usuariosColecciones);
}