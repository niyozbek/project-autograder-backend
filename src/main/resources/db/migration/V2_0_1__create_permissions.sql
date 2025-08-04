CREATE TABLE permissions
(
    id      SERIAL PRIMARY KEY,
    name    varchar(45) NOT NULL
);

CREATE UNIQUE INDEX permissions_idx_name on permissions (name);
