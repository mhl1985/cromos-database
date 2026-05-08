package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.UsuarioColeccionCromoEdicionInvalidaException;
import com.cromosdatabase.comun.excepciones.UsuarioColeccionNoEncontradaException;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoEdicionRequest;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionCromoResponse;
import com.cromosdatabase.modelo.dtos.usuario.UsuarioColeccionListaCromosEdicionRequest;
import com.cromosdatabase.modelo.entidades.Cromo;
import com.cromosdatabase.modelo.entidades.Usuario;
import com.cromosdatabase.modelo.entidades.UsuarioCromo;
import com.cromosdatabase.modelo.entidades.UsuarioCromoId;
import com.cromosdatabase.modelo.mappers.UsuarioCromoMapper;
import com.cromosdatabase.repositorios.CromoRepository;
import com.cromosdatabase.repositorios.UsuarioColeccionRepository;
import com.cromosdatabase.repositorios.UsuarioCromoRepository;
import com.cromosdatabase.servicios.AuthService;
import com.cromosdatabase.servicios.UsuarioCromoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Implementación del servicio para gestionar los cromos
 * de las colecciones del usuario autenticado.
 */
@Service
@Transactional
public class UsuarioCromoServiceImpl implements UsuarioCromoService {

    private final CromoRepository cromoRepository;
    private final UsuarioCromoRepository usuarioCromoRepository;
    private final UsuarioColeccionRepository usuarioColeccionRepository;
    private final UsuarioCromoMapper usuarioCromoMapper;
    private final AuthService authService;

    public UsuarioCromoServiceImpl(CromoRepository cromoRepository,
                                   UsuarioCromoRepository usuarioCromoRepository,
                                   UsuarioColeccionRepository usuarioColeccionRepository,
                                   UsuarioCromoMapper usuarioCromoMapper,
                                   AuthService authService) {
        this.cromoRepository = cromoRepository;
        this.usuarioCromoRepository = usuarioCromoRepository;
        this.usuarioColeccionRepository = usuarioColeccionRepository;
        this.usuarioCromoMapper = usuarioCromoMapper;
        this.authService = authService;
    }

    /**
     * Obtiene el listado completo de cromos de una colección
     * del usuario autenticado, incluyendo la información guardada
     * por el usuario sobre cada uno de ellos.
     *
     * @param idColeccion id de la colección.
     * @return listado de cromos de la colección con la información
     * guardada por el usuario.
     */
    @Override
    public List<UsuarioColeccionCromoResponse> obtenerCromosColeccionUsuario(Integer idColeccion) {

        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();

        // Validamos que la colección que se quiere consultar
        // esté realmente asociada al usuario autenticado.
        validarSiColeccionEsAsociadaAUsuario(usuarioAutenticado.getIdUsuario(), idColeccion);

        // Listado completo de cromos de la colección.
        List<Cromo> cromosColeccion = cromoRepository.findByColeccion_IdColeccion(idColeccion);

        // Obtenemos los cromos de la colección que tiene el
        // usuario + la info personal de cada uno de ellos.
        List<UsuarioCromo> usuarioCromosColeccion =
                usuarioCromoRepository.findByUsuario_IdUsuarioAndCromo_Coleccion_IdColeccion(
                        usuarioAutenticado.getIdUsuario(),
                        idColeccion
                );

        // Mapa (idCromo, info del usuario relacionada con ese id de cromo)
        Map<Integer, UsuarioCromo> mapaUsuarioCromos = construirMapaUsuarioCromos(usuarioCromosColeccion);

        List<UsuarioColeccionCromoResponse> listadoResponse = new ArrayList<>();

        // Recorremos la lista de todos los cromos de la colección.
        for (Cromo cromo : cromosColeccion) {

            // Obtenemos la info del usuario relacionada con el cromo
            // (puede ser null si el user no tiene el cromo).
            UsuarioCromo usuarioCromo = mapaUsuarioCromos.get(cromo.getIdCromo());

            // Añadimos al DTO los datos base del cromo + los datos guardados
            // por el usuario para ese cromo (en caso de que lo tenga).
            UsuarioColeccionCromoResponse dto =
                    usuarioCromoMapper.toUsuarioColeccionCromoResponse(cromo, usuarioCromo);

            listadoResponse.add(dto);
        }

        return listadoResponse;
    }

