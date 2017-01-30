
CREATE TABLE users (
  id  INT PRIMARY KEY,
  firstname VARCHAR(30),
  lastname VARCHAR(30),
  email  VARCHAR(50),
  username  VARCHAR(16),
  password  VARCHAR(25)
);

CREATE TABLE spittles (
  id         INTEGER PRIMARY KEY,
  message 	VARCHAR(4000),
  time 		TIMESTAMP,
  latitude  DOUBLE,
  longitude  DOUBLE
);

