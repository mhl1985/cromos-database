-- =========================================================
-- IMPORTANTE (ENTORNO AIVEN / BD REMOTA)
--
-- En este entorno NO se crea ni se selecciona la base de datos
-- porque el proveedor (Aiven) ya proporciona una BD existente:
--
-- Nombre BD: defaultdb
--
-- Por tanto:
-- - NO ejecutar DROP DATABASE
-- - NO ejecutar CREATE DATABASE
-- - NO ejecutar USE cromos_database
--
-- Todas las tablas se crearán directamente en defaultdb.
-- =========================================================

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
    periodo VARCHAR(20),
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

-- =========================================================
-- Script: 12_create_usuarios_cromos.sql
--
-- Descripción:
--   Crea la tabla intermedia entre usuarios y cromos.
--   Permite almacenar los cromos que tiene cada usuario,
--   incluyendo cantidades y cromos disponibles para intercambio.
--   Relación muchos a muchos (N:M).
-- =========================================================

CREATE TABLE IF NOT EXISTS usuarios_cromos (

    id_usuario INT NOT NULL,
    id_cromo INT NOT NULL,
    cantidad_total INT NOT NULL DEFAULT 0,
    cantidad_intercambiable INT NOT NULL DEFAULT 0,
    observaciones VARCHAR(255),

    -- Clave primaria compuesta (evita duplicados)
    CONSTRAINT pk_usuarios_cromos
        PRIMARY KEY (id_usuario, id_cromo),

    -- Relación con usuarios
    CONSTRAINT fk_usuarios_cromos_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios (id_usuario),

    -- Relación con cromos
    CONSTRAINT fk_usuarios_cromos_cromo
        FOREIGN KEY (id_cromo)
        REFERENCES cromos (id_cromo),

    -- No puede haber cantidades negativas
    CONSTRAINT chk_usuarios_cromos_numtotal_no_negativo
        CHECK (cantidad_total >= 0),
    CONSTRAINT chk_usuarios_cromos_numintercambiable_no_negativo
        CHECK (cantidad_intercambiable >= 0),

    -- No se pueden intercambiar más cromos de los que se tienen
    CONSTRAINT chk_usuarios_cromos_intercambiable_menor_igual_total
        CHECK (cantidad_intercambiable <= cantidad_total)
);

-- =========================================================
-- FIN 
-- =========================================================