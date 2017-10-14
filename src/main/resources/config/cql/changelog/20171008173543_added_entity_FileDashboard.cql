
CREATE TABLE IF NOT EXISTS fileDashboard (
    id uuid,
    filename text,
    filesize bigint,
    createdby text,
    PRIMARY KEY(id)
);
