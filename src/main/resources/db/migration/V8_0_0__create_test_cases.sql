CREATE TABLE test_cases
(
    id              BIGSERIAL PRIMARY KEY,
    problem_id      BIGINT NOT NULL,
    input           varchar(256),
    expected_output varchar(256),
    CONSTRAINT test_cases_fk_problem_id FOREIGN KEY (problem_id) REFERENCES problems (id)
);

CREATE INDEX test_cases_idx_problem_id on test_cases (problem_id);

