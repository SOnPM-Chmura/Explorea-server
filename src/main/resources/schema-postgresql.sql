CREATE TABLE IF NOT EXISTS ratings (
  id      SERIAL,
  user_id    INTEGER,
  route_id INTEGER,
  rating INTEGER,
  PRIMARY KEY (user_id, route_id)
);

CREATE TABLE IF NOT EXISTS routes (
  id      SERIAL        PRIMARY KEY,
  coded_route    VARCHAR(255),
  avg_rating NUMERIC(2,1),
  length_by_foot INTEGER,
  length_by_bike INTEGER,
  time_by_foot INTEGER,
  time_by_bike INTEGER
);

CREATE TABLE IF NOT EXISTS users (
  id      SERIAL        PRIMARY KEY,
  google_user_id    VARCHAR(255),
  favorite_routes VARCHAR(255),
  created_routes VARCHAR(255)
);
