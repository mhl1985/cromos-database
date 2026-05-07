package com.cromosdatabase.servicios.impl;

import com.cromosdatabase.comun.excepciones.EmailDuplicadoException;
import com.cromosdatabase.comun.excepciones.NombreAMostrarDuplicadoException;
import com.cromosdatabase.comun.excepciones.RolNoEncontradoException;
import com.cromosdatabase.modelo.dtos.auth.RegistroUsuarioRequest;
import com.cromosdatabase.modelo.dtos.auth.RegistroUsuarioResponse;
import com.cromosdatabase.modelo.entidades.Rol;
import com.cromosdatabase.modelo.entidades.Usuario;
import com.cromosdatabase.modelo.entidades.UsuarioRol;
import com.cromosdatabase.modelo.entidades.UsuarioRolId;
import com.cromosdatabase.repositorios.RolRepository;
import com.cromosdatabase.repositorios.UsuarioRepository;
import com.cromosdatabase.repositorios.UsuarioRolRepository;
import com.cromosdatabase.servicios.RegistroUsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de registro de usuarios.
 *
 * Contiene la lógica de negocio necesaria para dar de alta
 * un nuevo usuario en la aplicación.
 */
@Service
public class RegistroUsuarioServiceImpl implements RegistroUsuarioService {

    /**
     * Nombre del rol básico que se asignará por defecto
     * a los nuevos usuarios registrados.
     */
    private static final String NOMBRE_ROL_POR_DEFECTO = "ROLE_USER";

    /**
     * Repositorio de usuarios.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Repositorio de roles.
     */
    private final RolRepository rolRepository;

    /**
     * Repositorio de relaciones usuario-rol.
     */
    private final UsuarioRolRepository usuarioRolRepository;

    /**
     * Componente encargado de cifrar contraseñas.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param usuarioRepository repositorio de usuarios
     * @param rolRepository repositorio de roles
     * @param usuarioRolRepository repositorio de relaciones usuario-rol
     * @param passwordEncoder codificador de contraseñas
     */
    public RegistroUsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            UsuarioRolRepository usuarioRolRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
    @Override
    @Transactional
    public RegistroUsuarioResponse registrarUsuario(RegistroUsuarioRequest request) {

        // Comprobamos si ya existe un usuario con el mismo email.
        boolean existeUsuarioConEmail = usuarioRepository.existsByEmail(request.getEmail());

        if (existeUsuarioConEmail) {
            throw new EmailDuplicadoException("Ya existe un usuario con ese email.");
        }

        // Comprobamos si ya existe un usuario con el mismo nombre a mostrar.
        boolean existeUsuarioConNombreMostrar =
                usuarioRepository.existsByNombreMostrar(request.getNombreMostrar());

        if (existeUsuarioConNombreMostrar) {
            throw new NombreAMostrarDuplicadoException("Ya existe un usuario con ese nombre a mostrar.");
        }

        // Recuperamos el rol por defecto que se asignará al nuevo usuario.
        Rol rolPorDefecto = rolRepository.findByNombre(NOMBRE_ROL_POR_DEFECTO)
                .orElseThrow(() -> new RolNoEncontradoException("No existe el rol por defecto."));

        // Creamos la entidad Usuario con los datos recibidos en la petición.
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setNombreMostrar(request.getNombreMostrar());

        // Ciframos la contraseña antes de guardarla en base de datos.
        String contrasenaCifrada = passwordEncoder.encode(request.getContrasena());
        usuario.setContrasena(contrasenaCifrada);

        // Todos los usuarios nuevos se crean activos por defecto.
        usuario.setActivo(true);

        // Guardamos primero el usuario para obtener su id generado por la base de datos.
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Construimos la clave compuesta de la tabla intermedia usuarios_roles.
        UsuarioRolId usuarioRolId = new UsuarioRolId(
                usuarioGuardado.getIdUsuario(),
                rolPorDefecto.getIdRol()
        );

        // Creamos la relación entre el usuario guardado y el rol por defecto.
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(usuarioRolId);
        usuarioRol.setUsuario(usuarioGuardado);
        usuarioRol.setRol(rolPorDefecto);

        // Persistimos la relación usuario-rol en la tabla intermedia.
        usuarioRolRepository.save(usuarioRol);

        // Construimos la respuesta de salida con los datos básicos del usuario creado.
        RegistroUsuarioResponse response = new RegistroUsuarioResponse(
                usuarioGuardado.getIdUsuario(),
                usuarioGuardado.getEmail(),
                usuarioGuardado.getNombreMostrar()
        );

        return response;
    }
}
