
CREATE TABLE IF NOT EXISTS orders (
    id uuid,
    orderid text,
    ordername text,
    ordertype text,
    orderby text,
    orderdate tuple<timestamp,varchar>,
    orderattendee text,
    isassigned boolean,
    orderaddress text,
    delivered boolean,
    description text,
    PRIMARY KEY(id)
);
