CREATE TABLE submission_test_results
(
    submission_id        BIGINT PRIMARY KEY,
    status               INT     NOT NULL,
    total_test_cases     INTEGER NOT NULL,
    processed_test_cases INTEGER NOT NULL,
    correct_test_cases   INTEGER NOT NULL,
    CONSTRAINT submission_test_results_fk_submission_id FOREIGN KEY (submission_id) REFERENCES submissions (id)
);

CREATE INDEX submission_test_results_idx_submission_id on submission_test_results (submission_id);

