CREATE TABLE submissions
(
    id         BIGSERIAL PRIMARY KEY,
    problem_id BIGINT        NOT NULL,
    language   varchar(16)   NOT NULL,
    version    varchar(16)   NOT NULL,
    filename   varchar(256)  NOT NULL,
    code       varchar(2048) NOT NULL,
    status     INT           NOT NULL,
    grade      INT           NULL,
    user_id    BIGINT        NOT NULL,
    CONSTRAINT test_cases_fk_problem_id FOREIGN KEY (problem_id) REFERENCES problems (id),
    CONSTRAINT problems_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX submissions_idx_problem_id on submissions (problem_id);
CREATE INDEX problems_idx_user_id on submissions (user_id);

