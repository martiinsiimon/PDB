

DROP TABLE soil_type CASCADE CONSTRAINTS;
DROP TABLE soil CASCADE CONSTRAINTS;
DROP TABLE paths CASCADE CONSTRAINTS;
DROP TABLE water CASCADE CONSTRAINTS;
DROP TABLE fences CASCADE CONSTRAINTS;
DROP TABLE signs CASCADE CONSTRAINTS;
DROP TABLE beds CASCADE CONSTRAINTS;
DROP TABLE plants CASCADE CONSTRAINTS;
DROP TABLE plant_type CASCADE CONSTRAINTS;
DROP TABLE layers CASCADE CONSTRAINTS;

DELETE FROM user_sdo_geom_metadata;

--------------------------------------------------------------------------------
-- LAYERS -- [done]
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
-- SOIL_TYPE -- [done]
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
-- SOIL -- [done]
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

INSERT INTO user_sdo_geom_metadata values (
  'beds', 'geometry',
  sdo_dim_array(sdo_dim_element('X', 0, 1000, 1),
  sdo_dim_element('Y', 0, 700, 1)),
  NULL);

INSERT INTO user_sdo_geom_metadata values (
  'fences', 'geometry',
  sdo_dim_array(sdo_dim_element('X', 0, 1000, 1),
  sdo_dim_element('Y', 0, 700, 1)),
  NULL);

INSERT INTO user_sdo_geom_metadata values (
  'paths', 'geometry',
  sdo_dim_array(sdo_dim_element('X', 0, 1000, 1),
  sdo_dim_element('Y', 0, 700, 1)),
  NULL);

