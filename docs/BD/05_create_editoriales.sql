-- =========================================================
-- Script: 05_create_editoriales.sql
--
-- Descripción:
--   Crea la tabla de editoriales (empresas comercializadoras de colecciones).
-- =========================================================

CREATE TABLE IF NOT EXISTS editoriales (

    id_editorial INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),

    -- Clave primaria
    CONSTRAINT pk_editoriales
        PRIMARY KEY (id_editorial),

    -- Evita editoriales duplicadas
    CONSTRAINT uk_editoriales_nombre
        UNIQUE (nombre)
);