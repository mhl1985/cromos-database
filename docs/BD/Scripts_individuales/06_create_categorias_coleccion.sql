-- =========================================================
-- Script: 06_create_categorias_coleccion.sql
--
-- Descripción:
--   Crea la tabla de categorías de colecciones.
--   Permite clasificar las colecciones en grandes
--   grupos/temáticas (deportes, musica, tv, etc.).
-- =========================================================

CREATE TABLE IF NOT EXISTS categorias_coleccion (

    id_categoria INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),

    -- Clave primaria
    CONSTRAINT pk_categorias_coleccion
        PRIMARY KEY (id_categoria),

    -- Evita categorías duplicadas
    CONSTRAINT uk_categorias_coleccion_nombre
        UNIQUE (nombre)

);