DROP DATABASE IF EXISTS family_tree;
CREATE DATABASE family_tree;

USE family_tree;

DROP TABLE IF EXISTS gender;
CREATE TABLE gender (
  gender VARCHAR(10) PRIMARY KEY NOT NULL
);

DROP TABLE IF EXISTS family_member;
CREATE TABLE family_member(
  member_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  birth_date DATE DEFAULT NULL,
  death_date DATE DEFAULT NULL,
  birth_place VARCHAR(80) DEFAULT 'unknown',
  death_place VARCHAR(80) DEFAULT 'unknown',
  marriage_date DATE DEFAULT NULL,
  gender VARCHAR(10) NOT NULL
);

DROP TABLE IF EXISTS family_tree;
CREATE TABLE family_tree (
  member_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  parent_id INT DEFAULT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  birth_date DATE DEFAULT NULL,
  death_date DATE DEFAULT NULL,
  birth_place VARCHAR(80) DEFAULT 'unknown',
  death_place VARCHAR(80) DEFAULT 'unknown',
  credit_card_number BIGINT DEFAULT NULL,
  gender VARCHAR(10) NOT NULL ,
  partner_id INT UNIQUE NOT NULL
);


DROP TABLE IF EXISTS family_asset;
CREATE TABLE family_asset(
  asset_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  asset_name VARCHAR(50) NOT NULL,
  price_estimate FLOAT DEFAULT 0.0,
  price_max FLOAT DEFAULT 0.0,
  price_min FLOAT DEFAULT 0.0,
  catalog_code VARCHAR(10) UNIQUE DEFAULT NULL
);

DROP TABLE IF EXISTS asset_member;
CREATE TABLE asset_member(
  asset_member_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  asset_id INT NOT NULL,
  member_id INT NOT NULL
);

-- TRIGGERS --
DROP TRIGGER IF EXISTS before_family_member_insert;
CREATE TRIGGER before_family_member_insert
  BEFORE INSERT ON family_member
  FOR EACH ROW

  BEGIN

    DECLARE msg VARCHAR(50);

    IF NEW.birth_date > current_date() OR NEW.death_date > current_date() THEN
      SET msg = 'Birth and death dates cannot be in the future';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    ELSEIF NOT exists(SELECT * FROM gender WHERE gender.gender = NEW.gender) THEN
      SET msg = 'No such gender';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    END IF;

  END;

DROP TRIGGER IF EXISTS before_family_tree_insert;
CREATE TRIGGER before_family_tree_insert
  BEFORE INSERT ON family_tree
  FOR EACH ROW

  BEGIN

    DECLARE msg VARCHAR(50);
    IF NOT exists(SELECT * FROM gender WHERE gender.gender = NEW.gender) THEN
      SET msg = 'No Such Gender';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    ELSEIF NOT exists(SELECT * FROM family_member WHERE family_member.member_id = NEW.partner_id) THEN
      SET msg = 'No such partner';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    ELSEIF NOT exists(SELECT * FROM family_tree WHERE member_id = NEW.parent_id) THEN
      IF NEW.parent_id IS NULL THEN SET msg = 'Warning: Null parent Id';
        ELSE
          SET msg = 'Wrong parent id';
          SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
      END IF;

    END IF;

  END;


DROP TRIGGER IF EXISTS before_family_asset_insert;
CREATE TRIGGER before_family_asset_insert
  BEFORE INSERT ON family_asset
  FOR EACH ROW

  BEGIN

    DECLARE msg VARCHAR(50);

    IF NEW.catalog_code NOT REGEXP '[AMZ]\\d{5}[A-Z|a-z]{2}' THEN
      SET msg = 'Not allowed format';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    END IF;

  END;

DROP TRIGGER IF EXISTS before_asset_member_insert;
CREATE TRIGGER before_asset_member_insert
  BEFORE INSERT ON asset_member
  FOR EACH ROW

  BEGIN

    DECLARE msg VARCHAR(50);

    IF NOT exists(SELECT * FROM family_tree WHERE family_tree.member_id = NEW.member_id) THEN
      SET msg = 'No such family member';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    ELSEIF NOT exists(SELECT * FROM family_asset WHERE family_asset.asset_id = NEW.asset_id) THEN
      SET msg = 'No such asset';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    ELSEIF exists(SELECT * FROM asset_member WHERE member_id = NEW.member_id AND asset_id = NEW.asset_id) THEN
      SET msg = 'Such combination already exists';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    END IF;

  END;

