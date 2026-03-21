-- =========================================================
-- Script: 07_create_subcategorias_coleccion.sql

-- Descripción:
--   Crea la tabla de subcategorías de colección.
--   Permite clasificar las colecciones dentro de una
--      subcategoría concreta (Ejemplo: Fútbol dentro de
--      Deportes, dibujos animados dentro de Tv, etc).
-- =========================================================

CREATE TABLE IF NOT EXISTS subcategorias_coleccion (

    id_subcategoria INT NOT NULL AUTO_INCREMENT,
    id_categoria INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),

    -- Clave primaria
    CONSTRAINT pk_subcategorias_coleccion
        PRIMARY KEY (id_subcategoria),

    -- Relación con la tabla de categorías
    CONSTRAINT fk_subcategorias_coleccion_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categorias_coleccion (id_categoria),

    -- Evita subcategorías duplicadas dentro de la misma categoría
    CONSTRAINT uk_subcategorias_coleccion_categoria_nombre
        UNIQUE (id_categoria, nombre),

    -- Se define como único el par (id_subcategoria, id_categoria) 
    -- para permitir una FK compuesta desde la tabla colecciones.
    CONSTRAINT uk_subcategorias_coleccion_id_subcategoria_id_categoria
        UNIQUE (id_subcategoria, id_categoria)
);