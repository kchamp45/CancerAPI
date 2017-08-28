SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS patients (
    id int PRIMARY KEY auto_increment,
    gender VARCHAR,
    diagnosis VARCHAR,
    name VARCHAR,
    age INT,
    cancerId INT,
    geneMutation VARCHAR,
    famHistory BOOLEAN
   );

CREATE TABLE IF NOT EXISTS treatments (
    id int PRIMARY KEY auto_increment,
    description VARCHAR,
    duration VARCHAR
    );

CREATE TABLE IF NOT EXISTS cancers (
    id int PRIMARY KEY auto_increment,
    type VARCHAR,
    description VARCHAR
    ) ;

CREATE TABLE IF NOT EXISTS medplans (
     id int PRIMARY KEY auto_increment,
     patientId INTEGER,
     treatmentId INTEGER
    );