DROP TRIGGER IF EXISTS before_family_member_delete;
CREATE TRIGGER before_family_member_delete
  BEFORE DELETE ON family_member
  FOR EACH ROW

  BEGIN

    DECLARE msg VARCHAR(50);
    IF count((SELECT * FROM family_member)) <= 3 THEN
      SET msg = 'Minimal cardinality constraint failed';
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    END IF;

  END;

INSERT INTO gender VALUES ('male'), ('female');

INSERT INTO family_member (first_name, last_name, birth_date, death_date,
                           birth_place, death_place, marriage_date, gender)
VALUES
       ('John', 'Miller', '1985-05-05', '2016-08-21', 'Venice', 'Seoul', '2016-07-19', 'male'),
       ('Olivia', 'Wright', '1987-05-05', '2016-08-21', 'London', 'Bangkok', '2015-07-19', 'female'),
       ('Harry', 'Watson', '1896-05-05', '2016-08-21', 'Tokyo', 'London', '2014-07-19', 'male'),
       ('Emily', 'Green', '1896-05-05', '2018-08-21', 'London', 'Istanbul', '2018-07-19', 'female'),
       ('George', 'Murphy', '1786-05-05', '2016-08-21', 'London', 'Brussels', '2005-07-19', 'male'),
       ('Sophia', 'Rivera', '2015-05-05', '2016-08-21', 'Budapest', 'Kuala Lumpur', '2008-07-19', 'female'),
       ('Jacob', 'Cooper', '2016-05-05', '2016-08-21', 'Singapore', 'London', '2007-07-19', 'male'),
       ('Ava', 'Richardson', '1998-05-05', '2016-08-21', 'New York', 'London', '2010-07-19', 'female'),
       ('Freddie', 'Cook', '1896-05-05', '2016-08-21', 'Sydney', 'Dubai', '2009-07-19', 'male'),
       ('Isabella', 'Morris', '1893-05-05', '2016-08-21', 'Paris', 'London', '1999-07-19', 'female');


INSERT INTO family_tree (parent_id, first_name, last_name, birth_date, death_date,
                         birth_place, death_place, credit_card_number, gender, partner_id)
VALUES
       (NUll ,'John', 'Walker', '1985-05-05', '2016-08-21', 'Venice', 'Seoul', 5698987832456841, 'male', 6),
       (1 ,'Alma', 'Clark', '1996-05-05', '2016-08-21', 'Venice', 'Budapest', 5698987832456841, 'female', 10),
       (1 ,'Arnold', 'Parker', '1915-05-05', '2016-08-21', 'Kuala Lumpur', 'Seoul', 3082059706015675, 'male', 5),
       (2 ,'Amabel', 'Evans', '1945-05-05', '2016-08-21', 'Venice', 'Dubai', 4190024830598228, 'female', 1),
       (3 ,'Arthur', 'Collins', '1965-05-05', '2016-08-21', 'New York', 'Seoul', 932037935447372, 'male', 3),
       (3 ,'Angela', 'Sanders', '1919-05-05', '2016-08-21', 'Tokyo', 'Singapore', 4939169667399831, 'female', 4),
       (3 ,'George', 'Gray', '1917-05-05', '2016-08-21', 'Tokyo', 'Seoul', 4933617217189352, 'male', 8),
       (5 ,'Arabella', 'Watson', '1973-05-05', '2016-08-21', 'Venice', 'Dubai', 7478862611887037, 'female', 9),
       (3 ,'Jeremy', 'Ward', '1987-05-05', '2016-08-21', 'Bangkok', 'Seoul', 5496216753266857, 'male', 2),
       (5 ,'Augusta', 'Howard', '1998-05-05', '2016-08-21', 'Singapore', 'Seoul', 3320773751300739, 'female', 7);


