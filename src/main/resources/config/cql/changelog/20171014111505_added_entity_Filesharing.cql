
CREATE TABLE IF NOT EXISTS filesharing (
    id uuid,
    filename text,
    sharedby text,
    sharedto text,
    sharedon date,
    verified boolean,
    PRIMARY KEY(id)
);
