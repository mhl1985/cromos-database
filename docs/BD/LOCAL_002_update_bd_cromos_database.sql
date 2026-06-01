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

-- =========================================================
-- IMGs CROMOS REALES - (ID 1) - QATAR 2022
-- =========================================================

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/1_00_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/1_00_trasera.webp'
WHERE id_cromo = 1;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/2_FWC1_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/2_FWC1_trasera.webp'
WHERE id_cromo = 2;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/3_FWC2_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/3_FWC2_trasera.webp'
WHERE id_cromo = 3;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/4_FWC3_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/4_FWC3_trasera.webp'
WHERE id_cromo = 4;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/5_FWC4_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/5_FWC4_trasera.webp'
WHERE id_cromo = 5;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/6_FWC5_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/6_FWC5_trasera.webp'
WHERE id_cromo = 6;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/7_FWC6_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/7_FWC6_trasera.webp'
WHERE id_cromo = 7;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/8_FWC7_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/8_FWC7_trasera.webp'
WHERE id_cromo = 8;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/9_FWC8_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/9_FWC8_trasera.webp'
WHERE id_cromo = 9;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/10_FWC9_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/10_FWC9_trasera.webp'
WHERE id_cromo = 10;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/11_FWC10_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/11_FWC10_trasera.webp'
WHERE id_cromo = 11;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/12_FWC11_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/12_FWC11_trasera.webp'
WHERE id_cromo = 12;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/13_FWC12_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/13_FWC12_trasera.webp'
WHERE id_cromo = 13;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/14_FWC13_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/14_FWC13_trasera.webp'
WHERE id_cromo = 14;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/15_FWC14_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/15_FWC14_trasera.webp'
WHERE id_cromo = 15;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/16_FWC15_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/16_FWC15_trasera.webp'
WHERE id_cromo = 16;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/17_FWC16_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/17_FWC16_trasera.webp'
WHERE id_cromo = 17;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/18_FWC17_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/18_FWC17_trasera.webp'
WHERE id_cromo = 18;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/19_FWC18_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/19_FWC18_trasera.webp'
WHERE id_cromo = 19;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/20_QAT1_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/20_QAT1_trasera.webp'
WHERE id_cromo = 20;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/81_ARG20_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/81_ARG20_trasera.webp'
WHERE id_cromo = 81;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/84_FRA19_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/84_FRA19_trasera.webp'
WHERE id_cromo = 84;

UPDATE cromos
SET url_img_delantera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/87_POR18_delantera.webp',
    url_img_trasera = 'img/cromos/1_panini-fifa-world-cup-qatar-2022/87_POR18_trasera.webp'
WHERE id_cromo = 87;

-- =========================================================
-- IMGs CROMOS REALES - (ID 2) - RUSSIA 2018
-- =========================================================

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/101_00_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/101_00_trasera.webp'
WHERE id_cromo = 101;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/102_1_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/102_1_trasera.webp'
WHERE id_cromo = 102;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/103_2_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/103_2_trasera.webp'
WHERE id_cromo = 103;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/104_3_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/104_3_trasera.webp'
WHERE id_cromo = 104;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/105_4_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/105_4_trasera.webp'
WHERE id_cromo = 105;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/106_5_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/106_5_trasera.webp'
WHERE id_cromo = 106;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/107_6_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/107_6_trasera.webp'
WHERE id_cromo = 107;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/108_7_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/108_7_trasera.webp'
WHERE id_cromo = 108;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/109_8_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/109_8_trasera.webp'
WHERE id_cromo = 109;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/110_9_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/110_9_trasera.webp'
WHERE id_cromo = 110;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/111_10_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/111_10_trasera.webp'
WHERE id_cromo = 111;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/112_11_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/112_11_trasera.webp'
WHERE id_cromo = 112;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/113_12_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/113_12_trasera.webp'
WHERE id_cromo = 113;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/114_13_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/114_13_trasera.webp'
WHERE id_cromo = 114;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/115_14_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/115_14_trasera.webp'
WHERE id_cromo = 115;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/116_15_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/116_15_trasera.webp'
WHERE id_cromo = 116;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/117_16_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/117_16_trasera.webp'
WHERE id_cromo = 117;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/118_17_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/118_17_trasera.webp'
WHERE id_cromo = 118;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/119_18_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/119_18_trasera.webp'
WHERE id_cromo = 119;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/120_19_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/120_19_trasera.webp'
WHERE id_cromo = 120;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/181_130_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/181_130_trasera.webp'
WHERE id_cromo = 181;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/185_209_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/185_209_trasera.webp'
WHERE id_cromo = 185;

UPDATE cromos
SET url_img_delantera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/187_288_delantera.webp',
    url_img_trasera = 'img/cromos/2_panini-fifa-world-cup-russia-2018/187_288_trasera.webp'
WHERE id_cromo = 187;

-- =========================================================
-- IMGs CROMOS REALES - (ID 4) - FRANCE 98
-- =========================================================

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/301_1_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/301_1_trasera.webp'
WHERE id_cromo = 301;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/302_2_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/302_2_trasera.webp'
WHERE id_cromo = 302;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/303_3_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/303_3_trasera.webp'
WHERE id_cromo = 303;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/304_4_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/304_4_trasera.webp'
WHERE id_cromo = 304;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/305_5_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/305_5_trasera.webp'
WHERE id_cromo = 305;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/306_6_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/306_6_trasera.webp'
WHERE id_cromo = 306;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/307_7_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/307_7_trasera.webp'
WHERE id_cromo = 307;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/308_8_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/308_8_trasera.webp'
WHERE id_cromo = 308;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/309_9_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/309_9_trasera.webp'
WHERE id_cromo = 309;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/310_10_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/310_10_trasera.webp'
WHERE id_cromo = 310;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/311_11_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/311_11_trasera.webp'
WHERE id_cromo = 311;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/312_12_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/312_12_trasera.webp'
WHERE id_cromo = 312;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/313_13_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/313_13_trasera.webp'
WHERE id_cromo = 313;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/314_14_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/314_14_trasera.webp'
WHERE id_cromo = 314;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/315_15_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/315_15_trasera.webp'
WHERE id_cromo = 315;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/316_16_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/316_16_trasera.webp'
WHERE id_cromo = 316;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/317_17_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/317_17_trasera.webp'
WHERE id_cromo = 317;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/318_18_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/318_18_trasera.webp'
WHERE id_cromo = 318;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/319_19_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/319_19_trasera.webp'
WHERE id_cromo = 319;

UPDATE cromos
SET url_img_delantera = 'img/cromos/4_panini-fifa-world-cup-france-98/320_20_delantera.webp',
    url_img_trasera = 'img/cromos/4_panini-fifa-world-cup-france-98/320_20_trasera.webp'
WHERE id_cromo = 320;
