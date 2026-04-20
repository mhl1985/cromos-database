package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.ColeccionNoEncontradaException;
import com.cromosdatabase.comun.excepciones.UsuarioColeccionDuplicadaException;
import com.cromosdatabase.comun.excepciones.UsuarioColeccionNoEncontradaException;
import com.cromosdatabase.comun.excepciones.UsuarioNoEncontradoException;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.Coleccion;
import com.cromosdatabase.modelo.entidades.Usuario;
import com.cromosdatabase.modelo.entidades.UsuarioColeccion;
import com.cromosdatabase.modelo.entidades.UsuarioColeccionId;
import com.cromosdatabase.modelo.mappers.UsuarioColeccionMapper;
import com.cromosdatabase.repositorios.ColeccionRepository;
import com.cromosdatabase.repositorios.UsuarioColeccionRepository;
import com.cromosdatabase.repositorios.UsuarioCromoRepository;
import com.cromosdatabase.repositorios.UsuarioRepository;
import com.cromosdatabase.servicios.UsuarioColeccionService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para gestionar
 * las colecciones del usuario autenticado.
 */
@Service
@Transactional
public class UsuarioColeccionServiceImpl implements UsuarioColeccionService {

    private final UsuarioRepository usuarioRepository;
    private final ColeccionRepository coleccionRepository;
    private final UsuarioColeccionRepository usuarioColeccionRepository;
    private final UsuarioCromoRepository usuarioCromoRepository;
    private final UsuarioColeccionMapper usuarioColeccionMapper;

    public UsuarioColeccionServiceImpl(UsuarioRepository usuarioRepository,
                                       ColeccionRepository coleccionRepository,
                                       UsuarioColeccionRepository usuarioColeccionRepository,
                                       UsuarioCromoRepository usuarioCromoRepository,
                                       UsuarioColeccionMapper usuarioColeccionMapper) {
        this.usuarioRepository = usuarioRepository;
        this.coleccionRepository = coleccionRepository;
        this.usuarioColeccionRepository = usuarioColeccionRepository;
        this.usuarioCromoRepository = usuarioCromoRepository;
        this.usuarioColeccionMapper = usuarioColeccionMapper;
    }

    /**
     * Obtiene el listado de colecciones asociadas
     * al usuario autenticado.
     *
     * @return listado de colecciones del usuario autenticado.
     */
    @Override
    public List<UsuarioColeccionResumenResponse> obtenerColeccionesUsuarioAutenticado() {

        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

        List<UsuarioColeccion> listaUsuarioColeccion =
                usuarioColeccionRepository.findByUsuario_IdUsuario(usuarioAutenticado.getIdUsuario());

        return usuarioColeccionMapper.toResumenResponseList(listaUsuarioColeccion);
    }

    /**
     * Añade una colección al usuario autenticado.
     *
     * @param idColeccion ID de la colección a asociar.
     */
    @Override
    public void anadirColeccionUsuarioAutenticado(Integer idColeccion) {

        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

        Coleccion coleccion = obtenerColeccionPorId(idColeccion);

        boolean coleccionYaRelacionadaConUsuario =
                usuarioColeccionRepository.existsByUsuario_IdUsuarioAndColeccion_IdColeccion(
                        usuarioAutenticado.getIdUsuario(),
                        idColeccion
                );

        if (coleccionYaRelacionadaConUsuario) {
            throw new UsuarioColeccionDuplicadaException(
                    "La colección con id " + idColeccion + " ya está asociada al usuario autenticado."
            );
        }

        UsuarioColeccion usuarioColeccion = new UsuarioColeccion();
        usuarioColeccion.setId(new UsuarioColeccionId(
                usuarioAutenticado.getIdUsuario(),
                coleccion.getIdColeccion()
        ));
        usuarioColeccion.setUsuario(usuarioAutenticado);
        usuarioColeccion.setColeccion(coleccion);

        usuarioColeccionRepository.save(usuarioColeccion);
    }

    /**
     * Elimina una colección del usuario autenticado.
     *
     * Además de eliminar la relación usuario-colección,
     * también elimina las relaciones usuario-cromo
     * correspondientes a los cromos de esa colección.
     *
     * @param idColeccion ID de la colección a desasociar.
     */
    @Override
    public void eliminarColeccionUsuarioAutenticado(Integer idColeccion) {

        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

        validarSiColeccionEsAsociadaAUsuario(usuarioAutenticado.getIdUsuario(), idColeccion);

        // Eliminamos los cromos del usuario perteneciente sa esta colección.
        usuarioCromoRepository.deleteByUsuario_IdUsuarioAndCromo_Coleccion_IdColeccion(
                usuarioAutenticado.getIdUsuario(),
                idColeccion
        );

        // Eliminamos la relación usuario-colección.
        usuarioColeccionRepository.deleteByUsuario_IdUsuarioAndColeccion_IdColeccion(
                usuarioAutenticado.getIdUsuario(),
                idColeccion
        );
    }

    /**
     * Obtiene el usuario autenticado a partir del contexto de seguridad.
     *
     * @return usuario autenticado.
     */
    private Usuario obtenerUsuarioAutenticado() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = authentication.getName();

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(emailUsuario);

        if (usuarioOptional.isEmpty()) {
            throw new UsuarioNoEncontradoException(
                    "No se ha encontrado el usuario autenticado."
            );
        }

        return usuarioOptional.get();
    }

    /**
     * Obtiene una colección por su identificador.
     *
     * @param idColeccion id de la colección.
     * @return entidad colección.
     */
    private Coleccion obtenerColeccionPorId(Integer idColeccion) {

        Optional<Coleccion> coleccionOptional = coleccionRepository.findById(idColeccion);

        if (coleccionOptional.isEmpty()) {
            throw new ColeccionNoEncontradaException(
                    "No existe ninguna colección con id " + idColeccion + "."
            );
        }

        return coleccionOptional.get();
    }

    /**
     * Valida que la colección indicada esté asociada
     * al usuario autenticado.
     *
     * @param idUsuario id del usuario.
     * @param idColeccion id de la colección.
     */
    private void validarSiColeccionEsAsociadaAUsuario(Integer idUsuario, Integer idColeccion) {

        boolean coleccionYaRelacionadaConUsuario =
                usuarioColeccionRepository.existsByUsuario_IdUsuarioAndColeccion_IdColeccion(
                        idUsuario,
                        idColeccion
                );

        if (!coleccionYaRelacionadaConUsuario) {
            throw new UsuarioColeccionNoEncontradaException(
                    "La colección con id " + idColeccion + " no está asociada al usuario autenticado."
            );
        }
    }
}