package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.app.seguridad.UsuarioAuth;
import com.cromosdatabase.comun.excepciones.CromoNoEncontradoException;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoDetalleResponse;
import com.cromosdatabase.modelo.dtos.paginas.PaginaCromoResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioPoseedorCromoResponse;
import com.cromosdatabase.modelo.entidades.Coleccion;
import com.cromosdatabase.modelo.entidades.Cromo;
import com.cromosdatabase.modelo.entidades.Usuario;
import com.cromosdatabase.modelo.entidades.UsuarioCromo;
import com.cromosdatabase.modelo.entidades.UsuarioCromoId;
import com.cromosdatabase.modelo.mappers.ColeccionMapper;
import com.cromosdatabase.modelo.mappers.CromoMapper;
import com.cromosdatabase.modelo.mappers.UsuarioCromoMapper;
import com.cromosdatabase.repositorios.CromoRepository;
import com.cromosdatabase.repositorios.UsuarioColeccionRepository;
import com.cromosdatabase.repositorios.UsuarioCromoRepository;
import com.cromosdatabase.servicios.PaginaCromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para cargar la página de un cromo.
 *
 * Este servicio agrupa información pública del cromo y de su colección,
 * junto con información personalizada del usuario autenticado si existe.
 */
@Service
@RequiredArgsConstructor
public class PaginaCromoServiceImpl implements PaginaCromoService {

    /**
     * Repositorio de cromos.
     */
    private final CromoRepository cromoRepository;

    /**
     * Repositorio de relaciones usuario-colección.
     */
    private final UsuarioColeccionRepository usuarioColeccionRepository;

    /**
     * Repositorio de relaciones usuario-cromo.
     */
    private final UsuarioCromoRepository usuarioCromoRepository;

    /**
     * Mapper de colecciones.
     */
    private final ColeccionMapper coleccionMapper;

    /**
     * Mapper de cromos.
     */
    private final CromoMapper cromoMapper;

    /**
     * Mapper de cromos del usuario.
     */
    private final UsuarioCromoMapper usuarioCromoMapper;

    /**
     * Carga todos los datos necesarios para pintar la página
     * de un cromo.
     *
     * @param idCromo id del cromo
     * @return DTO con los datos de la página de cromo
     */
    @Override
    @Transactional(readOnly = true)
    public PaginaCromoResponse cargarPaginaCromo(Integer idCromo) {

        // Obtenemos el cromo solicitado.
        // Si no existe, se lanza excepción controlada.
        Cromo cromoEntidad = cromoRepository.findById(idCromo)
                .orElseThrow(() -> new CromoNoEncontradoException(
                        "No existe ningún cromo con id " + idCromo
                ));

        // Obtenemos la colección padre del cromo.
        Coleccion coleccionEntidad = cromoEntidad.getColeccion();

        // Obtenemos el id del usuario autenticado si existe.
        Integer idUsuarioAutenticado = obtenerIdUsuarioAutenticado();

        // Obtenemos la información del cromo del usuario, si corresponde.
        UsuarioColeccionCromoResponse cromoUsuario =
                obtenerCromoUsuarioSiCorresponde(
                        idUsuarioAutenticado,
                        coleccionEntidad.getIdColeccion(),
                        cromoEntidad
                );

        // Obtenemos los usuarios que tienen este cromo disponible para intercambio.
        List<UsuarioPoseedorCromoResponse> usuariosPoseedores =
                obtenerUsuariosPoseedores(idCromo, idUsuarioAutenticado);

        // Mapeo de entidades a DTOs.
        ColeccionDetalleResponse coleccion =
                coleccionMapper.toDetalleResponse(coleccionEntidad);
        CromoDetalleResponse cromo =
                cromoMapper.toDetalleResponse(cromoEntidad);

        // Construimos la respuesta final.
        PaginaCromoResponse response = new PaginaCromoResponse(
                coleccion,
                cromo,
                cromoUsuario,
                usuariosPoseedores
        );

        return response;
    }

