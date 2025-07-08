CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    username  varchar(45) NOT NULL,
    email     varchar(45),
    full_name varchar(45),
    password  varchar(64) NOT NULL,
    enabled   boolean
);

CREATE UNIQUE INDEX users_idx_username on users (username);
CREATE UNIQUE INDEX users_idx_email on users (email);

