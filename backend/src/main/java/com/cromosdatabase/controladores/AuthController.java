package com.cromosdatabase.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cromosdatabase.modelo.dtos.auth.AuthResponse;
import com.cromosdatabase.modelo.dtos.auth.LoginRequest;
import com.cromosdatabase.servicios.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador encargado de gestionar las operaciones de autenticación.
 *
 * Expone los endpoints relacionados con el login de usuarios.
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

        return ResponseEntity.ok(response);
    }
}
