DROP TABLE IF EXISTS TASK;

CREATE TABLE TASK(
	ID INT NOT NULL  AUTO_INCREMENT PRIMARY KEY,
	TITLE VARCHAR ,
	DESCRIPTION VARCHAR,
    STATUS INT DEFAULT 0,
    DEADLINE DATE
);