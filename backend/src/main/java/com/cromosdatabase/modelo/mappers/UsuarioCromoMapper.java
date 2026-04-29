package com.cromosdatabase.modelo.mappers;

import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoResponse;
import com.cromosdatabase.modelo.entidades.Cromo;
import com.cromosdatabase.modelo.entidades.UsuarioCromo;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir entidades relacionadas con usuario-cromo
 * en DTOs de respuesta.
 */
@Mapper(componentModel = "spring")
public interface UsuarioCromoMapper {

    /**
     * Convierte un cromo y su información asociada del usuario
     * en un DTO de respuesta para la pantalla de edición
     * de cromos de una colección.
     *
     * Si no existe relación en usuarios_cromos para ese cromo,
     * se devolverá el DTO con el cromo desmarcado y con los
     * valores por defecto correspondientes.
     *
     * @param cromo entidad cromo.
     * @param usuarioCromo relación usuario-cromo, o null si no existe.
     * @return DTO de respuesta con la información del cromo
     * y los datos guardados por el usuario o los datos por
     * defecto si no existe la relación usuario-cromo.
     */
    default UsuarioColeccionCromoResponse toUsuarioColeccionCromoResponse(
            Cromo cromo,
            UsuarioCromo usuarioCromo) {

        UsuarioColeccionCromoResponse dto = new UsuarioColeccionCromoResponse();

        dto.setIdCromo(cromo.getIdCromo());
        dto.setNumero(cromo.getNumero());
        dto.setNombre(cromo.getNombre());
        dto.setTipo(cromo.getTipo());
        dto.setDescripcion(cromo.getDescripcion());

        // Si el usuario NO tiene el cromo...
        if (usuarioCromo == null) {
            dto.setMarcado(false);
            dto.setCantidadTotal(0);
            dto.setCantidadIntercambiable(0);
            dto.setObservaciones(null);

        // Si el usuario SI tiene el cromo...
        } else {
            dto.setMarcado(true);
            dto.setCantidadTotal(usuarioCromo.getCantidadTotal());
            dto.setCantidadIntercambiable(usuarioCromo.getCantidadIntercambiable());
            dto.setObservaciones(usuarioCromo.getObservaciones());
        }

        return dto;
    }
}