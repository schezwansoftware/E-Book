
CREATE TABLE IF NOT EXISTS member (
    id uuid,
    name text,
    member_login text,
    member_email text,
    join_date tuple<timestamp,varchar>,
    memeber_type text,
    PRIMARY KEY(id)
);
