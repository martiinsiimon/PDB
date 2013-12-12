-- TODO: VYZKOUSET FUNKCNOST (neovereno)

-- SELECT temporalne promenlivych dat nad jednou tabulkou:
--
-- Vyber informace o rostlinach na cedulich v zahrade v roce 2013
--
VALIDTIME PERIOD[2013-2014) SELECT description FROM signs;

-- SQL:
SELECT description FROM signs
WHERE date_to >= TO_DATE('12-31-2013', 'MM-DD-YYYY') AND
date_from <= TO_DATE('01-01-2013', 'MM-DD-YYYY');


-- SELECT temporalne promenlivych dat nad spojenim vice tabulek (1):
--
-- Vyber nazvy vsech rostlin rostoucich v zahrade mezi roky 2008 a 2012
--
VALIDTIME PERIOD[2008-2012) SELECT plants.name FROM plants INNER JOIN beds ON plants.id=beds.plant;
-- nebo
VALIDTIME PERIOD[2008-2012) SELECT plants.name FROM plants WHERE EXISTS(VALIDTIME PERIOD[2008-2012) SELECT 1 FROM beds WHERE beds.plant=plants.id)

-- SQL:
SELECT plants.name FROM plants
WHERE date_to >= TO_DATE('12-31-2012', 'MM-DD-YYYY') AND
date_from <= TO_DATE('01-01-2008', 'MM-DD-YYYY') AND
EXISTS(
    SELECT 1 FROM beds WHERE beds.plant=plants.id AND
    date_to >= TO_DATE('12-31-2012', 'MM-DD-YYYY') AND
    date_from <= TO_DATE('01-01-2008', 'MM-DD-YYYY'));


-- SELECT temporalne promenlivych dat nad spojenim vice tabulek (2):
--
-- Vyber informace o rostlinach na cedulich u vsech stromu
-- rostoucich v zahrade v lednu roku 2012
--
VALIDTIME PERIOD[2012/01-2012/02) SELECT signs.description FROM signs INNER JOIN beds ON signs.plant=beds.plant WHERE beds.bed_type=2;
-- nebo
VALIDTIME PERIOD[2012/01-2012/02) SELECT sings.description FROM signs WHERE EXISTS(VALIDTIME PERIOD[2012/01-2012/02) SELECT 1 from beds WHERE beds.plant=signs.plant AND beds.bed_type=2);

-- SQL:
SELECT signs.description FROM signs
WHERE date_to >= TO_DATE('01-31-2012', 'MM-DD-YYYY') AND
date_from <= TO_DATE('01-01-2012', 'MM-DD-YYYY') AND
EXISTS(
    SELECT 1 FROM beds WHERE beds.plant=signs.plant AND beds.bed_type=2 AND
    date_to >= TO_DATE('01-31-2012', 'MM-DD-YYYY') AND
    date_from <= TO_DATE('01-01-2012', 'MM-DD-YYYY'));


-- UPDATE temporalne promenlivych dat:
--
-- Zmen informace o rostlinach na ceduli s ID=1 na: "Zeme puvodu: Neznama"
--
UPDATE signs SET description='Země původu: Neznámá' WHERE id = 1;

-- SQL:
-- 1. ukoncit puvodni zaznam (date_to = dnesni datum)
UPDATE signs
SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual) -- dnesni datum
WHERE date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY') AND signs.id = 1;

-- 2. zapsat novy zaznam (date_from = dnesni datum)
INSERT INTO signs VALUES (id, layer, geometry, description, plant, date_to, date_from)
    SELECT id_signs_seq.nextval,
           layer,
           geometry,
           'Země původu: Neznámá',
           plant,
           (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual)
           TO_DATE('12-31-9999', 'MM-DD-YYYY')
    FROM signs WHERE id = 1;


-- DELETE temporalne promenlivych dat:
--
-- Odstran ploty, ktere byly do zahrady instalovany 1.5.2013
--
VALIDTIME PERIOD[2013/05/01-2013/05/02) DELETE FROM fences;

-- SQL:
UPDATE fences
SET date_to = (SELECT TO_DATE((SELECT to_char(trunc(sysdate),'MM-DD-YYYY') FROM dual), 'MM-DD-YYYY') FROM dual) -- dnesni datum
WHERE date_from = TO_DATE('05-01-2013', 'MM-DD-YYYY') AND
date_to = TO_DATE('12-31-9999', 'MM-DD-YYYY');
