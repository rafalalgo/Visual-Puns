DROP TABLE IF EXISTS ranking CASCADE;
DROP TABLE IF EXISTS slowo CASCADE;

CREATE TABLE ranking (
  id     SERIAL NOT NULL PRIMARY KEY,
  nazwa  TEXT   NOT NULL,
  punkty INT    NOT NULL
);

CREATE TABLE slowo (
  slowo TEXT
);