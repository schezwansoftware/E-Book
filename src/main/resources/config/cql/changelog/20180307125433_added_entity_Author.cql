
CREATE TABLE IF NOT EXISTS author (
    id uuid,
    name text,
    biography text,
    genre text,
    ratings_total double,
    ratings_avg double,
    ratings int,
    PRIMARY KEY(id)
);
