package com.cromosdatabase.repositorios;

import com.cromosdatabase.modelo.entidades.UsuarioRol;
import com.cromosdatabase.modelo.entidades.UsuarioRolId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad UsuarioRol.
 */
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolId> {
}
