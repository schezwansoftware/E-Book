
CREATE TABLE IF NOT EXISTS book (
    id uuid,
    name text,
    price int,
    author text,
    released_date date,
    added_date date,
    ratings int,
    description text,
    PRIMARY KEY(id)
);
