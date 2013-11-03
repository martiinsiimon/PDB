

DROP TABLE soil_type CASCADE CONSTRAINTS;
DROP TABLE soil CASCADE CONSTRAINTS;
DROP TABLE paths CASCADE CONSTRAINTS;
DROP TABLE water CASCADE CONSTRAINTS;
DROP TABLE fences CASCADE CONSTRAINTS;
DROP TABLE signs CASCADE CONSTRAINTS;
DROP TABLE bed_type CASCADE CONSTRAINTS;
DROP TABLE beds CASCADE CONSTRAINTS;
DROP TABLE plants CASCADE CONSTRAINTS;
DROP TABLE layers CASCADE CONSTRAINTS;

--------------------------------------------------------------------------------
-- LAYERS --
--------------------------------------------------------------------------------

CREATE TABLE layers (

    id INT PRIMARY KEY,
    name VARCHAR2(16)

);

DROP SEQUENCE id_layers_seq;
CREATE SEQUENCE id_layers_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- SOIL_TYPE --
--------------------------------------------------------------------------------

CREATE TABLE soil_type (

    id INT PRIMARY KEY,
    name VARCHAR2(16)

);

DROP SEQUENCE id_soil_type_seq;
CREATE SEQUENCE id_soil_type_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- SOIL --
--------------------------------------------------------------------------------

CREATE TABLE soil (

    id INT PRIMARY KEY,
    layer INT,
    soil_type INT,
    geometry SDO_GEOMETRY,
    FOREIGN KEY (layer) REFERENCES layers(id),
    FOREIGN KEY (soil_type) REFERENCES soil_type(id)

);

DROP SEQUENCE id_soil_seq;
CREATE SEQUENCE id_soil_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- PATHS --
--------------------------------------------------------------------------------

CREATE TABLE paths (

    id INT PRIMARY KEY,
    layer INT,
    geometry SDO_GEOMETRY,
    FOREIGN KEY (layer) REFERENCES layers(id)

);

DROP SEQUENCE id_paths_seq;
CREATE SEQUENCE id_paths_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- WATER --
--------------------------------------------------------------------------------

CREATE TABLE water (

    id INT PRIMARY KEY,
    layer INT,
    geometry SDO_GEOMETRY,
    FOREIGN KEY (layer) REFERENCES layers(id)

);

DROP SEQUENCE id_water_seq;
CREATE SEQUENCE id_water_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- FENCES --
--------------------------------------------------------------------------------

CREATE TABLE fences (

    id INT PRIMARY KEY,
    layer INT,
    geometry SDO_GEOMETRY,
    date_from DATE,
    date_to DATE,
    FOREIGN KEY (layer) REFERENCES layers(id)

);

DROP SEQUENCE id_fences_seq;
CREATE SEQUENCE id_fences_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- PLANTS --
--------------------------------------------------------------------------------

CREATE TABLE plants(

    id INT PRIMARY KEY,
    name VARCHAR2(16),
    photo ORDSYS.ORDIMAGE,
    photo_sig ORDSYS.ORDIMAGESIGNATURE

);

DROP SEQUENCE id_plants_seq;
CREATE SEQUENCE id_plants_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- SIGNS --
--------------------------------------------------------------------------------

CREATE TABLE signs (

    id INT PRIMARY KEY,
    layer INT,
    geometry SDO_GEOMETRY,
    description VARCHAR2(128),
    plant INT,
    date_from DATE,
    date_to DATE,
    FOREIGN KEY (layer) REFERENCES layers(id),
    FOREIGN KEY (plant) REFERENCES plants(id)

);

DROP SEQUENCE id_signs_seq;
CREATE SEQUENCE id_signs_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- BED_TYPE --
--------------------------------------------------------------------------------

CREATE TABLE bed_type (

    id INT PRIMARY KEY,
    name VARCHAR2(16)

);

DROP SEQUENCE id_bed_type_seq;
CREATE SEQUENCE id_bed_type_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

--------------------------------------------------------------------------------
-- BEDS --
--------------------------------------------------------------------------------

CREATE TABLE beds (

    id INT PRIMARY KEY,
    layer INT,
    bed_type INT,
    geometry SDO_GEOMETRY,
    plant INT,
    date_from DATE,
    date_to DATE,
    FOREIGN KEY (layer) REFERENCES layers(id),
    FOREIGN KEY (bed_type) REFERENCES bed_type(id),
    FOREIGN KEY (plant) REFERENCES plants(id)

);

DROP SEQUENCE id_beds_seq;
CREATE SEQUENCE id_beds_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;



-- Data: --

//
