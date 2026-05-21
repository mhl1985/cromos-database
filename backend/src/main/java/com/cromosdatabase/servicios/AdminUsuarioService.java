package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.admin.AdminUsuarioResponse;
import com.cromosdatabase.modelo.dtos.admin.AdminUsuarioUpdateRequest;

import java.util.List;

/**
 * Servicio para la gestión administrativa de usuarios.
 *
 * Solo accesible para usuarios con ROLE_ADMIN.
 */
public interface AdminUsuarioService {

    /**
     * Obtiene el listado completo de usuarios registrados
     * en la aplicación junto con sus roles asociados.
     *
     * No incluye las contraseñas.
     *
     * @return listado completo de usuarios
     */
    List<AdminUsuarioResponse> obtenerUsuarios();

    /**
     * Actualiza los datos de un usuario existente.
     *
     * Permite modificar:
     * - email
     * - nombreMostrar
     * - activo
     * - roles
     *
     * No permite modificar la contraseña.
     *
     * @param idUsuario id del usuario a actualizar
     * @param request datos actualizados del usuario
     */
    void actualizarUsuario(
            Integer idUsuario,
            AdminUsuarioUpdateRequest request
    );
}