    /**
     * Actualiza de forma masiva los cromos de una colección
     * asociada al usuario autenticado.
     *
     * @param idColeccion id de la colección.
     * @param request lista de cromos enviada desde frontend.
     * @return listado actualizado de cromos de la colección
     * perteneciente al usuario.
     */
    @Override
    public List<UsuarioColeccionCromoResponse> actualizarCromosColeccionUsuario(
            Integer idColeccion,
            UsuarioColeccionListaCromosEdicionRequest request) {

        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();

        // Validamos que la colección que se quiere consultar
        // esté realmente asociada al usuario autenticado.
        validarSiColeccionEsAsociadaAUsuario(usuarioAutenticado.getIdUsuario(), idColeccion);

        // Extraemos lista de cromos enviada desde frontend.
        List<UsuarioColeccionCromoEdicionRequest> listaCromosRequest = request.getCromos();

        // Validamos en bloque que todos los ids de cromo recibidos pertenecen
        // realmente a la colección indicada y no vienen repetidos.
        // Clave -> idCromo, Valor -> entidad Cromo
        Map<Integer, Cromo> mapaCromosValidados =
                validarYObtenerMapaCromosDeLaColeccion(idColeccion, listaCromosRequest);

        // Recorremos todos los cromos recibidos en la petición y los procesamos individualmente.
        for (UsuarioColeccionCromoEdicionRequest cromoRequest : listaCromosRequest) {
            procesarEdicionCromo(usuarioAutenticado, cromoRequest, mapaCromosValidados);
        }

        // Volvemos a cargar el listado completo ya actualizado desde BD.
        // Esto asegura que enviemos al frontend el estado real
        // final persistido tras la operación.
        List<UsuarioColeccionCromoResponse> listadoActualizado = obtenerCromosColeccionUsuario(idColeccion);

        return listadoActualizado;
    }

    /**
     * Valida que la colección indicada esté asociada
     * al usuario autenticado.
     *
     * @param idUsuario id del usuario.
     * @param idColeccion id de la colección.
     */
    private void validarSiColeccionEsAsociadaAUsuario(Integer idUsuario, Integer idColeccion) {

        // Comprobamos si existe la relación entre este usuario y
        // esta colección en la tabla usuarios_colecciones.
        boolean existeRelacion =
                usuarioColeccionRepository.existsByUsuario_IdUsuarioAndColeccion_IdColeccion(
                        idUsuario,
                        idColeccion
                );

        if (!existeRelacion) {
            throw new UsuarioColeccionNoEncontradaException(
                    "La colección con id " + idColeccion + " no está asociada al usuario autenticado."
            );
        }
    }

