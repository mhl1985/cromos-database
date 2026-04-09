package com.cromosdatabase.controladores;

import com.cromosdatabase.modelo.dtos.auth.AuthResponse;
import com.cromosdatabase.modelo.dtos.auth.LoginRequest;
import com.cromosdatabase.modelo.dtos.auth.PerfilUsuarioAuthResponse;
import com.cromosdatabase.modelo.dtos.auth.RegistroUsuarioRequest;
import com.cromosdatabase.modelo.dtos.auth.RegistroUsuarioResponse;
import com.cromosdatabase.servicios.AuthService;
import com.cromosdatabase.servicios.RegistroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

/**
 * Controlador encargado de gestionar las operaciones de autenticación.
 * Expone los endpoints relacionados con el login, el registro
 * y la consulta del perfil del usuario autenticado.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Servicio de autenticación.
     */
    private final AuthService authService;

    /**
     * Servicio de registro de usuarios.
     */
    private final RegistroUsuarioService registroUsuarioService;

    /**
     * Endpoint para iniciar sesión.
     *
     * Recibe las credenciales del usuario y devuelve un token JWT si son correctas.
     *
     * @param loginRequest datos de login (email y contraseña)
     * @return respuesta con el token JWT y datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        AuthResponse response = authService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * Recibe los datos de alta, los valida (@Valid), crea el usuario en el sistema
     * y devuelve la información (DTO) del usuario registrado.
     *
     * @param request datos de entrada del registro
     * @return respuesta con los datos del usuario creado (DTO)
     */
    @PostMapping("/registro")
    public ResponseEntity<RegistroUsuarioResponse> registrarUsuario(
            @Valid @RequestBody RegistroUsuarioRequest request) {

        RegistroUsuarioResponse response = registroUsuarioService.registrarUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para obtener el perfil del usuario autenticado.
     *
     * Reutiliza el usuario autenticado actual disponible en Spring Security
     * a partir del JWT enviado en la petición.
     *
     * @return respuesta con los datos del perfil del usuario autenticado
     */
    @GetMapping("/perfil")
    public ResponseEntity<PerfilUsuarioAuthResponse> obtenerPerfilUsuarioAutenticado() {

        PerfilUsuarioAuthResponse response = authService.obtenerPerfilUsuarioAutenticado();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
