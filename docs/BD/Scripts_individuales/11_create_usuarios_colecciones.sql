-- =========================================================
-- Script: 11_create_usuarios_colecciones.sql
--
-- Descripción:
--   Crea la tabla intermedia entre usuarios y colecciones.
--   Permite asociar usuarios con las colecciones que tienen
--   añadidas en la aplicación.
-- =========================================================

CREATE TABLE IF NOT EXISTS usuarios_colecciones (

    id_usuario INT NOT NULL,
    id_coleccion INT NOT NULL,
    fecha_agregada DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Clave primaria compuesta
    CONSTRAINT pk_usuarios_colecciones
        PRIMARY KEY (id_usuario, id_coleccion),

    -- Relación con usuarios
    CONSTRAINT fk_usuarios_colecciones_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios (id_usuario),

    -- Relación con colecciones
    CONSTRAINT fk_usuarios_colecciones_coleccion
        FOREIGN KEY (id_coleccion)
        REFERENCES colecciones (id_coleccion)
);