    /**
     * Valida que todos los cromos recibidos pertenezcan
     * a la colección indicada y construye un mapa con ellos.
     * Validamos en bloque para evitar consultas individuales por cada cromo.
     * Clave -> idCromo
     * Valor -> entidad Cromo
     *
     * @param idColeccion id de la colección.
     * @param listaCromosEditadosRequest lista de cromos editados enviada desde frontend.
     * @return mapa de cromos validados, indexado por idCromo.
     */
    private Map<Integer, Cromo> validarYObtenerMapaCromosDeLaColeccion(
            Integer idColeccion,
            List<UsuarioColeccionCromoEdicionRequest> listaCromosEditadosRequest) {

        // Usamos LinkedHashSet porque elimina duplicados
        // y mantiene el orden de inserción.
        Set<Integer> idsCromoRequest = new LinkedHashSet<>();

        // Recorremos la lista de cromos recibida desde frontend.
        for (UsuarioColeccionCromoEdicionRequest cromoRequest : listaCromosEditadosRequest) {

            // Obtenemos el id del cromo actual.
            Integer idCromo = cromoRequest.getIdCromo();

            // Intentamos añadir el id al Set.
            // True, es la primera vez que aparece.
            // False, significa que ese id ya estaba dentro, es decir, que viene repetido en la petición.
            boolean anadido = idsCromoRequest.add(idCromo);

            // Lanzamos excepción si el id está repetido.
            // Así evitamos que el mismo cromo se procese dos veces
            // en una misma actualización.
            if (!anadido) {
                throw new UsuarioColeccionCromoEdicionInvalidaException(
                        "El cromo con id " + idCromo + " está repetido en la petición."
                );
            }
        }

        // Convertimos el Set en una lista porque el repositorio
        // espera una lista de ids para el IN de la consulta.
        List<Integer> listaIdsCromo = new ArrayList<>(idsCromoRequest);

        // Consultamos en bloque todos los cromos de la colección
        // cuyos ids estén dentro de la lista recibida.
        // Así evitamos hacer una consulta por cada cromo.
        List<Cromo> cromosEncontrados =
                cromoRepository.findByColeccion_IdColeccionAndIdCromoIn(idColeccion, listaIdsCromo);

        // Creamos y rellenamos un mapa para guardar los cromos encontrados en BD.
        // Clave -> idCromo, Valor -> entidad Cromo
        Map<Integer, Cromo> mapaCromos = new HashMap<>();
        for (Cromo cromo : cromosEncontrados) {
            mapaCromos.put(cromo.getIdCromo(), cromo);
        }

        // Comprobamos que todos los ids recibidos del frontend
        // están realmente en el mapa de cromos encontrados.
        for (Integer idCromo : listaIdsCromo) {
            // Si falta alguno (no existe o no pertenece a la
            // colección indicada) lanzamos excepción.
            if (!mapaCromos.containsKey(idCromo)) {
                throw new UsuarioColeccionCromoEdicionInvalidaException(
                        "El cromo con id " + idCromo + " no pertenece a la colección con id " + idColeccion + "."
                );
            }
        }

        // Devolvemos el mapa de cromos validados.
        return mapaCromos;
    }

    /**
     * Procesa la edición de un cromo enviada desde frontend.
     * Dependiendo de los datos recibidos, esto podrá terminar en:
     * - Borrado de la relación usuario-cromo.
     * - Creación de una nueva relación.
     * - Actualización de una relación existente.
     *
     * @param usuarioAutenticado usuario autenticado.
     * @param cromoRequest datos enviados para el cromo.
     * @param mapaCromosValidados mapa de cromos ya validados.
     */
    private void procesarEdicionCromo(Usuario usuarioAutenticado,
                                      UsuarioColeccionCromoEdicionRequest cromoRequest,
                                      Map<Integer, Cromo> mapaCromosValidados) {

        // Si el cromo viene desmarcado, eso significa que el usuario
        // ya no quiere tenerlo guardado en su colección.
        if (Boolean.FALSE.equals(cromoRequest.getMarcado())) {
            eliminarRelacionUsuarioCromo(usuarioAutenticado.getIdUsuario(), cromoRequest.getIdCromo());
            return;
        }

        // Validamos reglas de negocio:
        // CantidadTotal > 0
        // CantidadIntercambiable >= 0
        // CantidadIntercambiable <= cantidadTotal
        validarDatosCromoMarcado(cromoRequest);

        // Recuperamos del mapa el cromo ya validado correspondiente
        // al id recibido en el request.
        Cromo cromo = mapaCromosValidados.get(cromoRequest.getIdCromo());

        // Construimos la clave compuesta de la relación usuario-cromo.
        UsuarioCromoId usuarioCromoId = new UsuarioCromoId(
                usuarioAutenticado.getIdUsuario(),
                cromo.getIdCromo()
        );

        // Buscamos si ya existe esa relación usuario-cromo en BD.
        Optional<UsuarioCromo> usuarioCromoOptional = usuarioCromoRepository.findById(usuarioCromoId);

        UsuarioCromo usuarioCromo;

        // Si la relación ya existe...
        if (usuarioCromoOptional.isPresent()) {
            // Reutilizamos la entidad existente para actualizarla.
            usuarioCromo = usuarioCromoOptional.get();

        // Si la relación no existe...
        } else {
            // Creamos una nueva entidad con los datos correspondientes.
            usuarioCromo = new UsuarioCromo();
            usuarioCromo.setId(usuarioCromoId);
            usuarioCromo.setUsuario(usuarioAutenticado);
            usuarioCromo.setCromo(cromo);
        }

        // Finalmente, seteamos los datos seleccionados por el usuario para ese cromo.
        usuarioCromo.setCantidadTotal(cromoRequest.getCantidadTotal());
        usuarioCromo.setCantidadIntercambiable(cromoRequest.getCantidadIntercambiable());
        usuarioCromo.setObservaciones(cromoRequest.getObservaciones());

        // Guardamos en BD la relación.
        usuarioCromoRepository.save(usuarioCromo);
    }

