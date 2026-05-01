package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.app.seguridad.UsuarioAuth;
import com.cromosdatabase.modelo.dtos.categoria.CategoriaColeccionResumenResponse;
import com.cromosdatabase.modelo.dtos.coleccion.ColeccionResumenResponse;
import com.cromosdatabase.modelo.dtos.cromo.CromoResumenResponse;
import com.cromosdatabase.modelo.dtos.paginas.ActividadUsuarioInicioResponse;
import com.cromosdatabase.modelo.dtos.paginas.InicioPageResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionResumenResponse;
import com.cromosdatabase.modelo.entidades.CategoriaColeccion;
import com.cromosdatabase.modelo.entidades.Coleccion;
import com.cromosdatabase.modelo.entidades.Cromo;
import com.cromosdatabase.modelo.entidades.UsuarioColeccion;
import com.cromosdatabase.modelo.entidades.UsuarioCromo;
import com.cromosdatabase.modelo.mappers.CategoriaColeccionMapper;
import com.cromosdatabase.modelo.mappers.ColeccionMapper;
import com.cromosdatabase.modelo.mappers.CromoMapper;
import com.cromosdatabase.modelo.mappers.UsuarioColeccionMapper;
import com.cromosdatabase.repositorios.CategoriaColeccionRepository;
import com.cromosdatabase.repositorios.ColeccionRepository;
import com.cromosdatabase.repositorios.CromoRepository;
import com.cromosdatabase.repositorios.UsuarioColeccionRepository;
import com.cromosdatabase.repositorios.UsuarioCromoRepository;
import com.cromosdatabase.servicios.PaginaInicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio para cargar la página de inicio.
 *
 * Este servicio agrupa información de distintos recursos para que
 * el frontend pueda pintar la pantalla principal con una única llamada.
 */
@Service
@RequiredArgsConstructor
public class PaginaInicioServiceImpl implements PaginaInicioService {

    /**
     * Repositorio de colecciones.
     */
    private final ColeccionRepository coleccionRepository;

    /**
     * Repositorio de categorías.
     */
    private final CategoriaColeccionRepository categoriaColeccionRepository;

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
     * Mapper de categorías.
     */
    private final CategoriaColeccionMapper categoriaColeccionMapper;

    /**
     * Mapper de cromos.
     */
    private final CromoMapper cromoMapper;

    /**
     * Mapper de colecciones del usuario.
     */
    private final UsuarioColeccionMapper usuarioColeccionMapper;

    /**
     * Carga todos los datos necesarios para la página de inicio.
     *
     * Bloques devueltos:
     * - actividad del usuario autenticado, si existe
     * - 10 últimas colecciones
     * - 5 categorías
     * - 10 últimos cromos
     * - 10 cromos aleatorios
     *
     * @return DTO con los datos de la página de inicio
     */
    @Override
    @Transactional(readOnly = true)
    public InicioPageResponse cargarPaginaInicio() {

        // Si hay usuario autenticado, se obtiene su actividad resumida.
        ActividadUsuarioInicioResponse actividadUsuario =
                obtenerActividadUsuarioSiEstaAutenticado();

        // Obtenemos las últimas colecciones añadidas al sistema.
        List<Coleccion> ultimasColeccionesEntidad =
                coleccionRepository.findTop10ByOrderByIdColeccionDesc();

        // Obtenemos categorías aleatorias para mostrar en la home.
        List<CategoriaColeccion> categoriasEntidad =
                categoriaColeccionRepository.find5Aleatorias();

        // Obtenemos los últimos cromos añadidos al sistema.
        List<Cromo> ultimosCromosEntidad =
                cromoRepository.findTop10ByOrderByIdCromoDesc();

        // Obtenemos cromos aleatorios para mostrar en la home.
        List<Cromo> cromosAleatoriosEntidad =
                cromoRepository.find10Aleatorios();

        // Mapeo de entidades a DTOs.
        List<ColeccionResumenResponse> ultimasColecciones =
                coleccionMapper.toResumenResponseList(ultimasColeccionesEntidad);

        List<CategoriaColeccionResumenResponse> categorias =
                categoriaColeccionMapper.toResumenResponseList(categoriasEntidad);

        List<CromoResumenResponse> ultimosCromos =
                cromoMapper.toResumenResponseList(ultimosCromosEntidad);

        List<CromoResumenResponse> cromosAleatorios =
                cromoMapper.toResumenResponseList(cromosAleatoriosEntidad);

        // Construimos la respuesta final de la página.
        InicioPageResponse response = new InicioPageResponse(
                actividadUsuario,
                ultimasColecciones,
                categorias,
                ultimosCromos,
                cromosAleatorios
        );

        return response;
    }

