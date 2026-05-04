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