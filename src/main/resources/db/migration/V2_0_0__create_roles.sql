CREATE TABLE roles
(
    id      SERIAL PRIMARY KEY,
    name    varchar(45) NOT NULL
);

CREATE UNIQUE INDEX roles_idx_name on roles (name);
