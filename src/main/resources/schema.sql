CREATE TABLE SPACESHIP (
    ID bigint AUTO_INCREMENT PRIMARY KEY,
    NAME varchar(50) not null,
    SERIES varchar(250) not null,
    MOVIE varchar(250) not null,
    MODEL varchar(250) not null,
    CREW_CAPACITY int
);

CREATE TABLE users (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    country VARCHAR(255),
    firstname VARCHAR(255),
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    role VARCHAR(255) CHECK (role IN ('ADMIN', 'USER')),
    username VARCHAR(255) NOT NULL
);