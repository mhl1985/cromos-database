-- =========================================================
-- Script: 01_create_database.sql
--
-- Descripción:
--   Crea la base de datos del proyecto.
--
-- Características:
--   utf8mb4: Para soportar correctamente Unicode.
--   utf8mb4_spanish_ci: Reglas de ordenación y comparación para textos
--   en español. Ignora diferencias entre mayúsculas/minúsculas y
--   trata correctamente caracteres como acentos y la letra ñ.
-- =========================================================

CREATE DATABASE IF NOT EXISTS cromos_database
CHARACTER SET utf8mb4
COLLATE utf8mb4_spanish_ci;