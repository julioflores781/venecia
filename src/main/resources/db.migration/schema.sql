CREATE TABLE SPACESHIP (
    ID bigint not null auto_increment primary key,
    NAME varchar(50) not null,
    SERIES varchar(250) not null,
    MOVIE varchar(250) not null,
    MODEL varchar(250) not null,
    CREW_CAPACITY int
);

ALTER TABLE SPACESHIP ALTER COLUMN ID RESTART WITH 6;