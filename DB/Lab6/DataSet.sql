use lab6;

CREATE TABLE football_player (
last_name VARCHAR(30) NOT NULL,
first_name VARCHAR(30) NOT NULL, 
years_of_experience TINYINT DEFAULT 0,
club_name VARCHAR(50) DEFAULT 'Unknown',
FOREIGN KEY (club_name) REFERENCES football_club (club_name),
PRIMARY KEY(last_name, first_name));

INSERT INTO football_player (last_name, first_name, years_of_experience, club_name) 
VALUES 
('Lohvinov', 'John', 5, NULL),
('Beckenbauer', 'Yura', 4, NULL),
('Maradona', 'Vitalik', 8, NULL),
('Kisilov', 'Anna', 2, NULL),
('Popov', 'Daniil', 6, NULL),
('Ronaldo', 'Oleh', 12, NULL),
('Herak', 'Lawrence', 4, NULL),
('Pelé', 'John', 3, NULL),
('Beckenbauer', 'Ostap', 3, NULL),
('Müller', 'Jenniffer', 1, NULL);


CREATE TABLE football_club (
club_name VARCHAR(50) NOT NULL,
club_owner_name VARCHAR(50) DEFAULT 'Unknown',
coach_name VARCHAR(50) DEFAULT 'Unknown',
country VARCHAR(50) DEFAULT 'Unknown',
PRIMARY KEY(club_name));

INSERT INTO football_club VALUES 
('Manchester United', 'Lohvinov', 'Fedunushyn', 'England'),
('Real Madrid', 'Cruyff', 'Eusébio', 'Spain'),
('Everton FC', 'Stéfano', 'Gursky', 'Italy'),
('Napoli', 'Garrincha', 'Prychko', 'Italy'),
('Southampton FC', 'Maradona', 'Kisilov', 'England'),
('West Ham United', 'Meazza', 'Maslanyi', 'England'),
('Schalke', 'Müller', 'Popov', 'Germany'),
('Inter Milan', 'Pelé', 'Lozhkin', 'Italy'),
('Leicester City', 'Ronaldo', 'Ostash', 'England'),
('Atlético Madrid', 'Manko', 'Herak', 'Spain');


CREATE TABLE add_company (
company_name VARCHAR(50) NOT NULL,
budget DOUBLE DEFAULT 0.0,
industry VARCHAR(50) DEFAULT 'Unknown',
counry VARCHAR(50) DEFAULT 'Unknown',
PRIMARY KEY(company_name));

INSERT INTO add_company VALUES
('Nike', 55.8, 'Clothes', 'USA'),
('Citroen ', 86.3, 'Automotive', 'France'),
('Opel ', 15.9, 'Automotive', 'Germany'),
('Walmart', 45.2, 'Shops', 'USA'),
('Puma', 14.6, 'Clothes', 'USA'),
('Facebook', 19.8, 'IT', 'USA'),
('Xiaomi', 8.9, 'IT', 'China'),
('Mercedes-Benz', 125.0, 'Automotive', 'Germany'),
('Lays', 44.2, 'Food', 'Italy'),
('Mills Inc.', 31.1, 'Architecture', 'Netherlands');


CREATE TABLE playerscompanies (
player_id INT NOT NULL, 
company_name VARCHAR(50) NOT NULL,
FOREIGN KEY (player_id) REFERENCES football_player (player_id),
FOREIGN KEY (company_name) REFERENCES add_company (company_name),
PRIMARY KEY(player_id, company_name));


DELIMITER //

CREATE PROCEDURE insert_playerscompanies(
IN last_name_in VARCHAR(30),
IN first_name_in VARCHAR(30),
IN company_name_in VARCHAR(50)
)

BEGIN
DECLARE msg VARCHAR(50);
-- checks if player exists
IF NOT EXISTS(SELECT * FROM football_player WHERE last_name = last_name_in AND first_name = first_name_in) 
THEN SET msg = 'There is no such football player';
-- checks if company exists
ELSEIF NOT EXISTS(SELECT * FROM add_company WHERE company_name = company_name_in)
THEN SET msg = 'There is no such company';
-- checks if such combinations exists
ELSEIF EXISTS(
SELECT * FROM playerscompanies WHERE
last_name = (SELECT last_name FROM football_player WHERE last_name = last_name_in AND first_name = first_name_in) AND
first_name = (SELECT first_name FROM football_player WHERE last_name = last_name_in AND first_name = first_name_in) AND
company_name = (SELECT company_name FROM add_company WHERE company_name = company_name_in)
) THEN SET msg = 'This company already has this player';

ELSE INSERT INTO playerscompanies VALUE(
(SELECT last_name FROM football_player WHERE last_name = last_name_in AND first_name = first_name_in),
(SELECT first_name FROM football_player WHERE last_name = last_name_in AND first_name = first_name_in),
(SELECT company_name FROM add_company WHERE company_name = company_name_in)
); SET msg = 'OK'; 

END IF;

SELECT msg AS message;

END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE update_football_player(
IN old_last_name_in VARCHAR(30),
IN old_first_name_in VARCHAR(30),
IN new_last_name_in VARCHAR(30),
IN new_first_name_in VARCHAR(30)
)

BEGIN

DECLARE msg VARCHAR(50);

IF EXISTS (SELECT * FROM football_player WHERE last_name = old_last_name_in AND first_name = old_first_name_in) 
THEN UPDATE football_player SET last_name = new_last_name_in, first_name = new_first_name_in 
WHERE last_name = old_last_name_in AND first_name = old_first_name_in; SET msg = 'OK';

ELSE SET msg = 'No such player';
END IF;
SELECT msg AS message;
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_football_club(
IN old_club_name_in VARCHAR(50),
IN new_club_name_in VARCHAR(50)
)

BEGIN

DECLARE msg VARCHAR(50);

IF EXISTS (SELECT * FROM football_club WHERE club_name = old_club_name_in) 
THEN UPDATE football_club SET club_name = new_club_name_in
WHERE club_name = old_club_name_in; SET msg = 'OK';

ELSE SET msg = 'No such club';

END IF;

END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE update_company(
IN old_company_name_in VARCHAR(50),
IN new_company_name_in VARCHAR(50)
)

BEGIN 

DECLARE msg VARCHAR(50);

IF EXISTS (SELECT * FROM add_company WHERE commany_name = old_company_name_in)
THEN UPDATE add_company SET company_name = new_company_name_in
WHERE company_name = old_company_name_in; SET msg = 'OK';

ELSE SET msg = 'No such company';
END IF;
END //

DELIMITER ;

alter table football_player add column player_id int;

alter table football_player add unique(player_id);

drop table playerscompanies;-- CALL update_football_club('Popov', 'Daniil');


alter table football_player alter player_id set identity(1, 1);

alter table playerscompanies drop foreign key player_id;
ALTER TABLE football_player MODIFY player_id INTEGER NOT NULL AUTO_INCREMENT;



