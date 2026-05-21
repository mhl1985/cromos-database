package com.cromosdatabase.modelo.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta con los datos de un usuario
 * para la zona de administración.
 *
 * No incluye la contraseña.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUsuarioResponse {

    private Integer idUsuario;

    private String email;

    private String nombreMostrar;

    private Boolean activo;

    private LocalDateTime fechaRegistro;

    private List<AdminUsuarioRolResponse> roles;
}