    /**
     * Obtiene el id del usuario autenticado.
     *
     * Si la petición no tiene usuario autenticado, devuelve null.
     *
     * @return id del usuario autenticado o null
     */
    private Integer obtenerIdUsuarioAutenticado() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Si no hay autenticación, no hay usuario autenticado.
        if (authentication == null) {
            return null;
        }

        // Si Spring Security ha marcado la petición como anónima, no hay usuario real.
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        // Validamos que el principal sea nuestro UsuarioAuth.
        if (!(authentication.getPrincipal() instanceof UsuarioAuth usuarioAuth)) {
            return null;
        }

        return usuarioAuth.getIdUsuario();
    }

    /**
     * Obtiene la información del cromo para el usuario autenticado.
     *
     * Se devuelve null en estos casos:
     * - si no hay usuario autenticado
     * - si el usuario no tiene agregada la colección del cromo
     * - si el usuario tiene la colección, pero no tiene este cromo
     *
     * @param idUsuario id del usuario autenticado
     * @param idColeccion id de la colección del cromo
     * @param cromo entidad cromo
     * @return información del cromo del usuario o null
     */
    private UsuarioColeccionCromoResponse obtenerCromoUsuarioSiCorresponde(
            Integer idUsuario,
            Integer idColeccion,
            Cromo cromo) {

        // Si no hay usuario autenticado, no hay información personalizada.
        if (idUsuario == null) {
            return null;
        }

        // Si el usuario no tiene agregada la colección,
        // no se devuelve información personalizada del cromo.
        boolean coleccionAsociadaUsuario =
                usuarioColeccionRepository.existsByUsuario_IdUsuarioAndColeccion_IdColeccion(
                        idUsuario,
                        idColeccion
                );

        if (!coleccionAsociadaUsuario) {
            return null;
        }

        UsuarioCromoId usuarioCromoId = new UsuarioCromoId(
                idUsuario,
                cromo.getIdCromo()
        );

        Optional<UsuarioCromo> usuarioCromoOptional =
                usuarioCromoRepository.findById(usuarioCromoId);

        // Si el usuario tiene la colección, pero no tiene este cromo,
        // devolvemos null.
        if (usuarioCromoOptional.isEmpty()) {
            return null;
        }

        UsuarioCromo usuarioCromo = usuarioCromoOptional.get();

        // Si por algún motivo la cantidad total no es positiva,
        // no lo consideramos cromo en posesión.
        if (usuarioCromo.getCantidadTotal() == null || usuarioCromo.getCantidadTotal() <= 0) {
            return null;
        }

        return usuarioCromoMapper.toUsuarioColeccionCromoResponse(cromo, usuarioCromo);
    }

    /**
     * Obtiene los usuarios que tienen el cromo disponible para intercambio.
     *
     * Se excluye de la lista el usuario autenticado actual, si existe.
     *
     * @param idCromo id del cromo
     * @param idUsuarioAutenticado id del usuario autenticado o null
     * @return lista de usuarios poseedores
     */
    private List<UsuarioPoseedorCromoResponse> obtenerUsuariosPoseedores(
            Integer idCromo,
            Integer idUsuarioAutenticado) {

        List<UsuarioCromo> usuariosCromos =
                usuarioCromoRepository.findByCromo_IdCromoAndCantidadIntercambiableGreaterThan(
                        idCromo,
                        0
                );

        List<UsuarioPoseedorCromoResponse> usuariosPoseedores = new ArrayList<>();

        for (UsuarioCromo usuarioCromo : usuariosCromos) {

            Usuario usuario = usuarioCromo.getUsuario();

            // No incluimos al usuario autenticado actual.
            if (idUsuarioAutenticado != null
                    && idUsuarioAutenticado.equals(usuario.getIdUsuario())) {
                continue;
            }

            UsuarioPoseedorCromoResponse dto = new UsuarioPoseedorCromoResponse(
                    usuario.getIdUsuario(),
                    usuario.getNombreMostrar(),
                    usuario.getEmail()
            );

            usuariosPoseedores.add(dto);
        }

        return usuariosPoseedores;
    }
}