CREATE TABLE IF NOT EXISTS ratings (
  id      SERIAL        PRIMARY KEY,
  userid    INTEGER,
  routeid INTEGER,
  val INTEGER
);

CREATE TABLE IF NOT EXISTS routes (
  id      SERIAL        PRIMARY KEY,
  places    VARCHAR(255),
  length NUMERIC ,
  avg_rating NUMERIC
);

CREATE TABLE IF NOT EXISTS users (
  id      SERIAL        PRIMARY KEY,
  email    VARCHAR(255),
  favorite_routes VARCHAR(255),
  created_routes VARCHAR(255)
);
