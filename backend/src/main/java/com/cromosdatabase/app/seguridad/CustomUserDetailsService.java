package com.cromosdatabase.app.seguridad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cromosdatabase.modelo.entidades.UsuarioRol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cromosdatabase.modelo.entidades.Rol;
import com.cromosdatabase.modelo.entidades.Usuario;
import com.cromosdatabase.repositorios.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de Spring Security encargado de cargar los datos del usuario
 * durante el proceso de autenticación.
 *
 * Busca un usuario por su email en base de datos y lo transforma en un
 * objeto UsuarioAuth, que es el formato que Spring Security utiliza
 * internamente.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repositorio de usuarios.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Carga un usuario a partir de su email.
     *
     * Spring Security utiliza este métod durante el proceso de autenticación
     * para recuperar los datos del usuario que intenta iniciar sesión.
     *
     * @param email email del usuario a autenticarse
     * @return usuario adaptado al formato UserDetails
     * @throws UsernameNotFoundException si no existe ningún usuario con ese email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe ningún usuario con el email: " + email));

        return convertirAUsuarioAuth(usuario);
    }

    /**
     * Convierte una entidad Usuario en un objeto UsuarioAuth.
     *
     * @param usuario entidad usuario obtenida de base de datos
     * @return objeto UsuarioAuth con los datos necesarios para Spring Security
     */
    private UsuarioAuth convertirAUsuarioAuth(Usuario usuario) {
        return new UsuarioAuth(
                usuario.getIdUsuario(),
                usuario.getEmail(),
                usuario.getNombreMostrar(),
                usuario.getContrasena(),
                usuario.isActivo(),
                obtenerAuthorities(usuario)
        );
    }

    /**
     * Obtiene las authorities de Spring Security a partir de los roles del usuario.
     *
     * Cada rol de base de datos se transforma en un objeto SimpleGrantedAuthority.
     *
     * @param usuario usuario del que se obtienen los roles
     * @return colección de authorities del usuario
     */
    private Collection<? extends GrantedAuthority> obtenerAuthorities(Usuario usuario) {

        // Lista donde iremos guardando las authorities
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Recorremos cada relación usuario-rol (tabla intermedia)
        for (UsuarioRol usuarioRol : usuario.getUsuariosRoles()) {

            // Obtenemos el rol asociado
            Rol rol = usuarioRol.getRol();

            // Obtenemos el nombre del rol (Ej: ROLE_ADMIN)
            String nombreRol = rol.getNombre();

            // Creamos una authority de Spring Security con ese nombre
            GrantedAuthority authority = new SimpleGrantedAuthority(nombreRol);

            // La añadimos a la lista
            authorities.add(authority);
        }

        // Devolvemos la lista completa
        return authorities;
    }
}
