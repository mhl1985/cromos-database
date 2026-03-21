-- =========================================================
-- Script: 09_create_cromos.sql
--
-- Descripción:
--   Crea la tabla de cromos.
--   Almacena los cromos que pertenecen a una colección.
-- =========================================================

CREATE TABLE IF NOT EXISTS cromos (

    id_cromo INT NOT NULL AUTO_INCREMENT,
    id_coleccion INT NOT NULL,
    numero VARCHAR(20) NOT NULL,
    nombre VARCHAR(100),
    tipo VARCHAR(50),
    descripcion VARCHAR(255),
    url_img_delantera VARCHAR(255),
    url_img_trasera VARCHAR(255),

    -- Clave primaria
    CONSTRAINT pk_cromos
        PRIMARY KEY (id_cromo),

    -- Relación con colecciones
    CONSTRAINT fk_cromos_coleccion
        FOREIGN KEY (id_coleccion)
        REFERENCES colecciones (id_coleccion),

    -- Evita duplicar el mismo número dentro de una colección
    CONSTRAINT uk_cromos_coleccion_numero
        UNIQUE (id_coleccion, numero)
);