    /**
     * Obtiene la actividad resumida del usuario autenticado.
     *
     * Si la petición no tiene usuario autenticado, devuelve null.
     *
     * @return actividad resumida del usuario o null si no está autenticado
     */
    private ActividadUsuarioInicioResponse obtenerActividadUsuarioSiEstaAutenticado() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Si no hay autenticación, no se puede devolver actividad de usuario.
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

        // Últimas colecciones añadidas por el usuario.
        List<UsuarioColeccion> ultimasColeccionesUsuarioEntidad =
                usuarioColeccionRepository.findTop10ByUsuario_IdUsuarioOrderByFechaAgregadaDesc(idUsuario);

        // Todas las colecciones del usuario, para obtener contador.
        List<UsuarioColeccion> coleccionesUsuarioEntidad =
                usuarioColeccionRepository.findByUsuario_IdUsuario(idUsuario);

        // Todos los cromos marcados por el usuario, para obtener contadores.
        List<UsuarioCromo> cromosUsuarioEntidad =
                usuarioCromoRepository.findByUsuario_IdUsuario(idUsuario);

        // Mapeo de las últimas colecciones del usuario.
        List<UsuarioColeccionResumenResponse> ultimasColeccionesAgregadas =
                usuarioColeccionMapper.toResumenResponseList(ultimasColeccionesUsuarioEntidad);

        Integer totalColecciones = coleccionesUsuarioEntidad.size();

        Integer totalCromosUsuario =
                calcularTotalCromosUsuario(cromosUsuarioEntidad);

        Integer totalCromosIntercambiables =
                calcularTotalCromosIntercambiables(cromosUsuarioEntidad);

        ActividadUsuarioInicioResponse response = new ActividadUsuarioInicioResponse(
                ultimasColeccionesAgregadas,
                totalColecciones,
                totalCromosUsuario,
                totalCromosIntercambiables
        );

        return response;
    }

    /**
     * Calcula el número total de cromos que tiene el usuario.
     *
     * Suma la cantidad total de unidades guardadas en cada relación
     * usuario-cromo.
     *
     * @param cromosUsuario lista de relaciones usuario-cromo
     * @return número total de unidades de cromos del usuario
     */
    private Integer calcularTotalCromosUsuario(List<UsuarioCromo> cromosUsuario) {

        Integer totalCromosUsuario = 0;

        for (UsuarioCromo usuarioCromo : cromosUsuario) {

            Integer cantidadTotal = usuarioCromo.getCantidadTotal();

            if (cantidadTotal != null) {
                totalCromosUsuario = totalCromosUsuario + cantidadTotal;
            }
        }

        return totalCromosUsuario;
    }

    /**
     * Calcula el total de cromos que el usuario tiene disponibles
     * para intercambio.
     *
     * Se considera intercambiable cuando cantidadIntercambiable es mayor que 0.
     *
     * @param cromosUsuario lista de relaciones usuario-cromo
     * @return número de cromos intercambiables
     */
    private Integer calcularTotalCromosIntercambiables(List<UsuarioCromo> cromosUsuario) {

        Integer totalCromosIntercambiables = 0;

        for (UsuarioCromo usuarioCromo : cromosUsuario) {

            Integer cantidadIntercambiable = usuarioCromo.getCantidadIntercambiable();

            if (cantidadIntercambiable != null && cantidadIntercambiable > 0) {
                totalCromosIntercambiables++;
            }
        }

        return totalCromosIntercambiables;
    }
}