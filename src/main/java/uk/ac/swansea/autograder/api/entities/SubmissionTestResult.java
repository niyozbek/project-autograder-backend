package uk.ac.swansea.autograder.api.entities;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "submission_test_results")
@Data
public class SubmissionTestResult {

    @Id
    @Column(name = "submission_id")
    private Long submissionId;
    private Status status;

    private Integer totalTestCases;
    private Integer processedTestCases;
    private Integer correctTestCases;
    public enum Status {PROCESSING, COMPLETED}
}