--
-- ER/Studio Data Architect SQL Code Generation
-- Project :      track_record.DM1
--
-- Date Created : Tuesday, November 20, 2018 09:10:17
-- Target DBMS : MySQL 5.x
--

-- 
-- TABLE: album 
--

CREATE TABLE album(
    album_id       INT            PRIMARY KEY AUTO_INCREMENT,
    album_name     VARCHAR(50)    NOT NULL,
    catalog_num    INT,
    record_year    INT,
    album_img      LONGBLOB,
    artist_id      INT            NOT NULL,
    studio_id      INT            NOT NULL
)ENGINE=InnoDB
;



-- 
-- TABLE: artist 
--

CREATE TABLE artist(
    artist_id      INT            PRIMARY KEY AUTO_INCREMENT,
    artist_name    VARCHAR(50)    NOT NULL,
    biography      TEXT,
    country        VARCHAR(50),
    birth_date     DATE           
                   CHECK (birth_date <= 1955)
)ENGINE=InnoDB
;



-- 
-- TABLE: song 
--

CREATE TABLE song(
    song_id         INT            PRIMARY KEY AUTO_INCREMENT,
    album_id        INT            NOT NULL,
    song_name       VARCHAR(50)    NOT NULL,
    album_number    INT            
                    CHECK (album_number > 0),
    song_time       TIME
)ENGINE=InnoDB
;



-- 
-- TABLE: studio 
--

CREATE TABLE studio(
    studio_id               INT            PRIMARY KEY AUTO_INCREMENT,
    studio_name             VARCHAR(50),
    studio_director_name    VARCHAR(50),
    address                 VARCHAR(50)
)ENGINE=InnoDB
;



-- 
-- INDEX: PK4 
--


-- 
-- INDEX: PK2 
--


-- 
-- INDEX: PK1 
--


-- 
-- INDEX: PK3 
--

-- 
-- TABLE: album 
--

ALTER TABLE album ADD CONSTRAINT Refartist21 
    FOREIGN KEY (artist_id)
    REFERENCES artist(artist_id) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE album ADD CONSTRAINT Refstudio51 
    FOREIGN KEY (studio_id)
    REFERENCES studio(studio_id) ON DELETE RESTRICT ON UPDATE RESTRICT
;


-- 
-- TABLE: song 
--

ALTER TABLE song ADD CONSTRAINT Refalbum61 
    FOREIGN KEY (album_id)
    REFERENCES album(album_id) ON DELETE RESTRICT ON UPDATE RESTRICT