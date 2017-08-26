SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS patients (
    id int PRIMARY KEY auto_increment,
    type VARCHAR,
    diagnosis VARCHAR,
    name VARCHAR,
    age INT,
    geneMutation VARCHAR,
    famHistory BOOLEAN,
    habit INT,
    habitDuration VARCHAR
   );

CREATE TABLE IF NOT EXISTS cancers (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR
    );

CREATE TABLE IF NOT EXISTS patients_cancers (
 id int PRIMARY KEY auto_increment,
 patientId INTEGER,
 cancerId INTEGER
);