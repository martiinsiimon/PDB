

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

DELETE FROM user_sdo_geom_metadata;

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

INSERT INTO layers VALUES (
    id_layers_seq.NEXTVAL, 'soil'
);

INSERT INTO layers VALUES (
    id_layers_seq.NEXTVAL, 'water'
);

INSERT INTO layers VALUES (
    id_layers_seq.NEXTVAL, 'paths'
);

INSERT INTO layers VALUES (
    id_layers_seq.NEXTVAL, 'beds'
);

INSERT INTO layers VALUES (
    id_layers_seq.NEXTVAL, 'fences'
);

INSERT INTO layers VALUES (
    id_layers_seq.NEXTVAL, 'signs'
);
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

INSERT INTO soil_type VALUES (
    id_soil_type_seq.NEXTVAL, 'black soil'
);

INSERT INTO soil_type VALUES (
    id_soil_type_seq.NEXTVAL, 'brown soil'
);

INSERT INTO soil_type VALUES (
    id_soil_type_seq.NEXTVAL, 'sand'
);

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

INSERT INTO user_sdo_geom_metadata values (
  'soil', 'geometry',
  sdo_dim_array(sdo_dim_element('X', 0, 1000, 1),
  sdo_dim_element('Y', 0, 700, 1)),
  NULL);

-- Data: --

INSERT INTO soil VALUES (
    id_soil_seq.nextval,
    (select id from layers where name = 'soil'),
    (select id from soil_type where name = 'black soil'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 3),
        SDO_ORDINATE_ARRAY(0,300, 820,700))
);

INSERT INTO soil VALUES (
    id_soil_seq.nextval,
    (select id from layers where name = 'soil'),
    (select id from soil_type where name = 'brown soil'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 3),
        SDO_ORDINATE_ARRAY(0,0, 1000,300))
);

INSERT INTO soil VALUES (
    id_soil_seq.nextval,
    (select id from layers where name = 'soil'),
    (select id from soil_type where name = 'sand'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 3),
        SDO_ORDINATE_ARRAY(820,300, 1000,700))
);

-- Validation: --

SELECT s.id, s.geometry.ST_IsValid() FROM soil s;

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

-- TODO: invalid data?

INSERT INTO paths VALUES (
    id_paths_seq.nextval,
    (select id from layers where name = 'paths'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(0,800, 800,490, 430,490, 430,140, 0,140, 0,160,
            410,160, 410,490, 0,490, 0,510, 820,510, 820,0))
);

-- Validation: --

SELECT p.id, p.geometry.ST_IsValid() FROM paths p;

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

INSERT INTO water VALUES (
    id_water_seq.nextval,
    (select id from layers where name = 'water'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(210,444, 385,343, 210,244))
);

-- Validation: --

SELECT w.id, w.geometry.ST_IsValid() FROM water w;

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

INSERT INTO fences VALUES (
    id_fences_seq.nextval,
    (select id from layers where name = 'fences'),
    SDO_GEOMETRY(2002, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 2, 1),
        SDO_ORDINATE_ARRAY(15,525, 210,525, 210,575)),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO fences VALUES (
    id_fences_seq.nextval,
    (select id from layers where name = 'fences'),
    SDO_GEOMETRY(2002, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 2, 1),
        SDO_ORDINATE_ARRAY(105,425, 140,475, 260,475, 300,430)),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

-- Validation: --

SELECT f.id, f.geometry.ST_IsValid() FROM fences f;

--------------------------------------------------------------------------------
-- PLANTS --
--------------------------------------------------------------------------------

CREATE TABLE plants(

    id INT PRIMARY KEY,
    name VARCHAR2(64),
    photo ORDSYS.ORDIMAGE,
    photo_sig ORDSYS.ORDIMAGESIGNATURE

);

DROP SEQUENCE id_plants_seq;
CREATE SEQUENCE id_plants_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

-- Data: --

INSERT INTO plants VALUES (
    id_plants_seq.nextval, 'genericka kvetina', NULL, NULL
);

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

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(158, 517)),
    'Popis genericke kvetiny',
    (select id from plants where name = 'genericka kvetina'),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(197, 482)),
    'Popis genericke kvetiny',
    (select id from plants where name = 'genericka kvetina'),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(302, 517)),
    'Popis genericke kvetiny',
    (select id from plants where name = 'genericka kvetina'),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

-- Validation: --

SELECT s.id, s.geometry.ST_IsValid() FROM signs s;

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

INSERT INTO bed_type VALUES (
    id_bed_type_seq.NEXTVAL, 'flower'
);

INSERT INTO bed_type VALUES (
    id_bed_type_seq.NEXTVAL, 'tree'
);

INSERT INTO bed_type VALUES (
    id_bed_type_seq.NEXTVAL, 'bush'
);

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

-- TODO: invalid data? --

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    (select id from bed_type where name = 'flower'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(15,525, 210,525, 210,685, 15,685)),
    (select id from plants where name = 'genericka kvetina'),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    (select id from bed_type where name = 'flower'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(95,410, 315,415, 260,475, 140,475)),
    (select id from plants where name = 'genericka kvetina'),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    (select id from bed_type where name = 'tree'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(268,550, 280,538, 268,525)),
    (select id from plants where name = 'genericka kvetina'),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    (select id from bed_type where name = 'bush'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(355,212, 385,192, 355,172)),
    (select id from plants where name = 'genericka kvetina'),
    TO_DATE('08-04-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

-- Validation: --

SELECT b.id, b.geometry.ST_IsValid() FROM beds b;

//
