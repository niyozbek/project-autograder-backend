CREATE TABLE problems
(
    id          BIGSERIAL PRIMARY KEY,
    title       varchar(256) NOT NULL,
    description text         NOT NULL,
    status      INT          NOT NULL,
    user_id     BIGINT       NOT NULL,
    CONSTRAINT problems_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX problems_fk_user_id on problems (user_id);

