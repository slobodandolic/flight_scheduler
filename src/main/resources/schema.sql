DROP TABLE IF EXISTS gate;
CREATE TABLE gate (
  id             INT                                NOT NULL AUTO_INCREMENT,
  occupied       ENUM('FREE', 'CLOSED', 'OCCUPIED') NOT NULL,
  time_available VARCHAR(50)                        NOT NULL,
  flight_code    VARCHAR(50),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS flight;
CREATE TABLE flight (
  id          INT         NOT NULL AUTO_INCREMENT,
  flight_code VARCHAR(50),
  PRIMARY KEY (id)
);