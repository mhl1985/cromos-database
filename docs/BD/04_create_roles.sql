-- =========================================================
-- Script: 04_create_roles.sql
--
-- Descripción:
--   Crea la tabla de roles del sistema.
-- =========================================================

CREATE TABLE IF NOT EXISTS roles (

    id_rol INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    descripcion VARCHAR(255),

    -- Clave primaria
    CONSTRAINT pk_roles
        PRIMARY KEY (id_rol),

    -- Evita roles duplicados
    CONSTRAINT uk_roles_nombre
        UNIQUE (nombre)
);