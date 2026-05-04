-- =========================================================
-- Script: 03_create_usuarios.sql
--
-- Descripción:
--   Crea la tabla de usuarios del sistema.
-- =========================================================

CREATE TABLE IF NOT EXISTS usuarios (

    id_usuario INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(80) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    nombre_mostrar VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Clave primaria
    CONSTRAINT pk_usuarios
        PRIMARY KEY (id_usuario),

    -- Evita que dos usuarios tengan el mismo email
    CONSTRAINT uk_usuarios_email
        UNIQUE (email),

    -- Evita que dos usuarios tengan el mismo nombre visible
    CONSTRAINT uk_usuarios_nombre_mostrar
        UNIQUE (nombre_mostrar)
);