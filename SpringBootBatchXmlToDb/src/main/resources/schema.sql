DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS taxpayer_type;
DROP TABLE IF EXISTS sovos_taxpayers;


CREATE TABLE person  (
    person_id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    email VARCHAR(100),
    age INT
);
CREATE TABLE taxpayer_type  (
    id INT NOT NULL PRIMARY KEY,
    taxpayerid VARCHAR(40),
    type INT
);
CREATE TABLE sovos_taxpayers  (
   
    taxpayerid VARCHAR(40),
    status INT,
    condition INT
    
);
