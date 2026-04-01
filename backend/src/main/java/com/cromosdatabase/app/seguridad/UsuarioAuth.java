package com.cromosdatabase.app.seguridad;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Implementación de UserDetails que representa al usuario autenticado en el sistema.
 *
 * Esta clase adapta la entidad Usuario de la base de datos al modelo que utiliza
 * Spring Security internamente.
 *
 * Contiene la información necesaria para la autenticación y autorización:
 * - credenciales (email y contraseña)
 * - estado de la cuenta (activo)
 * - roles convertidos en autoridades (GrantedAuthority)
 */
@Getter
@AllArgsConstructor
public class UsuarioAuth implements UserDetails {

    /**
     * Identificador del usuario.
     */
    private Integer idUsuario;

    /**
     * Email del usuario (se usará como username en Spring Security).
     */
    private String email;

    /**
     * Nombre visible del usuario.
     */
    private String nombreMostrar;

    /**
     * Contraseña encriptada del usuario.
     */
    private String contrasena;

    /**
     * Indica si el usuario está activo.
     */
    private boolean activo;

    /**
     * Lista de autoridades (roles) del usuario.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Devuelve las autoridades (roles) del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Devuelve la contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return contrasena;
    }

    /**
     * Devuelve el identificador del usuario (email).
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indica si el usuario está habilitado.
     * Se basa en el campo "activo" de la base de datos.
     */
    @Override
    public boolean isEnabled() {
        return activo;
    }

    //------------------------------------------------------
    // MÉTODOS NO UTILIZADOS ACTUALMENTE
    //------------------------------------------------------

    /**
     * Indica si la cuenta no ha expirado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta no está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales no han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
