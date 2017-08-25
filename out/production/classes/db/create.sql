SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS patients (
    id int PRIMARY KEY auto_increment,
    type VARCHAR,
    name VARCHAR,
    age INT,
    diagnosis VARCHAR,
    geneMutation VARCHAR,
    famHistory VARCHAR,
    habit INT,
    habitDuration VARCHAR
    );

CREATE TABLE IF NOT EXISTS cancers (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR
    );

--CREATE TABLE IF NOT EXISTS patients_cancers (
-- id int PRIMARY KEY auto_increment,
-- patientId INTEGER,
-- cancerId INTEGER
--);