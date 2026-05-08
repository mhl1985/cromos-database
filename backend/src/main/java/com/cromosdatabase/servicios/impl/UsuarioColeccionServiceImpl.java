package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.ColeccionNoEncontradaException;
import com.cromosdatabase.comun.excepciones.UsuarioColeccionDuplicadaException;
import com.cromosdatabase.comun.excepciones.UsuarioColeccionNoEncontradaException;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.Coleccion;
import com.cromosdatabase.modelo.entidades.Usuario;
import com.cromosdatabase.modelo.entidades.UsuarioColeccion;
import com.cromosdatabase.modelo.entidades.UsuarioColeccionId;
import com.cromosdatabase.modelo.mappers.UsuarioColeccionMapper;
import com.cromosdatabase.repositorios.ColeccionRepository;
import com.cromosdatabase.repositorios.UsuarioColeccionRepository;
import com.cromosdatabase.repositorios.UsuarioCromoRepository;
import com.cromosdatabase.servicios.AuthService;
import com.cromosdatabase.servicios.UsuarioColeccionService;
import jakarta.transaction.Transactional;
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

    private final ColeccionRepository coleccionRepository;
    private final UsuarioColeccionRepository usuarioColeccionRepository;
    private final UsuarioCromoRepository usuarioCromoRepository;
    private final UsuarioColeccionMapper usuarioColeccionMapper;
    private final AuthService authService;

    public UsuarioColeccionServiceImpl(ColeccionRepository coleccionRepository,
                                       UsuarioColeccionRepository usuarioColeccionRepository,
                                       UsuarioCromoRepository usuarioCromoRepository,
                                       UsuarioColeccionMapper usuarioColeccionMapper,
                                       AuthService authService) {
        this.coleccionRepository = coleccionRepository;
        this.usuarioColeccionRepository = usuarioColeccionRepository;
        this.usuarioCromoRepository = usuarioCromoRepository;
        this.usuarioColeccionMapper = usuarioColeccionMapper;
        this.authService = authService;
    }

    /**
     * Obtiene el listado de colecciones asociadas
     * al usuario autenticado.
     *
     * @return listado de colecciones del usuario autenticado.
     */
    @Override
    public List<UsuarioColeccionResumenResponse> obtenerColeccionesUsuarioAutenticado() {

        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();

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

        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();

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

        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();

        validarColeccionAsociadaAUsuario(usuarioAutenticado.getIdUsuario(), idColeccion);

        // Eliminamos los cromos del usuario perteneciente a esta colección.
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
    @Override
    public void validarColeccionAsociadaAUsuario(Integer idUsuario, Integer idColeccion) {

        // Comprobamos si existe la relación entre este usuario y
        // esta colección en la tabla usuarios_colecciones.
        boolean coleccionYaRelacionadaConUsuario =
                usuarioColeccionRepository.existsByUsuario_IdUsuarioAndColeccion_IdColeccion(
                        idUsuario,
                        idColeccion
                );

        // Si no existe relación, el usuario no puede operar sobre esa colección.
        if (!coleccionYaRelacionadaConUsuario) {
            throw new UsuarioColeccionNoEncontradaException(
                    "La colección con id " + idColeccion + " no está asociada al usuario autenticado."
            );
        }
    }
}