-- =========================================================
-- Script: 08_create_colecciones.sql
--
-- Descripción:
--   Crea la tabla de colecciones.
--   Almacena la información general de 
--   cada colección de cromos.
-- =========================================================

CREATE TABLE IF NOT EXISTS colecciones (

    id_coleccion INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    id_editorial INT NOT NULL,
    id_categoria INT NOT NULL,
    id_subcategoria INT NOT NULL,
    anio VARCHAR(20),
    pais VARCHAR(50),
    descripcion VARCHAR(255),
    url_img_portada VARCHAR(255),

    -- Clave primaria
    CONSTRAINT pk_colecciones
        PRIMARY KEY (id_coleccion),

    -- Relación con editoriales
    CONSTRAINT fk_colecciones_editorial
        FOREIGN KEY (id_editorial)
        REFERENCES editoriales (id_editorial),

    -- Relación con categorías
    CONSTRAINT fk_colecciones_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categorias_coleccion (id_categoria),

    -- Relación compuesta con subcategorías
    -- Garantiza coherencia entre categoría y subcategoría
    CONSTRAINT fk_colecciones_subcategoria_categoria
        FOREIGN KEY (id_subcategoria, id_categoria)
        REFERENCES subcategorias_coleccion (id_subcategoria, id_categoria)
);