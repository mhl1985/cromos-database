-- =========================================================
-- Script: 99_master.sql
--
-- Descripción:
--   Script maestro que ejecuta todos los scripts de creación
--   de la base de datos en el orden correcto.
--   Permite crear toda la estructura de la base de datos
--   ejecutando solo este archivo.
--   Todas las sources deben estar en la misma carpeta que este archivo.
-- =========================================================

SOURCE 01_create_database.sql;
SOURCE 02_use_database.sql;
SOURCE 03_create_usuarios.sql;
SOURCE 04_create_roles.sql;
SOURCE 05_create_editoriales.sql;
SOURCE 06_create_categorias_coleccion.sql;
SOURCE 07_create_subcategorias_coleccion.sql;
SOURCE 08_create_colecciones.sql;
SOURCE 09_create_cromos.sql;
SOURCE 10_create_usuarios_roles.sql;
SOURCE 11_create_usuarios_colecciones.sql;
SOURCE 12_create_usuarios_cromos.sql;