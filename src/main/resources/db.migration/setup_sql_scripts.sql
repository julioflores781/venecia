USE [master];

--IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'VENECIA')
--CREATE DATABASE [VENECIA] COLLATE Latin1_General_CI_AS
--
--USE [VENECIA];
DROP TABLE IF EXISTS SPACESHIP;
CREATE TABLE SPACESHIP (
    ID bigint identity(1,1) primary key,
    NAME varchar(50) not null,
    SERIES varchar(250) not null,
    MOVIE varchar(250) not null,
    MODEL varchar(250) not null,
    CREW_CAPACITY int
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INTEGER IDENTITY(1,1) NOT NULL PRIMARY KEY,
    country VARCHAR(255),
    firstname VARCHAR(255),
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    role VARCHAR(255) CHECK (role IN ('ADMIN', 'USER')),
    username VARCHAR(255) NOT NULL
);


INSERT INTO SPACESHIP (NAME, SERIES, MOVIE, MODEL, CREW_CAPACITY) VALUES
('SpaceX Falcon 9', 'Falcon', 'Interstellar', 'F9', 7),
('NASA Voyager 1', 'Voyager', 'Star Trek: The Motion Picture', 'V1', 5),
('Millennium Falcon', 'Star Wars', 'Star Wars: Episode IV - A New Hope', 'YT-1300', 5),
('USS Enterprise', 'Star Trek', 'Star Trek: The Original Series', 'NCC-1701', 500),
('Apollo 11', 'Apollo', 'First Man', 'Saturn V', 3),
('X-Wing', 'Star Wars', 'Star Wars: Episode IV - A New Hope', 'T-65B', 1);

INSERT INTO users (country, firstname, lastname, password, [role], username) VALUES
('Madrid', 'julio', 'flores', '$2a$10$4NBaxPpQPupty3FIKQxiZuDUi0w2176FJPUXy3u1S18HP43MO/nLG', 'USER', 'julioflores781@gmail.com');