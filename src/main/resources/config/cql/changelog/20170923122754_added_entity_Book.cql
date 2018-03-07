
CREATE TABLE IF NOT EXISTS book (
    id uuid,
    name text,
    price int,
    author text,
    released_date date,
    added_date date,
    ratings int,
    description text,
    language text,
    format text,
    isbn_no text,
    ratings_avg double,
    ratings_total double,
    PRIMARY KEY(id)
);
