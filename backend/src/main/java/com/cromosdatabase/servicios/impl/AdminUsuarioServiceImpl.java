package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.*;
import com.cromosdatabase.modelo.dtos.admin.AdminUsuarioResponse;
import com.cromosdatabase.modelo.dtos.admin.AdminUsuarioRolResponse;
import com.cromosdatabase.modelo.dtos.admin.AdminUsuarioUpdateRequest;
import com.cromosdatabase.modelo.entidades.Rol;
import com.cromosdatabase.modelo.entidades.Usuario;
import com.cromosdatabase.modelo.entidades.UsuarioRol;
import com.cromosdatabase.modelo.entidades.UsuarioRolId;
import com.cromosdatabase.repositorios.RolRepository;
import com.cromosdatabase.repositorios.UsuarioRepository;
import com.cromosdatabase.repositorios.UsuarioRolRepository;
import com.cromosdatabase.servicios.AdminUsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio administrativo de usuarios.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminUsuarioServiceImpl implements AdminUsuarioService {

    /**
     * Repositorio de usuarios.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Repositorio de roles.
     */
    private final RolRepository rolRepository;

    /**
     * Repositorio de relaciones usuario-rol.
     */
    private final UsuarioRolRepository usuarioRolRepository;

    /**
     * Nombre del rol de usuario estándar.
     */
    private static final String NOMBRE_ROL_USER = "ROLE_USER";

    /**
     * Nombre del rol de administrador.
     */
    private static final String NOMBRE_ROL_ADMIN = "ROLE_ADMIN";

    /**
     * Obtiene el listado completo de usuarios.
     *
     * @return listado de usuarios
     */
    @Override
    public List<AdminUsuarioResponse> obtenerUsuarios() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        List<AdminUsuarioResponse> response = new ArrayList<>();

        for (Usuario usuario : usuarios) {

            List<AdminUsuarioRolResponse> rolesResponse = construirRolesResponse(usuario);

            AdminUsuarioResponse dto = new AdminUsuarioResponse(
                    usuario.getIdUsuario(),
                    usuario.getEmail(),
                    usuario.getNombreMostrar(),
                    usuario.isActivo(),
                    usuario.getFechaRegistro(),
                    rolesResponse
            );

            response.add(dto);
        }

        return response;
    }

    /**
     * Actualiza los datos de un usuario.
     *
     * @param idUsuario id del usuario
     * @param request datos actualizados
     */
    @Override
    public void actualizarUsuario(
            Integer idUsuario,
            AdminUsuarioUpdateRequest request) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                        "No existe ningún usuario con id " + idUsuario + "."
                ));

        validarDuplicados(usuario, request);

        usuario.setEmail(request.getEmail());
        usuario.setNombreMostrar(request.getNombreMostrar());
        usuario.setActivo(request.getActivo());

        actualizarRolesUsuario(usuario, request.getRoles());

        usuarioRepository.save(usuario);
    }

    /**
     * Valida duplicados de email y nombreMostrar.
     *
     * @param usuario usuario actual
     * @param request request recibido
     */
    private void validarDuplicados(
            Usuario usuario,
            AdminUsuarioUpdateRequest request) {

        Optional<Usuario> usuarioMismoEmail =
                usuarioRepository.findByEmail(request.getEmail());

        if (usuarioMismoEmail.isPresent()
                && !usuarioMismoEmail.get().getIdUsuario().equals(usuario.getIdUsuario())) {

            throw new EmailDuplicadoException(
                    "Ya existe un usuario con ese email."
            );
        }

        Optional<Usuario> usuarioMismoNombre =
                usuarioRepository.findByNombreMostrar(request.getNombreMostrar());

        if (usuarioMismoNombre.isPresent()
                && !usuarioMismoNombre.get().getIdUsuario().equals(usuario.getIdUsuario())) {

            throw new NombreAMostrarDuplicadoException(
                    "Ya existe un usuario con ese nombre a mostrar."
            );
        }
    }

    /**
     * Actualiza completamente los roles de un usuario.
     *
     * Los roles existentes se eliminan y se sustituyen
     * por los recibidos en la petición.
     *
     * @param usuario usuario a actualizar
     * @param idsRoles ids de roles finales
     */
    private void actualizarRolesUsuario(
            Usuario usuario,
            List<Integer> idsRoles) {

        List<Rol> roles = obtenerRolesValidados(idsRoles);

        validarCombinacionRoles(roles);

        usuarioRolRepository.deleteByUsuario_IdUsuario(usuario.getIdUsuario());

        for (Rol rol : roles) {

            UsuarioRol usuarioRol = new UsuarioRol();

            usuarioRol.setId(new UsuarioRolId(
                    usuario.getIdUsuario(),
                    rol.getIdRol()
            ));

            usuarioRol.setUsuario(usuario);
            usuarioRol.setRol(rol);

            usuarioRolRepository.save(usuarioRol);
        }
    }

    /**
     * Construye la lista de roles de respuesta.
     *
     * @param usuario usuario
     * @return lista de roles
     */
    private List<AdminUsuarioRolResponse> construirRolesResponse(Usuario usuario) {

        List<AdminUsuarioRolResponse> rolesResponse = new ArrayList<>();

        for (UsuarioRol usuarioRol : usuario.getUsuariosRoles()) {

            Rol rol = usuarioRol.getRol();

            AdminUsuarioRolResponse dto = new AdminUsuarioRolResponse(
                    rol.getIdRol(),
                    rol.getNombre()
            );

            rolesResponse.add(dto);
        }

        return rolesResponse;
    }

    /**
     * Obtiene y valida los roles recibidos en la petición.
     *
     * @param idsRoles ids de roles recibidos
     * @return lista de roles encontrados
     */
    private List<Rol> obtenerRolesValidados(List<Integer> idsRoles) {

        List<Rol> roles = new ArrayList<>();

        for (Integer idRol : idsRoles) {

            Rol rol = rolRepository.findById(idRol)
                    .orElseThrow(() -> new RolNoEncontradoException(
                            "No existe el rol con id " + idRol + "."
                    ));

            roles.add(rol);
        }

        return roles;
    }

    /**
     * Valida que la combinación de roles recibida sea correcta.
     *
     * Reglas:
     * - Un usuario puede tener solo ROLE_USER.
     * - Un usuario puede tener ROLE_USER y ROLE_ADMIN.
     * - Un usuario no puede tener solo ROLE_ADMIN.
     *
     * @param roles roles recibidos
     */
    private void validarCombinacionRoles(List<Rol> roles) {

        boolean tieneRolUser = false;
        boolean tieneRolAdmin = false;

        for (Rol rol : roles) {

            if (NOMBRE_ROL_USER.equals(rol.getNombre())) {
                tieneRolUser = true;
            }

            if (NOMBRE_ROL_ADMIN.equals(rol.getNombre())) {
                tieneRolAdmin = true;
            }
        }

        if (tieneRolAdmin && !tieneRolUser) {
            throw new UsuarioRolInvalidoException(
                    "Ningún usuario puede tener solamente el rol ROLE_ADMIN."
            );
        }
    }
}