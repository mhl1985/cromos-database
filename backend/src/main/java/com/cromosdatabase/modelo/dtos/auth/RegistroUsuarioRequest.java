package com.cromosdatabase.modelo.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de entrada para la petición de registro de un nuevo usuario.
 *
 * Recoge los datos mínimos necesarios para dar de alta a un usuario
 * en la aplicación.
 *
 * Validaciones incluidas:
 * - Email obligatorio y con formato válido
 * - Password obligatorio y con longitud mínima y máxima
 * - NombreMostrar obligatorio y con longitud mínima y máxima
 */
@Getter
@Setter
@NoArgsConstructor
public class RegistroUsuarioRequest {

    /**
     * Correo electrónico del usuario.
     *
     * Se utilizará como identificador único de login.
     */
    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El formato del email no es válido.")
    @Size(max = 80, message = "El email no puede superar los 80 caracteres.")
    private String email;

    /**
     * Contraseña en texto plano enviada en la petición.
     *
     * Esta contraseña nunca se guardará tal cual en base de datos.
     * Antes de persistir el usuario, se transformará mediante BCrypt.
     */
    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 4, max = 255, message = "La contraseña debe tener entre 4 y 255 caracteres.")
    private String password;

    /**
     * Nombre visible del usuario dentro de la aplicación.
     *
     * Debe ser único en el sistema.
     */
    @NotBlank(message = "El nombre a mostrar es obligatorio.")
    @Size(min = 4, max = 50, message = "El nombre a mostrar debe tener entre 4 y 50 caracteres.")
    private String nombreMostrar;

}
