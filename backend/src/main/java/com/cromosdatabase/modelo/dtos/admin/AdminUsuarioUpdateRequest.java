package com.cromosdatabase.modelo.dtos.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * DTO de entrada para actualizar un usuario desde
 * la zona de administración.
 *
 * No permite modificar la contraseña.
 */
@Data
public class AdminUsuarioUpdateRequest {

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El formato del email no es válido.")
    @Size(max = 80, message = "El email no puede superar los 80 caracteres.")
    private String email;

    @NotBlank(message = "El nombre a mostrar es obligatorio.")
    @Size(min = 4, max = 50, message = "El nombre a mostrar debe tener entre 4 y 50 caracteres.")
    private String nombreMostrar;

    @NotNull(message = "El campo activo es obligatorio.")
    private Boolean activo;

    @NotEmpty(message = "El usuario debe tener al menos un rol.")
    private List<Integer> roles;
}