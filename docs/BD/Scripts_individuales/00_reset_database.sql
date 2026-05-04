-- =========================================================
-- Script: 00_reset_database.sql
--
-- Descripción:
--   Elimina completamente la base de datos y la vuelve 
--   a crear desde cero ejecutando el script maestro.
--   El script "master" debe estar en la misma carpeta que este archivo.
-- =========================================================

DROP DATABASE IF EXISTS cromos_database;
SOURCE 99_master.sql;