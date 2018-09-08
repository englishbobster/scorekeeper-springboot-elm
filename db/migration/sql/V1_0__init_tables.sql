CREATE TABLE planned_matches (
  id INTEGER NOT NULL UNIQUE PRIMARY KEY,
  match_time TIMESTAMP WITH TIME ZONE,
  arena VARCHAR(200),
  home_team VARCHAR(200),
  away_team VARCHAR(200),
  match_type VARCHAR(15),
  full_type BOOLEAN
);

CREATE TABLE full_time_scores (
  match_id INTEGER REFERENCES planned_matches(id),
  home_score INTEGER,
  away_score INTEGER
);

CREATE TABLE players (
  id SERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  salt varchar(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  paid BOOLEAN DEFAULT FALSE,
  created TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE player_guesses (
  match_id INTEGER REFERENCES planned_matches(id),
  player_id INTEGER REFERENCES players(id),
  homes_score INTEGER,
  away_score INTEGER
);