INSERT INTO family_asset (asset_name, price_estimate, price_max, price_min, catalog_code)
VALUES
       ('The Dropa Stones', 54.4, 98.4, 29.5, 'A98453XS'),
       ('The Saqqara bird', 45.6, 105.8, 18.6, 'Z91547UE'),
       ('The Baghdad battery', 45.2, 122.6, 27.5, 'M62485DD'),
       ('The Piri Reis map', 65.1, 92.6, 55.4, 'A34598PO'),
       ('The Nazca drawings', 98.1, 293.4, 48.6, 'M31587OX'),
       ('The Sacsayhuaman walls', 23.0, 30.5, 18.9, 'Z25493IA'),
       ('The Antikythera mechanism', 450.0, 485.0, 400.2, 'A50486FY'),
       ('Relic from Egypt', 165.8, 185.9, 80.3, 'M01489PD'),
       ('Thor\'s Hammer', 44.8, 68.3, 12.6, 'Z98701UY');

INSERT INTO asset_member (asset_id, member_id)
VALUES
       (4, 5),
       (8, 1),
       (2, 7),
       (4, 3),
       (5, 8);




SET GLOBAL log_bin_trust_function_creators = 1;

DROP FUNCTION IF EXISTS get_max_price_avg;
CREATE FUNCTION get_max_price_avg()
  RETURNS FLOAT
  BEGIN
    RETURN (SELECT avg(price_max) FROM family_asset);
  END;


SELECT * FROM family_asset
WHERE price_max < get_max_price_avg();


DROP FUNCTION IF EXISTS get_relationship_by_key;
CREATE FUNCTION get_relationship_by_key(val INT)
  RETURNS VARCHAR(80)
  BEGIN
    DECLARE result VARCHAR(80);

    SELECT first_name, last_name FROM family_tree WHERE partner_id = val INTO @first_name1, @last_name1;
    SELECT first_name, last_name FROM family_member WHERE member_id = val INTO @first_name2, @last_name2;

    SET result = concat('1: ', @first_name1, ' ', @last_name1, '  2: ', @first_name2, ' ', @last_name2);

    RETURN result;
  END;


DROP PROCEDURE IF EXISTS insert_to_family_member;
CREATE PROCEDURE insert_to_family_member (
  IN first_name_in VARCHAR(50),
  IN last_name_in VARCHAR(50),
  IN birth_date_in DATE,
  IN death_date_in DATE,
  IN birth_place_in VARCHAR(80),
  IN death_place_in VARCHAR(80),
  IN marriage_date_in DATE,
  IN gender_in VARCHAR(10)
)

BEGIN

  INSERT INTO family_member (first_name, last_name, birth_date, death_date,
                             birth_place, death_place, marriage_date, gender)
  VALUE (first_name_in, last_name_in, birth_date_in, death_date_in, birth_place_in, death_place_in, marriage_date_in, gender_in);

END;

DROP PROCEDURE IF EXISTS insert_package_to_family_asset;
CREATE PROCEDURE insert_package_to_family_asset ()
  BEGIN
    SET @counter = 0;
    WHILE (@counter < 10) DO
      SET @rand = floor(rand() * 100);
      WHILE (exists(SELECT * FROM family_asset WHERE asset_name = concat('Noname', @rand))) DO
        SET @rand = floor(rand() * 100);
      END WHILE;
      SET @name = concat('Noname', @rand);

      INSERT INTO family_asset (asset_name, price_estimate, price_max, price_min, catalog_code)
      VALUE
            (@name, 0.0, 0.0, 0.0, 'A00000AA');

      SET @counter = @counter + 1;
    END WHILE;

  END;


DROP PROCEDURE IF EXISTS copy_member_names;
CREATE PROCEDURE copy_member_names ()
  BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE first_nameT, last_nameT VARCHAR(50);

    DECLARE name_cursor CURSOR FOR
      SELECT first_name, last_name FROM family_member;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN name_cursor;

    myLoop: LOOP
      FETCH name_cursor INTO first_nameT, last_nameT;
      IF done = TRUE THEN LEAVE myLoop; END IF;

      SET @query = concat('CREATE TABLE ', first_nameT, last_nameT, '(', first_nameT, ' VARCHAR(50), ', last_nameT, ' VARCHAR(50)', ')');
      PREPARE myquery FROM @query;
      EXECUTE myquery;

      DEALLOCATE PREPARE myquery;

    END LOOP;

    CLOSE name_cursor;
  END;
