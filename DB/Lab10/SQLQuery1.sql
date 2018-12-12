/*
 * ER/Studio Data Architect SQL Code Generation
 * Project :      track_record.DM1
 *
 * Date Created : Tuesday, November 20, 2018 09:22:09
 * Target DBMS : Microsoft SQL Server 4.x
 */

/* 
 * TABLE: album 
 */

CREATE TABLE album(
    album_id       int            NOT NULL,
    album_name     varchar(50)    NOT NULL,
    catalog_num    int            NULL,
    record_year    int            NULL,
    album_img      image          NULL,
    artist_id      int            NOT NULL,
    studio_id      int            NOT NULL
)
go



IF OBJECT_ID('album') IS NOT NULL
    PRINT '<<< CREATED TABLE album >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE album >>>'
go

/* 
 * TABLE: artist 
 */

CREATE TABLE artist(
    artist_id      int            NOT NULL,
    artist_name    varchar(50)    NOT NULL,
    biography      text           NULL,
    country        varchar(50)    NULL,
    birth_date     datetime       NULL
)
go



IF OBJECT_ID('artist') IS NOT NULL
    PRINT '<<< CREATED TABLE artist >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE artist >>>'
go

/* 
 * TABLE: song 
 */

CREATE TABLE song(
    song_id         int            NOT NULL,
    album_id        int            NOT NULL,
    song_name       varchar(50)    NOT NULL,
    album_number    int            NULL,
    song_time       datetime       NULL
)
go



IF OBJECT_ID('song') IS NOT NULL
    PRINT '<<< CREATED TABLE song >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE song >>>'
go

/* 
 * TABLE: studio 
 */

CREATE TABLE studio(
    studio_id               int            NOT NULL,
    studio_name             varchar(50)    NULL,
    studio_director_name    varchar(50)    NULL,
    address                 varchar(50)    NULL
)
go



IF OBJECT_ID('studio') IS NOT NULL
    PRINT '<<< CREATED TABLE studio >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE studio >>>'
go

/* 
 * INDEX: PK4 
 */

CREATE UNIQUE INDEX PK4 ON album(album_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('album') AND name='PK4')
    PRINT '<<< CREATED INDEX album.PK4 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX album.PK4 >>>'
go

/* 
 * INDEX: PK2 
 */

CREATE UNIQUE INDEX PK2 ON artist(artist_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('artist') AND name='PK2')
    PRINT '<<< CREATED INDEX artist.PK2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX artist.PK2 >>>'
go

/* 
 * INDEX: PK1 
 */

CREATE UNIQUE INDEX PK1 ON song(song_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('song') AND name='PK1')
    PRINT '<<< CREATED INDEX song.PK1 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX song.PK1 >>>'
go

/* 
 * INDEX: PK3 
 */

CREATE UNIQUE INDEX PK3 ON studio(studio_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('studio') AND name='PK3')
    PRINT '<<< CREATED INDEX studio.PK3 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX studio.PK3 >>>'
go
