package com.cromosdatabase.app.seguridad;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio encargado de generar, leer y validar tokens JWT.
 *
 * Centraliza toda la lógica relacionada con los tokens usados en
 * la autenticación de la aplicación.
 */
@Service
public class JwtService {

    /**
     * Clave secreta usada para firmar y validar los tokens.
     */
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /**
     * Tiempo de validez del token en milisegundos.
     */
    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    /**
     * Genera un token JWT para el usuario autenticado.
     *
     * En este proyecto el token guardará el email del usuario en el subject.
     *
     * @param usuarioAuth usuario autenticado
     * @return token JWT generado
     */
    public String generarToken(UsuarioAuth usuarioAuth) {

        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationMs);
        SecretKey claveFirma = obtenerClaveFirma();

        JwtBuilder constructorToken = Jwts.builder();

        constructorToken.subject(usuarioAuth.getUsername());
        constructorToken.issuedAt(fechaActual);
        constructorToken.expiration(fechaExpiracion);
        constructorToken.signWith(claveFirma);

        String token = constructorToken.compact();

        return token;
    }

    /**
     * Extrae el email del usuario contenido en el token.
     *
     * En este proyecto el email se almacena en el subject del JWT.
     *
     * @param token token JWT
     * @return email contenido en el token
     */
    public String extraerEmail(String token) {

        Claims claims = extraerTodosLosClaims(token);
        String email = claims.getSubject();

        return email;
    }

    /**
     * Extrae la fecha de expiración del token.
     *
     * @param token token JWT
     * @return fecha de expiración del token
     */
    public Date extraerExpiracion(String token) {

        Claims claims = extraerTodosLosClaims(token);
        Date fechaExpiracion = claims.getExpiration();

        return fechaExpiracion;
    }

    /**
     * Comprueba si un token es válido para el usuario indicado.
     *
     * Para que un token sea válido:
     * - el email del token debe coincidir con el del usuario
     * - el token no debe haber expirado
     *
     * @param token token JWT
     * @param usuarioAuth usuario contra el que se valida el token
     * @return true si el token es válido, false en caso contrario
     */
    public boolean esTokenValido(String token, UsuarioAuth usuarioAuth) {

        String emailToken = extraerEmail(token);
        String emailUsuario = usuarioAuth.getUsername();
        boolean mismoUsuario = emailToken.equals(emailUsuario);
        boolean tokenExpirado = estaTokenExpirado(token);

        boolean tokenValido = mismoUsuario && !tokenExpirado;

        return tokenValido;
    }

    /**
     * Comprueba si el token ha expirado.
     *
     * @param token token JWT
     * @return true si el token ha expirado, false en caso contrario
     */
    private boolean estaTokenExpirado(String token) {

        Date fechaExpiracion = extraerExpiracion(token);
        Date fechaActual = new Date();
        boolean expirado = fechaExpiracion.before(fechaActual);

        return expirado;
    }

    /**
     * Extrae todos los claims contenidos en el token.
     *
     * Los claims son los datos almacenados dentro del JWT, como por ejemplo:
     * - subject
     * - fecha de creación
     * - fecha de expiración
     *
     * @param token token JWT
     * @return claims del token
     */
    private Claims extraerTodosLosClaims(String token) {

        SecretKey claveFirma = obtenerClaveFirma();

        Claims claims = Jwts.parser()
                .verifyWith(claveFirma)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims;
    }

    /**
     * Obtiene la clave usada para firmar y validar los tokens.
     *
     * @return clave de firma
     */
    private SecretKey obtenerClaveFirma() {

        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        SecretKey claveFirma = Keys.hmacShaKeyFor(keyBytes);

        return claveFirma;
    }
}
