CREATE TABLE submission_details
(
    id            BIGSERIAL PRIMARY KEY,
    submission_id BIGINT NOT NULL,
    test_case_id  BIGINT NOT NULL,
    actual_output varchar(256),
    test_case_is_passed boolean NOT NULL,
    CONSTRAINT submission_details_fk_submission_id FOREIGN KEY (submission_id) REFERENCES submissions (id),
    CONSTRAINT submission_details_fk_test_case_id FOREIGN KEY (test_case_id) REFERENCES test_cases (id)
);

CREATE INDEX submission_details_idx_submission_id on submission_details (submission_id);
CREATE INDEX submission_details_idx_test_case_id on submission_details (test_case_id);

