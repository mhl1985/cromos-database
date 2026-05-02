package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.app.seguridad.UsuarioAuth;
import com.cromosdatabase.comun.excepciones.ColeccionNoEncontradaException;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionDetalleResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoResumenResponse;
import com.cromosdatabase.modelo.dtos.paginas.PaginaColeccionResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoResponse;
import com.cromosdatabase.modelo.entidades.Coleccion;
import com.cromosdatabase.modelo.entidades.Cromo;
import com.cromosdatabase.modelo.entidades.UsuarioCromo;
import com.cromosdatabase.modelo.mappers.ColeccionMapper;
import com.cromosdatabase.modelo.mappers.CromoMapper;
import com.cromosdatabase.modelo.mappers.UsuarioCromoMapper;
import com.cromosdatabase.repositorios.ColeccionRepository;
import com.cromosdatabase.repositorios.CromoRepository;
import com.cromosdatabase.repositorios.UsuarioColeccionRepository;
import com.cromosdatabase.repositorios.UsuarioCromoRepository;
import com.cromosdatabase.servicios.PaginaColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del servicio para cargar la página de una colección.
 *
 * Este servicio agrupa información pública de la colección y, si existe
 * usuario autenticado, información concreta de sus cromos.
 */
@Service
@RequiredArgsConstructor
public class PaginaColeccionServiceImpl implements PaginaColeccionService {

    /**
     * Repositorio de colecciones.
     */
    private final ColeccionRepository coleccionRepository;

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
     * de una colección.
     *
     * @param idColeccion id de la colección
     * @return DTO con los datos de la página de colección
     */
    @Override
    @Transactional(readOnly = true)
    public PaginaColeccionResponse cargarPaginaColeccion(Integer idColeccion) {

        // Obtenemos los datos de la colección.
        // Si no existe, se lanza excepción controlada.
        Coleccion coleccionEntidad = coleccionRepository.findById(idColeccion)
                .orElseThrow(() -> new ColeccionNoEncontradaException(
                        "No existe ninguna colección con id " + idColeccion
                ));

        // Obtenemos los últimos cromos añadidos a la colección.
        List<Cromo> ultimosCromosEntidad =
                cromoRepository.findTop10ByColeccion_IdColeccionOrderByIdCromoDesc(idColeccion);

        // Obtenemos todos los cromos de la colección.
        List<Cromo> cromosColeccionEntidad =
                cromoRepository.findByColeccion_IdColeccion(idColeccion);

        // Si hay usuario autenticado y tiene cromos de esta colección,
        // se obtiene únicamente esa información.
        List<UsuarioColeccionCromoResponse> cromosUsuario =
                obtenerCromosUsuarioSiEstaAutenticado(idColeccion);

        // Mapeo de entidades a DTOs.
        ColeccionDetalleResponse coleccion =
                coleccionMapper.toDetalleResponse(coleccionEntidad);
        List<CromoResumenResponse> ultimosCromos =
                cromoMapper.toResumenResponseList(ultimosCromosEntidad);
        List<CromoResumenResponse> cromos =
                cromoMapper.toResumenResponseList(cromosColeccionEntidad);

        // Construimos la respuesta final de la página.
        PaginaColeccionResponse response = new PaginaColeccionResponse(
                ultimosCromos,
                coleccion,
                cromos,
                cromosUsuario
        );

        return response;
    }

    /**
     * Obtiene los cromos de esta colección que tiene realmente
     * el usuario autenticado.
     *
     * Se devuelve null en estos casos:
     * - si no hay usuario autenticado
     * - si el usuario no tiene agregada la colección
     * - si el usuario tiene la colección, pero no tiene cromos de ella
     *
     * @param idColeccion id de la colección
     * @return lista de cromos del usuario o null
     */
    private List<UsuarioColeccionCromoResponse> obtenerCromosUsuarioSiEstaAutenticado(
            Integer idColeccion) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Si no hay autenticación, no se devuelve información de usuario.
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

        Integer idUsuario = usuarioAuth.getIdUsuario();

        // Si el usuario no tiene agregada esta colección,
        // no se devuelve información personalizada.
        boolean coleccionAsociadaUsuario =
                usuarioColeccionRepository.existsByUsuario_IdUsuarioAndColeccion_IdColeccion(
                        idUsuario,
                        idColeccion
                );

        if (!coleccionAsociadaUsuario) {
            return null;
        }

        // Obtenemos únicamente los cromos de esta colección que el usuario tiene guardados.
        List<UsuarioCromo> usuarioCromos =
                usuarioCromoRepository.findByUsuario_IdUsuarioAndCromo_Coleccion_IdColeccion(
                        idUsuario,
                        idColeccion
                );

        // Si el usuario no tiene cromos de esta colección, devolvemos null.
        if (usuarioCromos.isEmpty()) {
            return null;
        }

        List<UsuarioColeccionCromoResponse> cromosUsuario = new ArrayList<>();

        // Convertimos cada relación usuario-cromo a DTO de respuesta.
        for (UsuarioCromo usuarioCromo : usuarioCromos) {

            Cromo cromo = usuarioCromo.getCromo();

            UsuarioColeccionCromoResponse dto =
                    usuarioCromoMapper.toUsuarioColeccionCromoResponse(cromo, usuarioCromo);

            cromosUsuario.add(dto);
        }

        return cromosUsuario;
    }
}