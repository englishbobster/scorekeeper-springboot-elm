SET MODE PostgreSQL;

CREATE TABLE planned_matches (
  id INTEGER NOT NULL PRIMARY KEY,
  match_time TIMESTAMP,
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
