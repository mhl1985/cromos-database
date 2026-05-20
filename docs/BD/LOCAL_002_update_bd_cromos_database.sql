-- =========================================================
-- Script: 002_update_bd_cromos_database.sql
--   Ejecutar después del insert inicial.
-- =========================================================

USE cromos_database;

-- =========================================================
-- IMGs GENÉRICAS POR CATEGORÍA
-- =========================================================

UPDATE colecciones
SET url_img_portada = 'img/muestras/portada_categoria_deportes.webp'
WHERE id_categoria = 1;

UPDATE colecciones
SET url_img_portada = 'img/muestras/portada_categoria_anime.webp'
WHERE id_categoria = 2;

UPDATE colecciones
SET url_img_portada = 'img/muestras/portada_categoria_cine_television.webp'
WHERE id_categoria = 3;

UPDATE colecciones
SET url_img_portada = 'img/muestras/portada_categoria_videojuegos.webp'
WHERE id_categoria = 4;

UPDATE colecciones
SET url_img_portada = 'img/muestras/portada_categoria_historia_cultura.webp'
WHERE id_categoria = 5;

-- =========================================================
-- IMGs PORTADAS REALES - COLECCIONES DE FÚTBOL
-- =========================================================
UPDATE colecciones
SET url_img_portada = 'img/colecciones/1_panini-fifa-world-cup-qatar-2022_portada.webp'
WHERE id_coleccion = 1;

UPDATE colecciones
SET url_img_portada = 'img/colecciones/2_panini-fifa-world-cup-russia-2018_portada.webp'
WHERE id_coleccion = 2;

UPDATE colecciones
SET url_img_portada = 'img/colecciones/3_panini-fifa-world-cup-brazil-2014_portada.webp'
WHERE id_coleccion = 3;

UPDATE colecciones
SET url_img_portada = 'img/colecciones/4_panini-fifa-world-cup-france-98_portada.webp'
WHERE id_coleccion = 4;

UPDATE colecciones
SET url_img_portada = 'img/colecciones/5_panini-fifa-world-cup-usa-94_portada.webp'
WHERE id_coleccion = 5;

