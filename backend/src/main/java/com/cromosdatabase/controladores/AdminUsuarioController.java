package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.admin.AdminUsuarioResponse;
import com.cromosdatabase.modelo.dtos.admin.AdminUsuarioUpdateRequest;
import com.cromosdatabase.servicios.AdminUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión administrativa
 * de usuarios.
 *
 * Solo accesible para usuarios con ROLE_ADMIN.
 */
@RestController
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
public class AdminUsuarioController {

    /**
     * Servicio administrativo de usuarios.
     */
    private final AdminUsuarioService adminUsuarioService;

    /**
     * Obtiene el listado completo de usuarios.
     *
     * Devuelve:
     * - datos básicos del usuario
     * - estado activo/inactivo
     * - fecha de registro
     * - roles asociados
     *
     * No devuelve contraseñas.
     *
     * @return listado completo de usuarios
     */
    @GetMapping
    public List<AdminUsuarioResponse> obtenerUsuarios() {

        List<AdminUsuarioResponse> response =
                adminUsuarioService.obtenerUsuarios();

        return response;
    }

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
    @PutMapping("/{idUsuario}")
    public void actualizarUsuario(
            @PathVariable Integer idUsuario,
            @Valid @RequestBody AdminUsuarioUpdateRequest request) {

        adminUsuarioService.actualizarUsuario(idUsuario, request);
    }
}