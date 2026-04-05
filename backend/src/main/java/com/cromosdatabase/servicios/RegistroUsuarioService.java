package com.cromosdatabase.servicios;

import com.cromosdatabase.modelo.dtos.auth.RegistroUsuarioRequest;
import com.cromosdatabase.modelo.dtos.auth.RegistroUsuarioResponse;

/**
 * Servicio encargado de gestionar el registro de nuevos usuarios.
 *
 * Define las operaciones necesarias para dar de alta un usuario
 * en el sistema.
 */
public interface RegistroUsuarioService {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * Realiza las siguientes acciones:
     * - Validación de email único
     * - Validación de nombre de mostrar único
     * - Cifrado de la contraseña
     * - Alta del usuario en base de datos
     * - Asociación del rol por defecto
     *
     * @param request datos de entrada del registro
     * @return datos básicos del usuario registrado
     */
    RegistroUsuarioResponse registrarUsuario(RegistroUsuarioRequest request);

}
