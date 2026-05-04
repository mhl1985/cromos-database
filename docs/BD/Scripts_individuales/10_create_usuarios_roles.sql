-- =========================================================
-- Script: 10_create_usuarios_roles.sql
--
-- Descripción:
--   Crea la tabla intermedia entre usuarios y roles.
--   Permite asociar uno o varios roles a cada usuario.
--   Relación muchos a muchos (N:M).
-- =========================================================

CREATE TABLE IF NOT EXISTS usuarios_roles (

    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,

    -- Clave primaria compuesta
    CONSTRAINT pk_usuarios_roles
        PRIMARY KEY (id_usuario, id_rol),

    -- Relación con usuarios
    CONSTRAINT fk_usuarios_roles_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios (id_usuario),

    -- Relación con roles
    CONSTRAINT fk_usuarios_roles_rol
        FOREIGN KEY (id_rol)
        REFERENCES roles (id_rol)
);