INSERT INTO user_sdo_geom_metadata values (
  'signs', 'geometry',
  sdo_dim_array(sdo_dim_element('X', 0, 1000, 1),
  sdo_dim_element('Y', 0, 700, 1)),
  NULL);
  
  INSERT INTO user_sdo_geom_metadata values (
  'water', 'geometry',
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
-- PATHS -- [done]
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

INSERT INTO paths VALUES (
    id_paths_seq.nextval,
    (select id from layers where name = 'paths'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(800,0, 820,0, 820,510, 0,510, 0,490, 410,490, 410,160,
            0,160, 0,140, 430,140, 430,490, 800,490, 800,0))
);

-- Validation: --

SELECT p.id, p.geometry.ST_IsValid() FROM paths p;

--------------------------------------------------------------------------------
-- WATER -- [done]
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
-- FENCES -- [done]
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
    TO_DATE('04-18-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO fences VALUES (
    id_fences_seq.nextval,
    (select id from layers where name = 'fences'),
    SDO_GEOMETRY(2002, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 2, 1),
        SDO_ORDINATE_ARRAY(105,425, 140,475, 260,475, 300,430)),
    TO_DATE('05-08-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO fences VALUES (
    id_fences_seq.nextval,
    (select id from layers where name = 'fences'),
    SDO_GEOMETRY(2002, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 2, 1),
        SDO_ORDINATE_ARRAY(555,525, 800,525)),
    TO_DATE('04-05-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO fences VALUES (
    id_fences_seq.nextval,
    (select id from layers where name = 'fences'),
    SDO_GEOMETRY(2002, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 2, 1),
        SDO_ORDINATE_ARRAY(835,685, 835,420, 985,475)),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO fences VALUES (
    id_fences_seq.nextval,
    (select id from layers where name = 'fences'),
    SDO_GEOMETRY(2002, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 2, 1),
        SDO_ORDINATE_ARRAY(985,460, 835,405, 835,315, 985,315)),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

-- Validation: --

SELECT f.id, f.geometry.ST_IsValid() FROM fences f;

--------------------------------------------------------------------------------
-- PLANT_TYPE -- [done]
--------------------------------------------------------------------------------

CREATE TABLE plant_type (

    id INT PRIMARY KEY,
    name VARCHAR2(16)

);

DROP SEQUENCE id_plant_type_seq;
CREATE SEQUENCE id_plant_type_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

-- Data: --

INSERT INTO plant_type VALUES (
    id_plant_type_seq.NEXTVAL, 'flower'
);

INSERT INTO plant_type VALUES (
    id_plant_type_seq.NEXTVAL, 'tree'
);

INSERT INTO plant_type VALUES (
    id_plant_type_seq.NEXTVAL, 'bush'
);

--------------------------------------------------------------------------------
-- PLANTS -- [done]
--------------------------------------------------------------------------------

CREATE TABLE plants(

    id INT PRIMARY KEY,
    name VARCHAR2(64),
    plant_type INT,
    photo ORDSYS.ORDIMAGE,
    photo_thumb ORDSYS.ORDIMAGE,
    photo_si ORDSYS.SI_StillImage,
    photo_ac ORDSYS.SI_AverageColor,
    photo_ch ORDSYS.SI_ColorHistogram,
    photo_pc ORDSYS.SI_PositionalColor,
    photo_tx ORDSYS.SI_Texture,
    FOREIGN KEY (plant_type) REFERENCES plant_type(id)
);

DROP SEQUENCE id_plants_seq;
CREATE SEQUENCE id_plants_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

-- Data: --

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'borovice lesní', (select id from plant_type where name = 'tree'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'buk lesní', (select id from plant_type where name = 'tree'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'chryzantéma zahradní', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'dub letní', (select id from plant_type where name = 'tree'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'gerbera zahradní', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'kaktus pichlavý', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'kopretina bílá', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'krokus žlutý', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'leknín bílý', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'lilie zahradní', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'lípa malolistá', (select id from plant_type where name = 'tree'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'narcis žlutý', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'rákos obecný', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'růže lesklá', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'sekvoj vždyzelená', (select id from plant_type where name = 'tree'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'smrk ztepilý', (select id from plant_type where name = 'tree'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'sněženka podsněžník', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'netřesk střešní', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'puchýřka útlá', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'túje obecná', (select id from plant_type where name = 'bush'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'tulipán zahradní', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'réva vinná', (select id from plant_type where name = 'bush'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

INSERT INTO plants (id, name, plant_type, photo, photo_thumb) VALUES (
    id_plants_seq.nextval, 'mák vlčí', (select id from plant_type where name = 'flower'), ordsys.ordimage.init(), ordsys.ordimage.init()
);

--------------------------------------------------------------------------------
-- SIGNS -- [done]
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
    'Země původu: Česká republika',
    (select id from plants where name = 'kopretina bílá'),
    TO_DATE('04-08-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(302, 517)),
    'Země původu: Česká repbulika',
    (select id from plants where name = 'smrk ztepilý'),
    TO_DATE('11-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(483, 517)),
    'Země původu: Slovenská republika',
    (select id from plants where name = 'borovice lesní'),
    TO_DATE('11-08-2010', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(702, 517)),
    'Země původu: Itálie',
    (select id from plants where name = 'tulipán zahradní'),
    TO_DATE('07-15-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(828, 492)),
    'Země původu: Řecko',
    (select id from plants where name = 'kaktus pichlavý'),
    TO_DATE('05-13-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(828, 357)),
    'Země původu: Rakousko',
    (select id from plants where name = 'netřesk střešní'),
    TO_DATE('12-08-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(198, 482)),
    'Země původu: Česká republika',
    (select id from plants where name = 'rákos obecný'),
    TO_DATE('02-22-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(512, 482)),
    'Země původu: Slovenská republika',
    (select id from plants where name = 'růže lesklá'),
    TO_DATE('04-22-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(708, 482)),
    'Země původu: Slovenská republika',
    (select id from plants where name = 'sněženka podsněžník'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(827, 218)),
    'Země původu: Španělsko',
    (select id from plants where name = 'túje obecná'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(827, 116)),
    'Země původu: Slovenská republika',
    (select id from plants where name = 'buk lesní'),
    TO_DATE('10-11-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(792, 42)),
    'Země původu: Česká republika',
    (select id from plants where name = 'lípa malolistá'),
    TO_DATE('11-11-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(82, 132)),
    'Země původu: Slovenská republika',
    (select id from plants where name = 'gerbera zahradní'),
    TO_DATE('05-11-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(248, 132)),
    'Země původu: Slovenská republika',
    (select id from plants where name = 'mák vlčí'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(318, 167)),
    'Země původu: Česká republika',
    (select id from plants where name = 'réva vinná'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(398, 132)),
    'Země původu: Německo',
    (select id from plants where name = 'dub letní'),
    TO_DATE('07-01-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO signs VALUES (
    id_signs_seq.nextval,
    (select id from layers where name = 'signs'),
    SDO_GEOMETRY(2001, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1 ,1 ,1),
        SDO_ORDINATE_ARRAY(398, 286)),
    'Země původu: Kanada',
    (select id from plants where name = 'leknín bílý'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);


-- Validation: --

SELECT s.id, s.geometry.ST_IsValid() FROM signs s;

--------------------------------------------------------------------------------
-- BEDS -- [data]
--------------------------------------------------------------------------------

CREATE TABLE beds (

    id INT PRIMARY KEY,
    layer INT,
    geometry SDO_GEOMETRY,
    plant INT,
    date_from DATE,
    date_to DATE,
    FOREIGN KEY (layer) REFERENCES layers(id),
    FOREIGN KEY (plant) REFERENCES plants(id)

);

DROP SEQUENCE id_beds_seq;
CREATE SEQUENCE id_beds_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

-- Data: --

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(15,525, 210,525, 210,685, 15,685, 15,525)),
    (select id from plants where name = 'kopretina bílá'),
    TO_DATE('04-14-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(95,410, 315,415, 260,475, 140,475, 95,410)),
    (select id from plants where name = 'rákos obecný'),
    TO_DATE('08-16-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(555,525, 800,525, 800,685, 470,685, 555,525)),
    (select id from plants where name = 'tulipán zahradní'),
    TO_DATE('04-19-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(835,420, 985,475, 985,685, 835,685, 835,420)),
    (select id from plants where name = 'kaktus pichlavý'),
    TO_DATE('12-12-2012', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(835,315, 985,315, 985,460, 835,405, 835,315)),
    (select id from plants where name = 'netřesk střešní'),
    TO_DATE('12-13-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(165,260, 280,260, 370,325, 160,325, 95,290, 165,260)),
    (select id from plants where name = 'leknín bílý'),
    TO_DATE('12-13-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(445,140, 600,140, 600,475, 445,475, 445,140)),
    (select id from plants where name = 'růže lesklá'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(630,140, 785,140, 785,475, 630,475, 630,140)),
    (select id from plants where name = 'sněženka podsněžník'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(15,15, 225,15, 185,125, 15,125, 15,15)),
    (select id from plants where name = 'gerbera zahradní'),
    TO_DATE('08-08-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 1),
        SDO_ORDINATE_ARRAY(240,15, 345,15, 345,125, 200,125, 240,15)),
    (select id from plants where name = 'mák vlčí'),
    TO_DATE('06-08-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

--------------------------------------------------------------------------------

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(268,550, 280,538, 268,525)),
    (select id from plants where name = 'smrk ztepilý'),
    TO_DATE('06-23-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(335,560, 347,548, 335,536)),
    (select id from plants where name = 'smrk ztepilý'),
    TO_DATE('04-22-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(460,550, 472,538, 460,527)),
    (select id from plants where name = 'borovice lesní'),
    TO_DATE('01-21-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(505,555, 518,543, 505,532)),
    (select id from plants where name = 'borovice lesní'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(378,125, 388,112, 378,100)),
    (select id from plants where name = 'dub letní'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(415,120, 428,108, 415,96)),
    (select id from plants where name = 'dub letní'),
    TO_DATE('05-08-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(766,74, 778,62, 766,50)),
    (select id from plants where name = 'lípa malolistá'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(767,35, 780,23, 767,10)),
    (select id from plants where name = 'lípa malolistá'),
    TO_DATE('04-08-2011', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(846,148, 858,136, 846,125)),
    (select id from plants where name = 'buk lesní'),
    TO_DATE('11-18-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(856,119, 868,107, 856,95)),
    (select id from plants where name = 'buk lesní'),
    TO_DATE('11-08-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);
--------------------------------------------------------------------------------

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(354,212, 384,192, 354,172)),
    (select id from plants where name = 'réva vinná'),
    TO_DATE('03-11-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(866,270, 896,250, 866,230)),
    (select id from plants where name = 'túje obecná'),
    TO_DATE('04-14-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

INSERT INTO beds VALUES (
    id_beds_seq.nextval,
    (select id from layers where name = 'beds'),
    SDO_GEOMETRY(2003, NULL, NULL,
        SDO_ELEM_INFO_ARRAY(1, 1003, 4),
        SDO_ORDINATE_ARRAY(895,218, 925,198, 895,178)),
    (select id from plants where name = 'túje obecná'),
    TO_DATE('11-01-2013', 'MM-DD-YYYY'),
    TO_DATE('12-31-9999', 'MM-DD-YYYY')
);

-- Validation: --

SELECT b.id, b.geometry.ST_IsValid() FROM beds b;


DROP INDEX beds_index;
CREATE INDEX beds_index ON beds(geometry) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DROP INDEX fences_index;
CREATE INDEX fences_index ON fences(geometry) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DROP INDEX paths_index;
CREATE INDEX paths_index ON paths(geometry) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DROP INDEX signs_index;
CREATE INDEX signs_index ON signs(geometry) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DROP INDEX soil_index;
CREATE INDEX soil_index ON soil(geometry) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DROP INDEX water_index;
CREATE INDEX water_index ON water(geometry) INDEXTYPE IS MDSYS.SPATIAL_INDEX;
//