    /**
     * Valida los datos de un cromo marcado por el usuario.
     * CantidadTotal > 0
     * CantidadIntercambiable >= 0
     * CantidadIntercambiable <= cantidadTotal
     *
     * @param cromoRequest datos recibidos desde el frontend.
     */
    private void validarDatosCromoMarcado(UsuarioColeccionCromoEdicionRequest cromoRequest) {

        Integer cantidadTotal = cromoRequest.getCantidadTotal();
        Integer cantidadIntercambiable = cromoRequest.getCantidadIntercambiable();

        if (cantidadTotal == null || cantidadTotal <= 0) {
            throw new UsuarioColeccionCromoEdicionInvalidaException(
                    "La cantidad total debe ser mayor que cero cuando el cromo está marcado."
            );
        }
        if (cantidadIntercambiable == null || cantidadIntercambiable < 0) {
            throw new UsuarioColeccionCromoEdicionInvalidaException(
                    "La cantidad intercambiable no puede ser negativa."
            );
        }
        if (cantidadIntercambiable > cantidadTotal) {
            throw new UsuarioColeccionCromoEdicionInvalidaException(
                    "La cantidad intercambiable no puede ser mayor que la cantidad total."
            );
        }
    }

    /**
     * Elimina la asociación entre el usuario y el cromo.
     *
     * @param idUsuario id del usuario.
     * @param idCromo id del cromo.
     */
    private void eliminarRelacionUsuarioCromo(Integer idUsuario, Integer idCromo) {
        usuarioCromoRepository.deleteByUsuario_IdUsuarioAndCromo_IdCromo(idUsuario, idCromo);
    }

    /**
     * Construye un mapa de relaciones usuario-cromo
     * indexado por el ID del cromo.
     * Clave -> idCromo
     * Valor -> entidad UsuarioCromo
     *
     * @param usuarioCromos lista de relaciones usuario-cromo.
     * @return mapa con clave idCromo y valor UsuarioCromo.
     */
    private Map<Integer, UsuarioCromo> construirMapaUsuarioCromos(List<UsuarioCromo> usuarioCromos) {

        Map<Integer, UsuarioCromo> mapaUsuarioCromos = new HashMap<>();

        for (UsuarioCromo usuarioCromo : usuarioCromos) {
            Integer idCromo = usuarioCromo.getCromo().getIdCromo();
            mapaUsuarioCromos.put(idCromo, usuarioCromo);
        }

        return mapaUsuarioCromos;
    }
}