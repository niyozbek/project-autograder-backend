CREATE TABLE problems
(
    id          BIGSERIAL PRIMARY KEY,
    title       varchar(256) NOT NULL,
    description text         NOT NULL,
    status      INT          NOT NULL,
    lecturer_id BIGINT       NOT NULL,
    CONSTRAINT problems_fk_lecturer_id FOREIGN KEY (lecturer_id) REFERENCES users (id)
);

CREATE INDEX problems_idx_lecturer_id on problems (lecturer_id);

